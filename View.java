/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.micromanager.ScanPlugin;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;

/** This class creates and formats the GUI elements but does not directly control it. That is up to the Controller through getters and setters.
 *
 * 
 */
public class View extends javax.swing.JFrame {
    public View() {
        initComponents();
    }
    
    public JLabel getFishLabel() {
        return fishLabel;
    }
    
    public JLabel getRegionLabel() {
        return regionLabel;
    }
    
    public JLabel getAcquisitionFishLabel() {
        return acquisitionFishLabel;
    }
    
    public JLabel getAcquisitionTimePointLabel() {
        return acquisitionTimePointLabel;
    }
    
    public JLabel getAcquisitionRegionLabel() {
        return acquisitionRegionLabel;
    }
    
    public JLabel getAcquisitionLabel() {
        return acquisitionLabel;
    }
    
    public JLabel getAcquisitionTimeSingleLabel() {
        return acquisitionTimeSingleLabel;
    }
    
    public JLabel getTotalAcquisitionTimeLabel() {
        return totalAcquisitionTimeLabel;
    }
    
    public JTextField getXField() {
        return xField;
    }
    
    public JTextField getYField() {
        return yField;
    }
    
    public JTextField getZField() {
        return zField;
    }
    
    public JTextField getZStartField() {
        return zStartField;
    }
    
    public JTextField getZEndField() {
        return zEndField;
    }
    
    public JTextField getStepSizeField() {
        return stepSizeField;
    }
    
    public JTextField getVideoExposureField() {
        return videoExposureField;
    }
    
    public JTextField getVideoDurationField() {
        return videoDurationField;
    }
    
    public JTextField getNumTimePointsField() {
        return numTimePointsField;
    }
    
    public JTextField getIntervalField() {
        return intervalField;
    }
    
    public JTextField getNumImagesField() {
        return numImagesField;
    }
    
    public JTextField getMemoryField() {
        return memoryField;
    }
    
    public JTextField getDirectoryField() {
        return directoryField;
    }
    
    public JButton getSetRegionButton() {
        return setRegionButton;
    }
    
    public JButton getRemoveRegionButton() {
        return removeRegionButton;
    }
    
    public JButton getPreviousFishButton() {
        return previousFishButton;
    }
    
    public JButton getPreviousRegionButton() {
        return previousRegionButton;
    }
    
    public JButton getNextRegionButton() {
        return nextRegionButton;
    }
    
    public JButton getNextFishButton() {
        return nextFishButton;
    }
    
    public JButton getZStartButton() {
        return zStartButton;
    }
    
    public JButton getZEndButton() {
        return zEndButton;
    }
    
    public JButton getZStackAddChannelButton() {
        return zStackAddChannelButton;
    }
    
    public JButton getZStackRemoveChannelButton() {
        return zStackRemoveChannelButton;
    }
    
    public JButton getVideoAddChannelButton() {
        return videoAddChannelButton;
    }
    
    public JButton getVideoRemoveChannelButton() {
        return videoRemoveChannelButton;
    }
    
    public JButton getSnapAddChannelButton() {
        return snapAddChannelButton;
    }
    
    public JButton getSnapRemoveChannelButton() {
        return snapRemoveChannelButton;
    }
    
    public JButton getAcquisitionSetupButton() {
        return acquisitionSetupButton;
    }
    
    public JButton getChannelOrderJListMoveUpButton() {
        return channelOrderJListMoveUpButton;
    }
    
     public JButton getChannelOrderJListMoveDownButton() {
        return channelOrderJListMoveDownButton;
    }
    
    public JButton getBrowseButton() {
        return browseButton;
    }
    
    public JButton getStartAcquisitionButton() {
        return startAcquisitionButton;
    }
    
    public JButton getAbortButton() {
        return abortButton;
    }
    
    public JButton getAbortYesButton() {
        return abortYesButton;
    }
    
