/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.micromanager.ScanPlugin;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import mmcorej.CMMCore;
import mmcorej.StrVector;
import org.micromanager.internal.MMStudio;

/**
 *
 * @author Jonah Sokoloff
 * 
 * This is the main controller class of the plugin. This gets user-input from the view and assigns values
 * to both the model classes (RegionSettings and AcquisitionSettings) and the view. All action listeners
 * and logic for GUI elements are kept in this file as well as any logic that goes with assigning values to 
 * RegionSettings and AcquisitionSettings.
 * 
 * Elements of the RegionSettingsArray in the AcquisitionSettings class are tracked using integers fishNum, 
 * fishRegionNum, and totalRegionNum that set pointer to the correct index. There's probably a more elegant 
 * way of doing this (this way requires a stupid amount of spaghetti logic, but this is what I went with.
 * 
 * Improvements:
 * - More global way of dealing with GUI logic (perhaps a state machine?)
 * -  
 * 
 */
public class Controller {

    private final MMStudio mm_;
    private final CMMCore core_;
    private RegionSettings regionSettings;
    private final HardwareCommands hardwareCommands;
    private final AcquisitionSettings acquisitionSettings;
    private Acquisition acquisition;
    private int fishNum;
    private int fishRegionNum;
    private int totalRegionNum;
    private View view;
    private int numImages;
    private Thread thread;
    
    public Controller(MMStudio studio) {
        mm_ = (MMStudio) studio;
        core_ = mm_.core();
        
        hardwareCommands = new HardwareCommands(mm_);
        
        view = new View();
        view.getRegionSettingsFrame().setVisible(true);
        
        regionSettings = new RegionSettings();
        acquisitionSettings = new AcquisitionSettings(mm_);
        
        /*These are used to track and assign indexes to RegionSettings classes
        put into the AcquisitionSettings regionSettingsArray */
        fishNum = 0;
        fishRegionNum = 0;
        totalRegionNum = 0;
        
        regionSettingsGUIChange();
        initListeners();
    }
    
     
    public void setRegionSettingsFrameVisible(Boolean visible) {
        view.getRegionSettingsFrame().setVisible(true);
    }
    
    private void updateRowHeights() {
        for (int row = 0; row < view.getRegionTable().getRowCount(); row++){
            int rowHeight = view.getRegionTable().getRowHeight();

            for (int column = 0; column < view.getRegionTable().getColumnCount(); column++) {
                Component comp = view.getRegionTable().prepareRenderer(view.getRegionTable().getCellRenderer(row, column), row, column);
                System.out.println(column);
                System.out.println(comp.getPreferredSize().height);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }

            view.getRegionTable().setRowHeight(row, rowHeight);
        }
    }
    
    public int calculateRegionNumImages(RegionSettings settings) {
        int numZStackImages = 0;
        int numVideoImages = 0;
        int numSnapImages = 0;
            
        if (settings.getZStackBoolean()) {
            double zEnd = settings.getZEndPosition();
            double zStart = settings.getZStartPosition();
            numZStackImages = (int) Math.round(Math.abs(zEnd - zStart)/settings.getStepSize()) * settings.getZStackChannelArray().size();
        }
            
        if (settings.getVideoBoolean()) {
            numVideoImages = (int) Math.round(1/(settings.getVideoExposureTime()/1000) * settings.getVideoDurationInSeconds()) * settings.getVideoChannelArray().size();
        }
            
        if (settings.getSnapBoolean()) {
            numSnapImages = settings.getSnapChannelArray().size();
            }
            
        return numZStackImages + numVideoImages + numSnapImages;
    }
    
    public void setImageNum() {
        numImages = 0;
        for (RegionSettings settings : acquisitionSettings.getRegionSettingsArray()) {
            numImages += calculateRegionNumImages(settings);
            
            if (view.getTimePointsCheckBox().isSelected()) {
                numImages *= acquisitionSettings.getNumTimePoints();
            }
        
            view.getNumImagesField().setText(String.valueOf(numImages));
            view.getMemoryField().setText(String.valueOf(Math.round(numImages * 8.08)/1000.));
        }
    }
    
    public void setAcquisitionTimeLabels() {
        int singleAcquisitionTime = 0;
        for (RegionSettings settings : acquisitionSettings.getRegionSettingsArray()) {
            if (settings.getZStackBoolean()) {
                double zEnd = settings.getZEndPosition();
                double zStart = settings.getZStartPosition();
                int numZStackImages = (int) Math.round(Math.abs(zEnd - zStart)/settings.getStepSize()) * settings.getZStackChannelArray().size();
                
                double scanTime = (numZStackImages / 30) + 0.3;
                
                double saveTime = 0;
                if (numZStackImages > 90) {
                    saveTime = numZStackImages * 0.04;
                }
                double otherTime = 9.0;
                
                singleAcquisitionTime += (Math.round(scanTime) + Math.round(saveTime) + Math.round(otherTime)) * settings.getZStackChannelArray().size();
            }
            
            if (settings.getVideoBoolean()) {
                double videoDuration = settings.getVideoDurationInSeconds();
                int numVideoImages = (int) Math.round(videoDuration / (settings.getVideoExposureTime() / 1000.));
                
                double saveTime = 0;
                if (numVideoImages > 90) {
                    saveTime = numVideoImages * 0.04;
                }
                double otherTime = 3.0;
                singleAcquisitionTime += (Math.round(saveTime) + Math.round(videoDuration) + Math.round(otherTime)) * settings.getVideoChannelArray().size();
            }
            
            if (settings.getSnapBoolean()) {
                double otherTime = 2.0;
                singleAcquisitionTime += Math.round(otherTime) + Math.round(0.3 * settings.getSnapChannelArray().size());
            }
        }
        
        int singleMinutes = Math.round(singleAcquisitionTime / 60);
        int singleSeconds = singleAcquisitionTime % 60;
        
        view.getAcquisitionTimeSingleLabel().setText(singleMinutes + " minutes " + singleSeconds + " seconds");
        view.getTotalAcquisitionTimeLabel().setText(singleMinutes + " minutes " + singleSeconds + " seconds");
        
        if (acquisitionSettings.getTimePointsBoolean()) {
            int totalAcquisitionTime = acquisitionSettings.getNumTimePoints() * acquisitionSettings.getTimePointsInterval() * 60 + singleAcquisitionTime;
            int totalMinutes = Math.round(totalAcquisitionTime / 60);
            int totalSeconds = totalAcquisitionTime % 60;
            
            view.getTotalAcquisitionTimeLabel().setText(totalMinutes + " minutes " + totalSeconds + " seconds");
        }
    }
    