    public JButton getAbortCancelButton() {
        return abortCancelButton;
    }
    
    public JCheckBox getZStackCheckBox() {
        return zStackCheck;
    }
    
    public JCheckBox getVideoCheckBox() {
        return videoCheck;
    }
    
    public JCheckBox getSnapCheckBox() {
        return snapCheck;
    }
    
    public JCheckBox getTimePointsCheckBox() {
        return timePointsCheck;
    }
    
    public JList getZStackAvailableChannelJList() {
        return zStackAvailableChannelJList;
    }
    
    public JList getZStackUsedChannelJList() {
        return zStackUsedChannelJList;
    }
    
    public JList getVideoAvailableChannelJList() {
        return videoAvailableChannelJList;
    }
    
    public JList getVideoUsedChannelJList() {
        return videoUsedChannelJList;
    }
    
    public JList getSnapAvailableChannelJList() {
        return snapAvailableChannelJList;
    }
    
    public JList getSnapUsedChannelJList() {
        return snapUsedChannelJList;
    }
    
    public JList getChannelOrderJList() {
        return channelOrderJList;
    }
    
    public JTable getRegionTable() {
        return regionTable;
    }
    
    public JFrame getAcquisitionSettingsFrame() {
        return acquisitionSettingsFrame;
    }
    
    public JFrame getAcquisitionFrame() {
        return acquisitionFrame;
    }
    
    public JFrame getAbortFrame() {
        return abortFrame;
    }
    