    public void setUsedChannelJList(javax.swing.JList usedJList, ArrayList<String> channelArray) {
        DefaultListModel listModel = new DefaultListModel();
        
        for (int channelNum = 0; channelNum < channelArray.size(); channelNum++) {
            listModel.addElement(channelArray.get(channelNum));
        }
        usedJList.setModel(listModel);
    }
    public void setAvailableChannelJList(javax.swing.JList availableJList, ArrayList<String> channelArray) {
        DefaultListModel listModel = new DefaultListModel();
        
        listModel.removeAllElements();
        for (String configChannel: core_.getAvailableConfigs(acquisitionSettings.getChannelGroupName())) {
            if (!channelArray.contains(configChannel)) {
                listModel.addElement(configChannel);
            }
        }
        
        availableJList.setModel(listModel);
    }
    
    public void regionSettingsGUIChange() {
        view.getFishLabel().setText("Fish " + (regionSettings.getFishNumber() + 1));
        view.getRegionLabel().setText("Region " + (regionSettings.getRegionNumber() + 1));

        view.getXField().setText(String.valueOf(regionSettings.getXPosition()));
        view.getYField().setText(String.valueOf(regionSettings.getYPosition()));
        view.getZField().setText(String.valueOf(regionSettings.getZPosition()));
        
        view.getZStackCheckBox().setSelected(regionSettings.getZStackBoolean());
        view.getZStartButton().setEnabled(regionSettings.getZStackBoolean());
        view.getZEndButton().setEnabled(regionSettings.getZStackBoolean());
        view.getZStartField().setEnabled(regionSettings.getZStackBoolean());
        view.getZEndField().setEnabled(regionSettings.getZStackBoolean());
        view.getZStartField().setText(String.valueOf(regionSettings.getZStartPosition()));
        view.getZEndField().setText(String.valueOf(regionSettings.getZEndPosition()));
        view.getStepSizeField().setText(String.valueOf(regionSettings.getStepSize()));
        view.getStepSizeField().setEnabled(regionSettings.getZStackBoolean());
        view.getZStackAddChannelButton().setEnabled(regionSettings.getZStackBoolean());
        view.getZStackRemoveChannelButton().setEnabled(regionSettings.getZStackBoolean());
        
        view.getVideoCheckBox().setSelected(regionSettings.getVideoBoolean());
        view.getVideoDurationField().setEnabled(regionSettings.getVideoBoolean());
        view.getVideoExposureField().setEnabled(regionSettings.getVideoBoolean());
        view.getVideoExposureField().setText(String.valueOf(regionSettings.getVideoExposureTime()));
        view.getVideoDurationField().setText((String.valueOf(regionSettings.getVideoDurationInSeconds())));
        view.getVideoAddChannelButton().setEnabled(regionSettings.getVideoBoolean());
        view.getVideoRemoveChannelButton().setEnabled(regionSettings.getVideoBoolean());
        
        view.getSnapCheckBox().setSelected(regionSettings.getSnapBoolean());
        view.getSnapAddChannelButton().setEnabled(regionSettings.getSnapBoolean());
        view.getSnapRemoveChannelButton().setEnabled(regionSettings.getSnapBoolean());
        
        
        setAvailableChannelJList(view.getZStackAvailableChannelJList(), regionSettings.getZStackChannelArray());
        setUsedChannelJList(view.getZStackUsedChannelJList(), regionSettings.getZStackChannelArray());
        
        setAvailableChannelJList(view.getVideoAvailableChannelJList(), regionSettings.getVideoChannelArray());
        setUsedChannelJList(view.getVideoUsedChannelJList(), regionSettings.getVideoChannelArray());
        
        setAvailableChannelJList(view.getSnapAvailableChannelJList(), regionSettings.getSnapChannelArray());
        setUsedChannelJList(view.getSnapUsedChannelJList(), regionSettings.getSnapChannelArray());
        
        setAvailableChannelJList(view.getChannelOrderJList(), new ArrayList());
    }
    
    public void insertTableRow() {
        if (acquisitionSettings.getRegionSettingsArray().size() > totalRegionNum) {
            if (view.getRegionTable().getValueAt(totalRegionNum, 0) == null  || ((int) view.getRegionTable().getValueAt(totalRegionNum, 0) - 1 != fishNum & (int) view.getRegionTable().getValueAt(totalRegionNum, 1) - 1 != fishRegionNum)) {
                int regionNumImages = calculateRegionNumImages(regionSettings);
                
                Vector vector = new Vector(100);
                vector.add(acquisitionSettings.getRegionSettings(totalRegionNum).getFishNumber() + 1);
                vector.add(acquisitionSettings.getRegionSettings(totalRegionNum).getRegionNumber() + 1);
                vector.add(acquisitionSettings.getRegionSettings(totalRegionNum).getXPosition());
                vector.add(acquisitionSettings.getRegionSettings(totalRegionNum).getYPosition());
                vector.add(acquisitionSettings.getRegionSettings(totalRegionNum).getZPosition());
                vector.add(acquisitionSettings.getRegionSettings(totalRegionNum).getZStackBoolean());
                vector.add(acquisitionSettings.getRegionSettings(totalRegionNum).getZStartPosition());
                vector.add(acquisitionSettings.getRegionSettings(totalRegionNum).getZEndPosition());
                vector.add(acquisitionSettings.getRegionSettings(totalRegionNum).getStepSize());
                vector.add(acquisitionSettings.getRegionSettings(totalRegionNum).getZStackChannelArray());
                vector.add(acquisitionSettings.getRegionSettings(totalRegionNum).getVideoBoolean());
                vector.add(acquisitionSettings.getRegionSettings(totalRegionNum).getVideoDurationInSeconds());
                vector.add(acquisitionSettings.getRegionSettings(totalRegionNum).getVideoExposureTime());
                vector.add(acquisitionSettings.getRegionSettings(totalRegionNum).getVideoChannelArray());
                vector.add(acquisitionSettings.getRegionSettings(totalRegionNum).getSnapBoolean());
                vector.add(acquisitionSettings.getRegionSettings(totalRegionNum).getSnapChannelArray());
                vector.add(regionNumImages);
            
                DefaultTableModel model = (DefaultTableModel) view.getRegionTable().getModel();
                model.insertRow(totalRegionNum, vector);
                view.getRegionTable().setModel(model);
            }
        }
    }
    
    public void setTableRow() {
        if ((int) view.getRegionTable().getValueAt(totalRegionNum, 0) - 1 == fishNum & (int) view.getRegionTable().getValueAt(totalRegionNum, 1) - 1 == fishRegionNum) {
            int regionNumImages = calculateRegionNumImages(regionSettings);
            
            view.getRegionTable().setValueAt(regionSettings.getFishNumber() + 1, totalRegionNum, 0);
            view.getRegionTable().setValueAt(regionSettings.getRegionNumber() + 1, totalRegionNum, 1);
            view.getRegionTable().setValueAt(regionSettings.getXPosition(), totalRegionNum, 2);
            view.getRegionTable().setValueAt(regionSettings.getYPosition(), totalRegionNum, 3);
            view.getRegionTable().setValueAt(regionSettings.getZPosition(), totalRegionNum, 4);
            view.getRegionTable().setValueAt(regionSettings.getZStackBoolean(), totalRegionNum, 5);
            view.getRegionTable().setValueAt(regionSettings.getZStartPosition(), totalRegionNum, 6);
            view.getRegionTable().setValueAt(regionSettings.getZEndPosition(), totalRegionNum, 7);
            view.getRegionTable().setValueAt(regionSettings.getStepSize(), totalRegionNum, 8);
            view.getRegionTable().setValueAt(regionSettings.getZStackChannelArray(), totalRegionNum, 9);
            view.getRegionTable().setValueAt(regionSettings.getVideoBoolean(), totalRegionNum, 10);
            view.getRegionTable().setValueAt(regionSettings.getVideoDurationInSeconds(), totalRegionNum, 11);
            view.getRegionTable().setValueAt(regionSettings.getVideoExposureTime(), totalRegionNum, 12);
            view.getRegionTable().setValueAt(regionSettings.getVideoChannelArray(), totalRegionNum, 13);
            view.getRegionTable().setValueAt(regionSettings.getSnapBoolean(), totalRegionNum, 14);
            view.getRegionTable().setValueAt(regionSettings.getSnapChannelArray(), totalRegionNum, 15);
            view.getRegionTable().setValueAt(regionNumImages, totalRegionNum, 16);
        }
    }
    
    public void setTable() {
        DefaultTableModel model = (DefaultTableModel) view.getRegionTable().getModel();
        model.removeRow(acquisitionSettings.getRegionSettingsArray().size());
        
        for (int rowNumber = 0; rowNumber < acquisitionSettings.getRegionSettingsArray().size(); rowNumber++) {
            model.removeRow(rowNumber);
            
            RegionSettings settings = new RegionSettings();
            settings = acquisitionSettings.getRegionSettings(rowNumber);
            
            int regionNumImages = calculateRegionNumImages(settings);
            
            Vector vector = new Vector(100);
            vector.add(settings.getFishNumber() + 1);
            vector.add(settings.getRegionNumber() + 1);
            vector.add(settings.getXPosition());
            vector.add(settings.getYPosition());
            vector.add(settings.getZPosition());
            vector.add(settings.getZStackBoolean());
            vector.add(settings.getZStartPosition());
            vector.add(settings.getZEndPosition());
            vector.add(settings.getStepSize());
            vector.add(settings.getZStackChannelArray());
            vector.add(settings.getVideoBoolean());
            vector.add(settings.getVideoDurationInSeconds());
            vector.add(settings.getVideoExposureTime());
            vector.add(settings.getVideoChannelArray());
            vector.add(settings.getSnapBoolean());
            vector.add(settings.getSnapChannelArray());
            vector.add(regionNumImages);
            
            model.insertRow(rowNumber, vector);
        }
        
        view.getRegionTable().setModel(model);
    }
        