    public JFrame getRegionSettingsFrame() {
        return regionSettingsFrame;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        acquisitionSettingsFrame = new javax.swing.JFrame();
        startAcquisitionButton = new javax.swing.JButton();
        numTimePointsField = new javax.swing.JTextField();
        timePointsCheck = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        intervalField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        numImagesField = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        memoryField = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        channelOrderJListMoveUpButton = new javax.swing.JButton();
        channelOrderJListMoveDownButton = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        channelOrderJList = new javax.swing.JList<>();
        SaveLocationLabel = new javax.swing.JLabel();
        directoryField = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        acquisitionTimeSingleLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        totalAcquisitionTimeLabel = new javax.swing.JLabel();
        acquisitionFrame = new javax.swing.JFrame();
        jLabel25 = new javax.swing.JLabel();
        abortButton = new javax.swing.JButton();
        acquisitionFishLabel = new javax.swing.JLabel();
        acquisitionTimePointLabel = new javax.swing.JLabel();
        acquisitionRegionLabel = new javax.swing.JLabel();
        acquisitionLabel = new javax.swing.JLabel();
        abortFrame = new javax.swing.JFrame();
        jLabel26 = new javax.swing.JLabel();
        abortYesButton = new javax.swing.JButton();
        abortCancelButton = new javax.swing.JButton();
        regionSettingsFrame = new javax.swing.JFrame();
        zField = new javax.swing.JTextField();
        setRegionButton = new javax.swing.JButton();
        yField = new javax.swing.JTextField();
        xField = new javax.swing.JTextField();
        fishLabel = new javax.swing.JLabel();
        zStackCheck = new javax.swing.JCheckBox();
        zEndField = new javax.swing.JTextField();
        zStartField = new javax.swing.JTextField();
        zStartButton = new javax.swing.JButton();
        zEndButton = new javax.swing.JButton();
        channelsLabel3 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        zStackAddChannelButton = new javax.swing.JButton();
        zStackRemoveChannelButton = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        zStackAvailableChannelJList = new javax.swing.JList<>();
        jScrollPane10 = new javax.swing.JScrollPane();
        zStackUsedChannelJList = new javax.swing.JList<>();
        jLabel28 = new javax.swing.JLabel();
        channelsLabel4 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        videoAddChannelButton = new javax.swing.JButton();
        videoRemoveChannelButton = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        videoAvailableChannelJList = new javax.swing.JList<>();
        jScrollPane12 = new javax.swing.JScrollPane();
        videoUsedChannelJList = new javax.swing.JList<>();
        jLabel30 = new javax.swing.JLabel();
        videoCheck = new javax.swing.JCheckBox();
        videoExposureField = new javax.swing.JTextField();
        videoDurationField = new javax.swing.JTextField();
        regionLabel = new javax.swing.JLabel();
        acquisitionSetupButton = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        nextRegionButton = new javax.swing.JButton();
        previousFishButton = new javax.swing.JButton();
        nextFishButton = new javax.swing.JButton();
        previousRegionButton = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        regionSetupTitle = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        regionTable = new javax.swing.JTable();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jScrollPane14 = new javax.swing.JScrollPane();
        snapAvailableChannelJList = new javax.swing.JList<>();
        jScrollPane15 = new javax.swing.JScrollPane();
        snapUsedChannelJList = new javax.swing.JList<>();
        jLabel36 = new javax.swing.JLabel();
        snapCheck = new javax.swing.JCheckBox();
        channelsLabel5 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        snapAddChannelButton = new javax.swing.JButton();
        snapRemoveChannelButton = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        stepSizeField = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        removeRegionButton = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();

        acquisitionSettingsFrame.setBounds(new java.awt.Rectangle(0, 0, 500, 400));

        startAcquisitionButton.setEnabled(false);
        startAcquisitionButton.setText("Start Acquisition");

        numTimePointsField.setEnabled(false);

        timePointsCheck.setText("Time Points");

        jLabel15.setText("Number of Time Points:");

        jLabel16.setText("Interval:");

        intervalField.setEnabled(false);

        jLabel17.setText("minutes");

        jLabel18.setText("Total Number of Images:");

        jLabel19.setText("Total Memory:");

        jLabel20.setText("GB");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setText("Acquisition Setup");

        jLabel24.setText("Channel Acquisition Order");

        channelOrderJListMoveUpButton.setText("Move Up");

        channelOrderJListMoveDownButton.setText("Move Down");

        jScrollPane8.setViewportView(channelOrderJList);

        SaveLocationLabel.setText("Save Location");

        browseButton.setText("Browse...");

        jLabel1.setText("Duration of single time point:");

        acquisitionTimeSingleLabel.setText("0 minutes 0 seconds");

        jLabel2.setText("Total Acquisition Time:");

        totalAcquisitionTimeLabel.setText("0 minutes 0 seconds");

        javax.swing.GroupLayout acquisitionSettingsFrameLayout = new javax.swing.GroupLayout(acquisitionSettingsFrame.getContentPane());
        acquisitionSettingsFrame.getContentPane().setLayout(acquisitionSettingsFrameLayout);
        acquisitionSettingsFrameLayout.setHorizontalGroup(
            acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(acquisitionSettingsFrameLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(acquisitionSettingsFrameLayout.createSequentialGroup()
                        .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(numTimePointsField, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(intervalField, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(timePointsCheck))
                .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(acquisitionSettingsFrameLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addComponent(jLabel24)
                        .addGap(78, 78, 78))
                    .addGroup(acquisitionSettingsFrameLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel17)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, acquisitionSettingsFrameLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, acquisitionSettingsFrameLayout.createSequentialGroup()
                        .addComponent(directoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseButton))
                    .addGroup(acquisitionSettingsFrameLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(SaveLocationLabel)))
                .addGap(138, 138, 138))
            .addGroup(acquisitionSettingsFrameLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(acquisitionSettingsFrameLayout.createSequentialGroup()
                        .addComponent(memoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20))
                    .addComponent(numImagesField, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(channelOrderJListMoveUpButton)
                    .addComponent(channelOrderJListMoveDownButton))
                .addGap(14, 14, 14))
            .addGroup(acquisitionSettingsFrameLayout.createSequentialGroup()
                .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(acquisitionSettingsFrameLayout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addComponent(jLabel21))
                    .addGroup(acquisitionSettingsFrameLayout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(startAcquisitionButton))
                    .addGroup(acquisitionSettingsFrameLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(acquisitionTimeSingleLabel)
                            .addComponent(totalAcquisitionTimeLabel))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        acquisitionSettingsFrameLayout.setVerticalGroup(
            acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(acquisitionSettingsFrameLayout.createSequentialGroup()
                .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(acquisitionSettingsFrameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21)
                        .addGap(31, 31, 31)
                        .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(timePointsCheck)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(acquisitionSettingsFrameLayout.createSequentialGroup()
                                .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(numTimePointsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(intervalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(numImagesField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(memoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20)))))
                    .addGroup(acquisitionSettingsFrameLayout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(channelOrderJListMoveUpButton)
                        .addGap(18, 18, 18)
                        .addComponent(channelOrderJListMoveDownButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(acquisitionTimeSingleLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(totalAcquisitionTimeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SaveLocationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(acquisitionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(browseButton)
                    .addComponent(directoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(startAcquisitionButton)
                .addContainerGap())
        );

        acquisitionFrame.setBounds(new java.awt.Rectangle(0, 0, 370, 220));

        jLabel25.setText("Acquisition in Progress");

        abortButton.setText("Abort Acquisition");

        acquisitionFishLabel.setText("Fish 1");

        acquisitionTimePointLabel.setText("TimePoint 1");

        acquisitionRegionLabel.setText("Region 1");

        acquisitionLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        acquisitionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acquisitionLabel.setText("Starting");

        javax.swing.GroupLayout acquisitionFrameLayout = new javax.swing.GroupLayout(acquisitionFrame.getContentPane());
        acquisitionFrame.getContentPane().setLayout(acquisitionFrameLayout);
        acquisitionFrameLayout.setHorizontalGroup(
            acquisitionFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(acquisitionFrameLayout.createSequentialGroup()
                .addGroup(acquisitionFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(acquisitionFrameLayout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel25))
                    .addGroup(acquisitionFrameLayout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(abortButton))
                    .addGroup(acquisitionFrameLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(acquisitionFishLabel)
                        .addGap(18, 18, 18)
                        .addGroup(acquisitionFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(acquisitionLabel)
                            .addGroup(acquisitionFrameLayout.createSequentialGroup()
                                .addComponent(acquisitionRegionLabel)
                                .addGap(18, 18, 18)
                                .addComponent(acquisitionTimePointLabel)))))
                .addContainerGap(107, Short.MAX_VALUE))
        );
        acquisitionFrameLayout.setVerticalGroup(
            acquisitionFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(acquisitionFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addGap(18, 18, 18)
                .addGroup(acquisitionFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acquisitionFishLabel)
                    .addComponent(acquisitionRegionLabel)
                    .addComponent(acquisitionTimePointLabel))
                .addGap(49, 49, 49)
                .addComponent(acquisitionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(abortButton)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        abortFrame.setBounds(new java.awt.Rectangle(0, 0, 390, 100));

        jLabel26.setText("Are you sure you want to abort the current acquisition?");

        abortYesButton.setText("Yes");

        abortCancelButton.setText("Cancel");

        javax.swing.GroupLayout abortFrameLayout = new javax.swing.GroupLayout(abortFrame.getContentPane());
        abortFrame.getContentPane().setLayout(abortFrameLayout);
        abortFrameLayout.setHorizontalGroup(
            abortFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, abortFrameLayout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addComponent(jLabel26)
                .addGap(67, 67, 67))
            .addGroup(abortFrameLayout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(abortCancelButton)
                .addGap(60, 60, 60)
                .addComponent(abortYesButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        abortFrameLayout.setVerticalGroup(
            abortFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abortFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addGap(18, 18, 18)
                .addGroup(abortFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(abortYesButton)
                    .addComponent(abortCancelButton))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        regionSettingsFrame.setBounds(new java.awt.Rectangle(0, 0, 1010, 880));

        setRegionButton.setText("Set");

        fishLabel.setText("Fish 1");

        zStackCheck.setText("Z-Stack");

        zEndField.setEnabled(false);

        zStartField.setEnabled(false);

        zStartButton.setText("Set");
        zStartButton.setEnabled(false);

        zEndButton.setText("Set");
        zEndButton.setEnabled(false);

        channelsLabel3.setText("Channels");

        jLabel27.setText("Available");

        zStackAddChannelButton.setText("Add >>");
        zStackAddChannelButton.setEnabled(false);

        zStackRemoveChannelButton.setText("<< Remove");
        zStackRemoveChannelButton.setEnabled(false);

        jScrollPane9.setViewportView(zStackAvailableChannelJList);

        zStackUsedChannelJList.setModel(new DefaultListModel<String>() {
        });
        jScrollPane10.setViewportView(zStackUsedChannelJList);

        jLabel28.setText("Used");

        channelsLabel4.setText("Channels");

        jLabel29.setText("Available");

        videoAddChannelButton.setText("Add >>");
        videoAddChannelButton.setEnabled(false);

        videoRemoveChannelButton.setText("<< Remove");
        videoRemoveChannelButton.setEnabled(false);

        jScrollPane11.setViewportView(videoAvailableChannelJList);

        videoUsedChannelJList.setModel(new DefaultListModel<String>() {
        });
        jScrollPane12.setViewportView(videoUsedChannelJList);

        jLabel30.setText("Used");

        videoCheck.setText("Video");

        videoExposureField.setEnabled(false);

        videoDurationField.setEnabled(false);

        regionLabel.setText("Region 1");

        acquisitionSetupButton.setText("Acquisition Setup");

        jLabel31.setText("Z Start");

        jLabel32.setText("Z End");

        nextRegionButton.setText("Next Region");
        nextRegionButton.setEnabled(false);

        previousFishButton.setText("Previous Fish");
        previousFishButton.setEnabled(false);

        nextFishButton.setText("Next Fish");
        nextFishButton.setEnabled(false);

        previousRegionButton.setText("Previous Region");
        previousRegionButton.setEnabled(false);

        jLabel33.setText("X:");

        jLabel34.setText("Y:");

        jLabel35.setText("Z:");

        regionSetupTitle.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        regionSetupTitle.setText("Region Setup");

        regionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Fish #", "Region #", "X", "Y", "Z", "Z-Stack", "Z Start", "Z End", "Step Size", "Channels", "Video", "Duration", "Exp", "Channels", "Snap", "Channels", "# Images"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        regionTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        TableRenderer tableRenderer = new TableRenderer();
        regionTable.getColumnModel().getColumn(9).setCellRenderer(tableRenderer);
        regionTable.getColumnModel().getColumn(13).setCellRenderer(tableRenderer);
        regionTable.getColumnModel().getColumn(15).setCellRenderer(tableRenderer);
        jScrollPane13.setViewportView(regionTable);

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jScrollPane14.setViewportView(snapAvailableChannelJList);

        snapUsedChannelJList.setModel(new DefaultListModel<String>() {
        });
        jScrollPane15.setViewportView(snapUsedChannelJList);

        jLabel36.setText("Used");

        snapCheck.setText("Snap");

        channelsLabel5.setText("Channels");

        jLabel37.setText("Available");

        snapAddChannelButton.setText("Add >>");
        snapAddChannelButton.setEnabled(false);

        snapRemoveChannelButton.setText("<< Remove");
        snapRemoveChannelButton.setEnabled(false);

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel38.setText("Exposure:");

        jLabel39.setText("Duratiion:");

        stepSizeField.setEnabled(false);

        jLabel40.setText("Step Size:");

        removeRegionButton.setText("Remove Region");

        jLabel41.setText("ms");

        jLabel42.setText("sec");

        javax.swing.GroupLayout regionSettingsFrameLayout = new javax.swing.GroupLayout(regionSettingsFrame.getContentPane());
        regionSettingsFrame.getContentPane().setLayout(regionSettingsFrameLayout);
        regionSettingsFrameLayout.setHorizontalGroup(
            regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, regionSettingsFrameLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 979, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(zStackRemoveChannelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(zStackAddChannelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, regionSettingsFrameLayout.createSequentialGroup()
                                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                                        .addComponent(jLabel32)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(zEndButton))
                                                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                                        .addComponent(jLabel31)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(zStartButton))
                                                    .addComponent(jLabel40))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(zStartField, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                                    .addComponent(zEndField)
                                                    .addComponent(stepSizeField)))
                                            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(channelsLabel3)
                                                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                                        .addComponent(jLabel27)
                                                        .addGap(89, 89, 89)))
                                                .addGap(53, 53, 53)
                                                .addComponent(jLabel28)))
                                        .addGap(26, 26, 26))))
                            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                .addGap(118, 118, 118)
                                .addComponent(zStackCheck)))
                        .addGap(44, 44, 44)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addComponent(jLabel29)
                                .addGap(142, 142, 142)
                                .addComponent(jLabel30))
                            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(videoRemoveChannelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(videoAddChannelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                        .addComponent(channelsLabel4)
                                        .addGap(7, 7, 7))
                                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel38)
                                            .addComponent(jLabel39))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(videoDurationField, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                                            .addComponent(videoExposureField))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel41)
                                    .addComponent(jLabel42)))
                            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addComponent(videoCheck)))
                        .addGap(54, 54, 54)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel37)
                                .addGap(142, 142, 142)
                                .addComponent(jLabel36))
                            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(snapRemoveChannelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(snapAddChannelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(snapCheck)
                                    .addComponent(channelsLabel5)))))
                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                        .addGap(455, 455, 455)
                        .addComponent(setRegionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                        .addGap(463, 463, 463)
                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fishLabel)
                            .addComponent(regionLabel)))
                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                        .addGap(435, 435, 435)
                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(regionSetupTitle)
                            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(zField)
                                    .addComponent(yField)
                                    .addComponent(xField, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                        .addGap(433, 433, 433)
                        .addComponent(acquisitionSetupButton))
                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                        .addGap(299, 299, 299)
                        .addComponent(previousFishButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(previousRegionButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nextRegionButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nextFishButton))
                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                        .addGap(435, 435, 435)
                        .addComponent(removeRegionButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        regionSettingsFrameLayout.setVerticalGroup(
            regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(regionSetupTitle)
                .addGap(18, 18, 18)
                .addComponent(fishLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(regionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(setRegionButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(zField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addGap(18, 18, 18)
                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nextRegionButton)
                    .addComponent(previousFishButton)
                    .addComponent(nextFishButton)
                    .addComponent(previousRegionButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeRegionButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                        .addComponent(videoCheck)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(videoExposureField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel38)
                                            .addComponent(jLabel41))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(videoDurationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel39)
                                            .addComponent(jLabel42))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(channelsLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel29)
                                            .addComponent(jLabel30))
                                        .addGap(9, 9, 9)
                                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                                .addComponent(videoAddChannelButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(videoRemoveChannelButton))))
                                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                        .addComponent(snapCheck)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(channelsLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel37)
                                            .addComponent(jLabel36))
                                        .addGap(9, 9, 9)
                                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                                .addComponent(snapAddChannelButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(snapRemoveChannelButton))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, regionSettingsFrameLayout.createSequentialGroup()
                                        .addComponent(zStackCheck)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(zStartField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(zStartButton)
                                            .addComponent(jLabel31))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(zEndField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(zEndButton)
                                            .addComponent(jLabel32))
                                        .addGap(2, 2, 2)
                                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(stepSizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel40))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(channelsLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel27)
                                            .addComponent(jLabel28))
                                        .addGap(9, 9, 9)
                                        .addGroup(regionSettingsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                                                .addComponent(zStackAddChannelButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(zStackRemoveChannelButton))))))
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE))
                    .addGroup(regionSettingsFrameLayout.createSequentialGroup()
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(acquisitionSetupButton)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1025, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 802, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new View().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel SaveLocationLabel;
    private javax.swing.JButton abortButton;
    private javax.swing.JButton abortCancelButton;
    private javax.swing.JFrame abortFrame;
    private javax.swing.JButton abortYesButton;
    private javax.swing.JLabel acquisitionFishLabel;
    private javax.swing.JFrame acquisitionFrame;
    private javax.swing.JLabel acquisitionLabel;
    private javax.swing.JLabel acquisitionRegionLabel;
    private javax.swing.JFrame acquisitionSettingsFrame;
    private javax.swing.JButton acquisitionSetupButton;
    private javax.swing.JLabel acquisitionTimePointLabel;
    private javax.swing.JLabel acquisitionTimeSingleLabel;
    private javax.swing.JButton browseButton;
    private javax.swing.JList<String> channelOrderJList;
    private javax.swing.JButton channelOrderJListMoveDownButton;
    private javax.swing.JButton channelOrderJListMoveUpButton;
    private javax.swing.JLabel channelsLabel3;
    private javax.swing.JLabel channelsLabel4;
    private javax.swing.JLabel channelsLabel5;
    private javax.swing.JTextField directoryField;
    private javax.swing.JLabel fishLabel;
    private javax.swing.JTextField intervalField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    public javax.swing.JTextField memoryField;
    private javax.swing.JButton nextFishButton;
    private javax.swing.JButton nextRegionButton;
    public javax.swing.JTextField numImagesField;
    private javax.swing.JTextField numTimePointsField;
    private javax.swing.JButton previousFishButton;
    private javax.swing.JButton previousRegionButton;
    private javax.swing.JLabel regionLabel;
    private javax.swing.JFrame regionSettingsFrame;
    private javax.swing.JLabel regionSetupTitle;
    private javax.swing.JTable regionTable;
    private javax.swing.JButton removeRegionButton;
    private javax.swing.JButton setRegionButton;
    private javax.swing.JButton snapAddChannelButton;
    private javax.swing.JList<String> snapAvailableChannelJList;
    private javax.swing.JCheckBox snapCheck;
    private javax.swing.JButton snapRemoveChannelButton;
    private javax.swing.JList<String> snapUsedChannelJList;
    private javax.swing.JButton startAcquisitionButton;
    private javax.swing.JTextField stepSizeField;
    private javax.swing.JCheckBox timePointsCheck;
    private javax.swing.JLabel totalAcquisitionTimeLabel;
    private javax.swing.JButton videoAddChannelButton;
    private javax.swing.JList<String> videoAvailableChannelJList;
    private javax.swing.JCheckBox videoCheck;
    private javax.swing.JTextField videoDurationField;
    private javax.swing.JTextField videoExposureField;
    private javax.swing.JButton videoRemoveChannelButton;
    private javax.swing.JList<String> videoUsedChannelJList;
    private javax.swing.JTextField xField;
    private javax.swing.JTextField yField;
    private javax.swing.JButton zEndButton;
    private javax.swing.JTextField zEndField;
    private javax.swing.JTextField zField;
    private javax.swing.JButton zStackAddChannelButton;
    private javax.swing.JList<String> zStackAvailableChannelJList;
    private javax.swing.JCheckBox zStackCheck;
    private javax.swing.JButton zStackRemoveChannelButton;
    private javax.swing.JList<String> zStackUsedChannelJList;
    private javax.swing.JButton zStartButton;
    private javax.swing.JTextField zStartField;
    // End of variables declaration//GEN-END:variables
}