    private void initListeners() {
        view.getXField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                xFieldKeyReleased(e);
            }
        });
        
        view.getYField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                yFieldKeyReleased(e);
            }
        });
        
        view.getZField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                zFieldKeyReleased(e);
            }
        });
        
        view.getZStartField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                zStartFieldKeyReleased(e);
            }
        });
        
        view.getZEndField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                zEndFieldKeyReleased(e);
            }
        });
        
        view.getStepSizeField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                stepSizeFieldKeyReleased(e);
            }
        });
        
        view.getVideoExposureField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                videoExposureFieldKeyReleased(e);
            }
        });
        
        view.getVideoDurationField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                videoDurationFieldKeyReleased(e);
            }
        });
        
        view.getNumTimePointsField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                numTimePointsFieldKeyReleased(e);
            }
        });
        
        view.getIntervalField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                intervalFieldKeyReleased(e);
            }
        });
        
        view.getSetRegionButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    setRegionButtonActionPerformed(e);
                } catch (Exception ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        view.getRemoveRegionButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeRegionButtonActionPerformed(e);
            }
        });
        
        view.getPreviousFishButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                previousFishButtonActionPerformed(e);
            }
        });
        
        view.getPreviousRegionButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                previousRegionButtonActionPerformed(e);
            }
        });
        
        view.getNextRegionButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextRegionButtonActionPerformed(e);
            }
        });
        
        view.getNextFishButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextFishButtonActionPerformed(e);
            }
        });
        
        view.getZStartButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zStartButtonActionPerformed(e);
            }
        });
        
        view.getZEndButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zEndButtonActionPerformed(e);
            }
        });
        
        view.getZStackAddChannelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zStackAddChannelButtonActionPerformed(e);
            }
        });
        
        view.getZStackRemoveChannelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zStackRemoveChannelButtonActionPerformed(e);
            }
        });        
        
        view.getVideoAddChannelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                videoAddChannelButtonActionPerformed(e);
            }
        });
        
        view.getVideoRemoveChannelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                videoRemoveChannelButtonActionPerformed(e);
            }
        });      
        
        view.getSnapAddChannelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                snapAddChannelButtonActionPerformed(e);
            }
        });
        
        view.getSnapRemoveChannelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                snapRemoveChannelButtonActionPerformed(e);
            }
        });
        
        view.getAcquisitionSetupButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                acquisitionSetupButtonActionPerformed(e);
            }
        });
        
        view.getChannelOrderJListMoveUpButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                channelOrderJListMoveUpButtonActionPerformed(e);
            }
        });
        
        view.getChannelOrderJListMoveDownButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                channelOrderJListMoveDownButtonActionPerformed(e);
            }
        });
        
        view.getBrowseButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browseButtonActionPerformed(e);
            }
        });
        
        view.getStartAcquisitionButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startAcquisitionButtonActionPerformed(e);
            }
        });
        
        view.getZStackCheckBox().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zStackCheckBoxActionPerformed(e);
            }
        });
        
        view.getVideoCheckBox().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                videoCheckBoxActionPerformed(e);
            }
        });
        
        view.getSnapCheckBox().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                snapCheckBoxActionPerformed(e);
            }
        });
        
        view.getTimePointsCheckBox().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timePointsCheckBoxActionPerformed(e);
            }
        });
        
        view.getAbortButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abortButtonActionPerformed(e);
            }
        });
        
        view.getAbortYesButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abortYesButtonActionPerformed(e);
            }
        });
        
        view.getAbortCancelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abortCancelButtonActionPerformed(e);
            }
        });
    }
    
    private void xFieldKeyReleased(java.awt.event.KeyEvent e) {
        regionSettings.setXPosition(Integer.valueOf(view.getXField().getText()));
    }
    
    private void yFieldKeyReleased(java.awt.event.KeyEvent evt) {
        regionSettings.setYPosition(Integer.valueOf(view.getYField().getText()));
    }
    
    private void zFieldKeyReleased(java.awt.event.KeyEvent evt) {                                      
        regionSettings.setZPosition(Integer.valueOf(view.getZField().getText()));
    }
    
     private void removeRegionButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        acquisitionSettings.removeRegionSettings(regionSettings, fishNum, fishRegionNum, totalRegionNum);
        setTable();

        if (acquisitionSettings.getRegionSettingsArray().size() == totalRegionNum & acquisitionSettings.getRegionSettingsArray().size() >= 1) {
            totalRegionNum--;
            regionSettings = acquisitionSettings.getRegionSettings(totalRegionNum);
            fishNum = regionSettings.getFishNumber();
            fishRegionNum = regionSettings.getRegionNumber();
            
            if (acquisitionSettings.getRegionSettingsArray().size() == 1) {
                view.getPreviousRegionButton().setEnabled(false);
            }
        }
        

        if (acquisitionSettings.getRegionSettingsArray().size() == 0) {
            fishNum = 0;
            fishRegionNum = 0;
            totalRegionNum = 0;

            regionSettings = new RegionSettings();
            view.getNextRegionButton().setEnabled(false);
            view.getPreviousRegionButton().setEnabled(false);
        }
        if (acquisitionSettings.getRegionSettingsArray().size() > totalRegionNum) {
            regionSettings = acquisitionSettings.getRegionSettings(totalRegionNum);
            if (regionSettings.getFishNumber() > fishNum) {
                totalRegionNum--;
                regionSettings = acquisitionSettings.getRegionSettings(totalRegionNum);
            }
            fishNum = regionSettings.getFishNumber();
            fishRegionNum = regionSettings.getRegionNumber();
        }

        if (fishRegionNum > 0) {
            view.getPreviousRegionButton().setEnabled(true);
        }

        regionSettingsGUIChange();
    }                                                  

    private void stepSizeFieldKeyReleased(java.awt.event.KeyEvent evt) {                                          
        int stepSize = (int) Math.round(Double.valueOf(view.getStepSizeField().getText()));
        regionSettings.setStepSize(stepSize);
        setTableRow();
        setImageNum();
        setAcquisitionTimeLabels();
    }                                         

    private void snapRemoveChannelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                        
        DefaultListModel allModel = new DefaultListModel();
        allModel = (DefaultListModel) view.getSnapAvailableChannelJList().getModel();
        List<String> selected = view.getSnapUsedChannelJList().getSelectedValuesList();
        
        for (String channel : selected) {
            allModel.addElement(channel);
        }
        
        view.getSnapAvailableChannelJList().setModel(allModel);

        DefaultListModel usedModel = new DefaultListModel();
        usedModel =  (DefaultListModel) view.getSnapUsedChannelJList().getModel();
        for (String channel : selected) {
            usedModel.removeElement(channel);
        }

        view.getSnapUsedChannelJList().setModel(usedModel);

        ArrayList<String> usedChannelArray = new ArrayList();
        for (int channelNum = 0; channelNum < usedModel.getSize(); channelNum++) {
            usedChannelArray.add((String) usedModel.get(channelNum));
        }
        regionSettings.setSnapChannelArray(usedChannelArray);

        setTableRow();
        setImageNum();
        updateRowHeights();
        setAcquisitionTimeLabels();
    }                                                       

    private void snapAddChannelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        DefaultListModel usedModel = new DefaultListModel();
        usedModel = (DefaultListModel) view.getSnapUsedChannelJList().getModel();
        List<String> selected = view.getSnapAvailableChannelJList().getSelectedValuesList();
        
        for (String channel : selected) {
            usedModel.addElement(channel);
        }
        
        view.getSnapUsedChannelJList().setModel(usedModel);

        ArrayList<String> usedChannelArray = new ArrayList();
        for (int channelNum = 0; channelNum < usedModel.getSize(); channelNum++) {
            usedChannelArray.add((String) usedModel.get(channelNum));
        }
        regionSettings.setSnapChannelArray(usedChannelArray);

        DefaultListModel allModel = new DefaultListModel();
        allModel =  (DefaultListModel) view.getSnapAvailableChannelJList().getModel();
        for (String channel : selected) {
            allModel.removeElement(channel);
        }

        view.getSnapAvailableChannelJList().setModel(allModel);

        setTableRow();
        setImageNum();
        updateRowHeights();
        setAcquisitionTimeLabels();
    }                                                    

    private void snapCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                          
        regionSettings.setSnapBoolean(view.getSnapCheckBox().isSelected());
        
        view.getSnapAddChannelButton().setEnabled(regionSettings.getSnapBoolean());
        view.getSnapRemoveChannelButton().setEnabled(regionSettings.getSnapBoolean());
        
        setTableRow();
        setImageNum();
        setAcquisitionTimeLabels();
    }                                         

    private void snapMoveDownButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        DefaultListModel usedModel = (DefaultListModel) view.getSnapUsedChannelJList().getModel();

        String movedChannel = (String) view.getSnapUsedChannelJList().getSelectedValue();
        int movedChannelIndex = view.getSnapUsedChannelJList().getSelectedIndex();

        int channelBelowIndex = movedChannelIndex + 1;
        String channelBelow = (String) usedModel.getElementAt(channelBelowIndex);

        usedModel.setElementAt(channelBelow, movedChannelIndex);
        usedModel.setElementAt(movedChannel, channelBelowIndex);
        view.getSnapUsedChannelJList().setModel(usedModel);

        ArrayList<String> usedChannelArray = new ArrayList();
        for (int channelNum = 0; channelNum < usedModel.getSize(); channelNum++) {
            usedChannelArray.add((String) usedModel.get(channelNum));
        }
        regionSettings.setSnapChannelArray(usedChannelArray);
    }                                                  

    private void snapMoveUpButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        DefaultListModel usedModel = (DefaultListModel) view.getSnapUsedChannelJList().getModel();

        String movedChannel = (String) view.getSnapUsedChannelJList().getSelectedValue();
        int movedChannelIndex = view.getSnapUsedChannelJList().getSelectedIndex();

        int channelAboveIndex = movedChannelIndex - 1;
        String channelAbove = (String) usedModel.getElementAt(channelAboveIndex);

        usedModel.setElementAt(channelAbove, movedChannelIndex);
        usedModel.setElementAt(movedChannel, channelAboveIndex);
        view.getSnapUsedChannelJList().setModel(usedModel);

        ArrayList<String> usedChannelArray = new ArrayList();
        for (int channelNum = 0; channelNum < usedModel.getSize(); channelNum++) {
            usedChannelArray.add((String) usedModel.get(channelNum));
        }
        regionSettings.setSnapChannelArray(usedChannelArray);
    }                                                                                       

    private void nextFishButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        fishNum++;
        fishRegionNum = 0;

        regionSettings = new RegionSettings();

        boolean foundBoolean = false;
        for(RegionSettings regionSettingsArrayElement: acquisitionSettings.getRegionSettingsArray()) {
            int regionSettingsFishNum = regionSettingsArrayElement.getFishNumber();
            int regionSettingsRegionNum = regionSettingsArrayElement.getRegionNumber();

            if (regionSettingsFishNum == fishNum & regionSettingsRegionNum == fishRegionNum) {
                foundBoolean = true;
                regionSettings = regionSettingsArrayElement;
                totalRegionNum = acquisitionSettings.getRegionSettingsArray().indexOf(regionSettingsArrayElement);
                view.getNextRegionButton().setEnabled(true);

                try {
                    hardwareCommands.moveStage(regionSettings.getXPosition(), regionSettings.getYPosition(), regionSettings.getZPosition());
                } catch (Exception ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (!foundBoolean) {
            totalRegionNum = acquisitionSettings.getRegionSettingsArray().size();
            view.getNextRegionButton().setEnabled(false);
            view.getNextFishButton().setEnabled(false);
        }

        view.getPreviousRegionButton().setEnabled(false);
        view.getPreviousFishButton().setEnabled(true);

        regionSettings.setFishNumber(fishNum);
        regionSettings.setRegionNumber(fishRegionNum);

        regionSettingsGUIChange();

    }                                              

    private void previousFishButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        fishNum--;
        fishRegionNum = 0;

        regionSettings = new RegionSettings();

        for(RegionSettings regionSettingsArrayElement: acquisitionSettings.getRegionSettingsArray()) {
            int regionSettingsFishNum = regionSettingsArrayElement.getFishNumber();
            int regionSettingsRegionNum = regionSettingsArrayElement.getRegionNumber();

            if (regionSettingsFishNum == fishNum & regionSettingsRegionNum == fishRegionNum) {
                regionSettings = regionSettingsArrayElement;
                totalRegionNum = acquisitionSettings.getRegionSettingsArray().indexOf(regionSettingsArrayElement);
            }
        }

        try {
            hardwareCommands.moveStage(regionSettings.getXPosition(), regionSettings.getYPosition(), regionSettings.getZPosition());
        } catch (Exception ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }

        view.getPreviousRegionButton().setEnabled(false);
        view.getNextRegionButton().setEnabled(true);
        view.getNextFishButton().setEnabled(true);

        if (fishNum == 0) {
            view.getPreviousFishButton().setEnabled(false);
        }

        regionSettings.setFishNumber(fishNum);
        regionSettings.setRegionNumber(fishRegionNum);

        view.getRemoveRegionButton().setEnabled(true);

        regionSettingsGUIChange();

    }                                                  

    private void nextRegionButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        totalRegionNum++;
        fishRegionNum++;

        regionSettings = new RegionSettings();

        if (acquisitionSettings.getRegionSettingsArray().size() > totalRegionNum) {
            if (acquisitionSettings.getRegionSettings(totalRegionNum).getFishNumber() == fishNum) {
                regionSettings = acquisitionSettings.getRegionSettings(totalRegionNum);

                try {
                    hardwareCommands.moveStage(regionSettings.getXPosition(), regionSettings.getYPosition(), regionSettings.getZPosition());
                } catch (Exception ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                view.getNextRegionButton().setEnabled(false);
                view.getRemoveRegionButton().setEnabled(false);
            }
        }

        if (acquisitionSettings.getRegionSettingsArray().size() <= totalRegionNum) {
            view.getNextRegionButton().setEnabled(false);
            view.getRemoveRegionButton().setEnabled(false);
        }

        view.getPreviousRegionButton().setEnabled(true);

        regionSettings.setFishNumber(fishNum);
        regionSettings.setRegionNumber(fishRegionNum);

        regionSettingsGUIChange();
    }                                                

    private void acquisitionSetupButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        view.getAcquisitionSettingsFrame().setVisible(true);
        setImageNum();
        setAcquisitionTimeLabels();
    }                                                      

    private void videoDurationFieldKeyReleased(java.awt.event.KeyEvent evt) {                                               
        double videoDuration = Double.valueOf(view.getVideoDurationField().getText());
        regionSettings.setVideoDurationInSeconds(videoDuration);
        setTableRow();
        setImageNum();
        setAcquisitionTimeLabels();
    }                                              

    private void videoExposureFieldKeyReleased(java.awt.event.KeyEvent evt) {                                               
        double videoExposure = Double.valueOf(view.getVideoExposureField().getText());
        regionSettings.setVideoExposureTime(videoExposure);
        setTableRow();
        setImageNum();
        setAcquisitionTimeLabels();
    }                                              

    private void videoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                           
        regionSettings.setVideoBoolean(view.getVideoCheckBox().isSelected());
        
        view.getVideoDurationField().setEnabled(regionSettings.getVideoBoolean());
        view.getVideoExposureField().setEnabled(regionSettings.getVideoBoolean());
        view.getVideoAddChannelButton().setEnabled(regionSettings.getVideoBoolean());
        view.getVideoRemoveChannelButton().setEnabled(regionSettings.getVideoBoolean());
        
        setTableRow();
        setImageNum();
        setAcquisitionTimeLabels();
    }                                          

    private void videoMoveDownButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        DefaultListModel usedModel = (DefaultListModel) view.getVideoUsedChannelJList().getModel();

        String movedChannel = (String) view.getVideoUsedChannelJList().getSelectedValue();
        int movedChannelIndex = view.getVideoUsedChannelJList().getSelectedIndex();

        int channelBelowIndex = movedChannelIndex + 1;
        String channelBelow = (String) usedModel.getElementAt(channelBelowIndex);

        usedModel.setElementAt(channelBelow, movedChannelIndex);
        usedModel.setElementAt(movedChannel, channelBelowIndex);
        view.getVideoUsedChannelJList().setModel(usedModel);

        ArrayList<String> usedChannelArray = new ArrayList();
        for (int channelNum = 0; channelNum < usedModel.getSize(); channelNum++) {
            usedChannelArray.add((String) usedModel.get(channelNum));
        }
        regionSettings.setVideoChannelArray(usedChannelArray);
    }                                                   

    private void videoMoveUpButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        DefaultListModel usedModel = (DefaultListModel) view.getVideoUsedChannelJList().getModel();

        String movedChannel = (String) view.getVideoUsedChannelJList().getSelectedValue();
        int movedChannelIndex = view.getVideoUsedChannelJList().getSelectedIndex();

        int channelAboveIndex = movedChannelIndex - 1;
        String channelAbove = (String) usedModel.getElementAt(channelAboveIndex);

        usedModel.setElementAt(channelAbove, movedChannelIndex);
        usedModel.setElementAt(movedChannel, channelAboveIndex);
        view.getVideoUsedChannelJList().setModel(usedModel);

        ArrayList<String> usedChannelArray = new ArrayList();
        for (int channelNum = 0; channelNum < usedModel.getSize(); channelNum++) {
            usedChannelArray.add((String) usedModel.get(channelNum));
        }
        regionSettings.setVideoChannelArray(usedChannelArray);
    }                                                 

    private void videoRemoveChannelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                         
        DefaultListModel allModel = new DefaultListModel();
        allModel = (DefaultListModel) view.getVideoAvailableChannelJList().getModel();
        List<String> selected = view.getVideoUsedChannelJList().getSelectedValuesList();
        
        for (String channel : selected) {
            allModel.addElement(channel);
        }
        
        view.getVideoAvailableChannelJList().setModel(allModel);

        DefaultListModel usedModel = new DefaultListModel();
        usedModel =  (DefaultListModel) view.getVideoUsedChannelJList().getModel();
        for (String channel : selected) {
            usedModel.removeElement(channel);
        }

        view.getVideoUsedChannelJList().setModel(usedModel);

        ArrayList<String> usedChannelArray = new ArrayList();
        for (int channelNum = 0; channelNum < usedModel.getSize(); channelNum++) {
            usedChannelArray.add((String) usedModel.get(channelNum));
        }
        regionSettings.setVideoChannelArray(usedChannelArray);

        setTableRow();
        setImageNum();
        updateRowHeights();
        setAcquisitionTimeLabels();
    }                                                        

    private void videoAddChannelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        DefaultListModel usedModel = new DefaultListModel();
        usedModel = (DefaultListModel) view.getVideoUsedChannelJList().getModel();
        List<String> selected = view.getVideoAvailableChannelJList().getSelectedValuesList();
        
        for (String channel : selected) {
            usedModel.addElement(channel);
        }
        
        view.getVideoUsedChannelJList().setModel(usedModel);

        ArrayList<String> usedChannelArray = new ArrayList();
        for (int channelNum = 0; channelNum < usedModel.getSize(); channelNum++) {
            usedChannelArray.add((String) usedModel.get(channelNum));
        }
        regionSettings.setVideoChannelArray(usedChannelArray);

        DefaultListModel allModel = new DefaultListModel();
        allModel =  (DefaultListModel) view.getVideoAvailableChannelJList().getModel();
        for (String channel : selected) {
            allModel.removeElement(channel);
        }

        view.getVideoAvailableChannelJList().setModel(allModel);

        setTableRow();
        setImageNum();
        updateRowHeights();
        setAcquisitionTimeLabels();
    }                                                     

    private void zStackMoveDownButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        DefaultListModel usedModel = (DefaultListModel) view.getZStackUsedChannelJList().getModel();

        String movedChannel = (String) view.getZStackUsedChannelJList().getSelectedValue();
        int movedChannelIndex = view.getZStackUsedChannelJList().getSelectedIndex();

        int channelBelowIndex = movedChannelIndex + 1;
        String channelBelow = (String) usedModel.getElementAt(channelBelowIndex);

        usedModel.setElementAt(channelBelow, movedChannelIndex);
        usedModel.setElementAt(movedChannel, channelBelowIndex);
        view.getZStackUsedChannelJList().setModel(usedModel);

        ArrayList<String> usedChannelArray = new ArrayList();
        for (int channelNum = 0; channelNum < usedModel.getSize(); channelNum++) {
            usedChannelArray.add((String) usedModel.get(channelNum));
        }
        regionSettings.setZStackChannelArray(usedChannelArray);
    }                                                    

    private void zStackMoveUpButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        DefaultListModel usedModel = (DefaultListModel) view.getZStackUsedChannelJList().getModel();

        String movedChannel = (String) view.getZStackUsedChannelJList().getSelectedValue();
        int movedChannelIndex = view.getZStackUsedChannelJList().getSelectedIndex();

        int channelAboveIndex = movedChannelIndex - 1;
        String channelAbove = (String) usedModel.getElementAt(channelAboveIndex);

        usedModel.setElementAt(channelAbove, movedChannelIndex);
        usedModel.setElementAt(movedChannel, channelAboveIndex);
        view.getZStackUsedChannelJList().setModel(usedModel);

        ArrayList<String> usedChannelArray = new ArrayList();
        for (int channelNum = 0; channelNum < usedModel.getSize(); channelNum++) {
            usedChannelArray.add((String) usedModel.get(channelNum));
        }
        regionSettings.setZStackChannelArray(usedChannelArray);

    }                                                  

    private void zStackRemoveChannelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                          
        DefaultListModel allModel = new DefaultListModel();
        allModel = (DefaultListModel) view.getZStackAvailableChannelJList().getModel();
        List<String> selected = view.getZStackUsedChannelJList().getSelectedValuesList();
        
        for (String channel : selected) {
            allModel.addElement(channel);
        }
        
        view.getZStackAvailableChannelJList().setModel(allModel);

        DefaultListModel usedModel = new DefaultListModel();
        usedModel =  (DefaultListModel) view.getZStackUsedChannelJList().getModel();
        for (String channel : selected) {
            usedModel.removeElement(channel);
        }

        view.getZStackUsedChannelJList().setModel(usedModel);

        ArrayList<String> usedChannelArray = new ArrayList();
        for (int channelNum = 0; channelNum < usedModel.getSize(); channelNum++) {
            usedChannelArray.add((String) usedModel.get(channelNum));
        }
        regionSettings.setZStackChannelArray(usedChannelArray);

        setTableRow();
        setImageNum();
        updateRowHeights();
        setAcquisitionTimeLabels();
    }                                                         

    private void zStackAddChannelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        DefaultListModel usedModel = new DefaultListModel();
        usedModel = (DefaultListModel) view.getZStackUsedChannelJList().getModel();
        List<String> selected = view.getZStackAvailableChannelJList().getSelectedValuesList();
        
        for (String channel : selected) {
            usedModel.addElement(channel);
        }
        
        view.getZStackUsedChannelJList().setModel(usedModel);

        ArrayList<String> usedChannelArray = new ArrayList();
        for (int channelNum = 0; channelNum < usedModel.getSize(); channelNum++) {
            usedChannelArray.add((String) usedModel.get(channelNum));
        }
        regionSettings.setZStackChannelArray(usedChannelArray);

        DefaultListModel allModel = new DefaultListModel();
        allModel =  (DefaultListModel) view.getZStackAvailableChannelJList().getModel();
        for (String channel : selected) {
            allModel.removeElement(channel);
        }

        view.getZStackAvailableChannelJList().setModel(allModel);

        setTableRow();
        setImageNum();
        updateRowHeights();
        setAcquisitionTimeLabels();
    }                                                      

    private void zEndButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        int zEndPosition = (int) hardwareCommands.getZPosition();
        System.out.println(zEndPosition);
        regionSettings.setZEndPosition(zEndPosition);
        System.out.println(regionSettings.getZEndPosition());
        view.getZEndField().setText(String.valueOf(zEndPosition));
        
        setTableRow();
        setImageNum();
        setAcquisitionTimeLabels();
    }                                          

    private void zStartButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        int zStartPosition = hardwareCommands.getZPosition();
        System.out.println(zStartPosition);
        regionSettings.setZStartPosition((int) zStartPosition);
        System.out.println(regionSettings.getZStartPosition());
        view.getZStartField().setText(String.valueOf(zStartPosition));
        
        setTableRow();
        setImageNum();
        setAcquisitionTimeLabels();
    }                                            

    private void zStartFieldKeyReleased(java.awt.event.KeyEvent evt) {                                        
        int zStartPosition = Integer.valueOf(view.getZStartField().getText());
        regionSettings.setZStartPosition((int) zStartPosition);
        
        setTableRow();
        setImageNum();
        setAcquisitionTimeLabels();
    }                                       

    private void zEndFieldKeyReleased(java.awt.event.KeyEvent evt) {                                      
        int zEndPosition = Integer.valueOf(view.getZEndField().getText());
        regionSettings.setZEndPosition((int) zEndPosition);
        
        setTableRow();
        setImageNum();
        setAcquisitionTimeLabels();
    }                                     

    private void zStackCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                            
        regionSettings.setZStackBoolean(view.getZStackCheckBox().isSelected());
        
        view.getZStartButton().setEnabled(regionSettings.getZStackBoolean());
        view.getZEndButton().setEnabled(regionSettings.getZStackBoolean());
        view.getZStartField().setEnabled(regionSettings.getZStackBoolean());
        view.getZEndField().setEnabled(regionSettings.getZStackBoolean());
        view.getStepSizeField().setEnabled(regionSettings.getZStackBoolean());
        view.getZStackAddChannelButton().setEnabled(regionSettings.getZStackBoolean());
        view.getZStackRemoveChannelButton().setEnabled(regionSettings.getZStackBoolean());
        
        setTableRow();
        setImageNum();
        setAcquisitionTimeLabels();
    }                                           

    private void setRegionButtonActionPerformed(java.awt.event.ActionEvent evt) throws Exception {                                                
        int xPos = hardwareCommands.getXPosition();
        int yPos = hardwareCommands.getYPosition();
        int zPos = hardwareCommands.getZPosition();
        
        regionSettings.setXPosition(xPos);
        regionSettings.setYPosition(yPos);
        regionSettings.setZPosition(zPos);

        view.getXField().setText(String.valueOf(xPos));
        view.getYField().setText(String.valueOf(yPos));
        view.getZField().setText(String.valueOf(zPos));
        
        acquisitionSettings.addRegionSettings(regionSettings, fishNum, fishRegionNum, totalRegionNum);
        view.getNextRegionButton().setEnabled(true);
        view.getNextFishButton().setEnabled(true);
        view.getRemoveRegionButton().setEnabled(true);
        
        insertTableRow();
        setTableRow();
        setImageNum();
        setAcquisitionTimeLabels();
        regionSettingsGUIChange();
    }                                               

    private void previousRegionButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        fishRegionNum--;
        totalRegionNum--;

        regionSettings = new RegionSettings();

        regionSettings = acquisitionSettings.getRegionSettings(totalRegionNum);

        try {
            hardwareCommands.moveStage(regionSettings.getXPosition(), regionSettings.getYPosition(), regionSettings.getZPosition());
        } catch (Exception ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (fishRegionNum == 0) {
            view.getPreviousRegionButton().setEnabled(false);
        }

        if (acquisitionSettings.getRegionSettingsArray().size() >= totalRegionNum + 1) {
            view.getNextRegionButton().setEnabled(true);
        }

        view.getRemoveRegionButton().setEnabled(true);

        regionSettingsGUIChange();
    }                                                    

    private void timePointsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                
        acquisitionSettings.setTimePointsBoolean(view.getTimePointsCheckBox().isSelected());
        view.getIntervalField().setEnabled(view.getTimePointsCheckBox().isSelected());
        view.getNumTimePointsField().setEnabled(view.getTimePointsCheckBox().isSelected());
        
        setImageNum();
        setAcquisitionTimeLabels();
    }                                               

    private void numTimePointsFieldKeyReleased(java.awt.event.KeyEvent evt) {                                               
        acquisitionSettings.setNumTimePoints(Integer.valueOf(view.getNumTimePointsField().getText()));
        
        setImageNum();
        setAcquisitionTimeLabels();
    }                                              

    private void intervalFieldKeyReleased(java.awt.event.KeyEvent evt) {                                          
        acquisitionSettings.setTimePointsInterval(Integer.valueOf(view.getIntervalField().getText()));
    }                                         

    private void channelOrderJListMoveUpButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                              
        DefaultListModel usedModel = (DefaultListModel) view.getChannelOrderJList().getModel();

        String movedChannel = (String) view.getChannelOrderJList().getSelectedValue();
        int movedChannelIndex = view.getChannelOrderJList().getSelectedIndex();

        int channelAboveIndex = movedChannelIndex - 1;
        String channelAbove = (String) usedModel.getElementAt(channelAboveIndex);

        usedModel.setElementAt(channelAbove, movedChannelIndex);
        usedModel.setElementAt(movedChannel, channelAboveIndex);
        view.getChannelOrderJList().setModel(usedModel);

        ArrayList<String> usedChannelArray = new ArrayList();
        for (int channelNum = 0; channelNum < usedModel.getSize(); channelNum++) {
            usedChannelArray.add((String) usedModel.get(channelNum));
        }
        
        acquisitionSettings.setChannelOrderArray(usedChannelArray);
    }                                                             

    private void channelOrderJListMoveDownButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                                
        DefaultListModel usedModel = (DefaultListModel) view.getChannelOrderJList().getModel();

        String movedChannel = (String) view.getChannelOrderJList().getSelectedValue();
        int movedChannelIndex = view.getChannelOrderJList().getSelectedIndex();

        int channelBelowIndex = movedChannelIndex + 1;
        String channelBelow = (String) usedModel.getElementAt(channelBelowIndex);

        usedModel.setElementAt(channelBelow, movedChannelIndex);
        usedModel.setElementAt(movedChannel, channelBelowIndex);
        view.getChannelOrderJList().setModel(usedModel);

        ArrayList<String> usedChannelArray = new ArrayList();
        for (int channelNum = 0; channelNum < usedModel.getSize(); channelNum++) {
            usedChannelArray.add((String) usedModel.get(channelNum));
        }
        acquisitionSettings.setChannelOrderArray(usedChannelArray);
    }                                                               

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(acquisitionSettings.getDirectory()).getParentFile());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showSaveDialog(null);
        String directory = fileChooser.getSelectedFile().getPath();
        view.getDirectoryField().setText(directory);
        directory.replace('\\','/');
        acquisitionSettings.setDirectory(directory);
        
        view.getStartAcquisitionButton().setEnabled(true);
    }                                            

    private void startAcquisitionButtonActionPerformed(java.awt.event.ActionEvent evt) {
        view.getAcquisitionFrame().setVisible(true);
        view.getStartAcquisitionButton().setEnabled(false);
        mm_.getSnapLiveManager().setLiveMode(false);
                
        DefaultListModel usedModel = (DefaultListModel) view.getChannelOrderJList().getModel();
        ArrayList<String> usedChannelArray = new ArrayList();
        for (int channelNum = 0; channelNum < usedModel.getSize(); channelNum++) {
            usedChannelArray.add((String) usedModel.get(channelNum));
        }
        acquisitionSettings.setChannelOrderArray(usedChannelArray);
        
        acquisition = new Acquisition(acquisitionSettings, view, mm_);
        thread = new Thread(acquisition);
        thread.start();
    }
    
    private void abortButtonActionPerformed(java.awt.event.ActionEvent evt) {
       view.getAbortFrame().setVisible(true);
    }
    
    private void abortYesButtonActionPerformed(java.awt.event.ActionEvent evt) {
       view.getAcquisitionLabel().setText("Aborting acquisition...");
       acquisition.setAbortBoolean(true);
       thread.interrupt();
       view.getAbortFrame().setVisible(false);
    }
    
    private void abortCancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
       view.getAbortFrame().setVisible(false);
    }
}
        
    
