/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.micromanager.ScanPlugin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import mmcorej.CMMCore;
import static mmcorej.DeviceType.StageDevice;
import mmcorej.TaggedImage;
import org.micromanager.MultiStagePosition;
import org.micromanager.data.Coords;
import org.micromanager.data.Datastore;
import org.micromanager.data.Image;
import org.micromanager.data.Metadata;
import org.micromanager.internal.MMStudio;

/**
 *
 * @author Raghu
 */
public class Acquisition implements Runnable{

    private final MMStudio mm_;
    private final CMMCore core_;
    private final HardwareCommands hardwareCommands;
    private final ArrayList<RegionSettings> regionSettingsArray;
    private Datastore data;
    private final ArrayList<String> channelOrderArray;
    private final String directory;
    private final AcquisitionSettings settings;
    private volatile Boolean abortBoolean;
    private final View view;
    private Thread Thread;
    
    public String fileExistsCheck(String directory) {
        int i = 1;
        String dir = directory;
        Path path = Paths.get(directory);
        while (true) {
            if (Files.exists(path)) {
                dir = directory + "_" + i;
                path = Paths.get(dir);
                i++;
            }
            else {
                break;
            }
        }
        return dir;
    }
    
    public void setAbortBoolean(Boolean abort) {
        abortBoolean = abort;
    }
    
    public Acquisition(AcquisitionSettings acquisitionSettings, View mainFrame, MMStudio studio) {
        mm_ = (MMStudio) studio;
        core_ = mm_.core();
        settings = acquisitionSettings;
        view = mainFrame;
        
        hardwareCommands = new HardwareCommands(mm_);
        regionSettingsArray = settings.getRegionSettingsArray();
        channelOrderArray = settings.getChannelOrderArray();
        directory = settings.getDirectory();
        abortBoolean = false;
    }  
    @Override
    public void run() {
        acquisition: {
            try {
                core_.setShutterOpen(false);
                core_.setAutoShutter(true);
                hardwareCommands.initializePLC(10);
                
                for (int numTimePoints = 0; numTimePoints < settings.getNumTimePoints(); numTimePoints++) {
                    long start = System.nanoTime();
                    
                    view.getAcquisitionTimePointLabel().setText("Time point " + (numTimePoints + 1) );
                    view.getAcquisitionLabel().setText("Initializing Acquisition");
                   
                    if (abortBoolean) {
                        view.getAcquisitionLabel().setText("Aborted");
                        view.getStartAcquisitionButton().setEnabled(true);
                        break acquisition;
                    }
                    
                    for (RegionSettings regionSettings : regionSettingsArray) {
                        int fishNum = regionSettings.getFishNumber();
                        int regionNum = regionSettings.getRegionNumber();
                        double xPos = regionSettings.getXPosition();
                        double yPos = regionSettings.getYPosition();
                        double zPos = regionSettings.getZPosition();
                        
                        view.getAcquisitionFishLabel().setText("Fish " + (fishNum + 1) );
                        view.getAcquisitionRegionLabel().setText("Region " + (regionNum + 1) );
                        
                        //check for way to make move stage method blocking. maybe wait for serial command from stage?
                        hardwareCommands.moveStage(xPos, yPos, zPos);
                        
                        if (regionSettings.getSnapBoolean()) {
                            hardwareCommands.setDefaultCameraProperties();
                            
                            for (String channel : channelOrderArray) {
                                if (regionSettings.getSnapChannelArray().contains(channel)) {
                                    view.getAcquisitionLabel().setText("Acquiring " + channel + " snap");
                                    core_.setConfig(settings.getChannelGroupName(), channel);
                                    
                                    String dir = directory + "/Fish" + (fishNum + 1) + "/Timepoint" + (numTimePoints + 1) + "/Pos" + (regionNum + 1) + "/snap/" + channel;
                                    dir = fileExistsCheck(dir);
                                    data = mm_.data().createSinglePlaneTIFFSeriesDatastore(dir);
                                    
                                    Image image = mm_.live().snap(false).get(0);
                                    Metadata meta = image.getMetadata().copy().xPositionUm(xPos).yPositionUm(yPos).zPositionUm(zPos).build();
                                    Coords coords = image.getCoords().copyBuilder().build();
                                    image = image.copyWith(coords, meta);
                                    data.putImage(image);
                                    
                                    view.getAcquisitionLabel().setText("Saving " + channel + " snap");
                                    data.close();
                                    core_.clearCircularBuffer();
                                    if (abortBoolean) {
                                        view.getAcquisitionLabel().setText("Aborted");
                                        view.getStartAcquisitionButton().setEnabled(true);
                                        break acquisition;
                                    }
                                }
                            }
                        }
                        
                        if (regionSettings.getVideoBoolean()) {
                            hardwareCommands.setDefaultCameraProperties();
                            core_.setExposure(regionSettings.getVideoExposureTime());
                            
                            for (String channel : channelOrderArray) {
                                if (regionSettings.getVideoChannelArray().contains(channel)) {
                                    view.getAcquisitionLabel().setText("Acquiring " + channel + " video");
                                    
                                    core_.setConfig(settings.getChannelGroupName(), channel);
                                    String dir = directory + "/Fish" + (fishNum + 1) + "/Timepoint" + (numTimePoints + 1) + "/Pos" + (regionNum + 1) + "/video/" + channel;
                                    dir = fileExistsCheck(dir);
                                    data = mm_.data().createSinglePlaneTIFFSeriesDatastore(dir);
                                    
                                    int numImages = (int) Math.round(1/(regionSettings.getVideoExposureTime()/1000) * regionSettings.getVideoDurationInSeconds());
                                    int frameNumber = 0;
                                    int timeout = 0;
                                    boolean sequenceBoolean = false;
                                    try {
                                        core_.startSequenceAcquisition(numImages, 0, true);
                                        while (core_.getRemainingImageCount() > 0 || core_.isSequenceRunning()) {
                                            if (core_.getRemainingImageCount() > 0) {
                                                TaggedImage tagged = core_.popNextTaggedImage();
                                                Image image = mm_.data().convertTaggedImage(tagged);
                                                Metadata meta = image.getMetadata().copy().xPositionUm(xPos).yPositionUm(yPos).zPositionUm(zPos).build();
                                                Coords coords = image.getCoords().copyBuilder().t(frameNumber).build();
                                                image = image.copyWith(coords, meta);
                                                data.putImage(image);
                                                frameNumber++;
                                                timeout = 0;
                                                if (core_.isSequenceRunning() & !sequenceBoolean) {
                                                    view.getAcquisitionLabel().setText("Saving " + channel + " video");
                                                    sequenceBoolean = true;
                                                } else {
                                                    core_.sleep(10);
                                                    timeout++;
                                                }
                                                
                                                if (abortBoolean) {
                                                    core_.stopSequenceAcquisition();
                                                    view.getAcquisitionLabel().setText("Aborted");
                                                    view.getStartAcquisitionButton().setEnabled(true);
                                                    break acquisition;
                                                }
                                        
                                                if (timeout > 1000) {
                                                    core_.stopSequenceAcquisition();
                                                    core_.clearCircularBuffer();
                                                    view.getAcquisitionLabel().setText("Timepoint " + (numTimePoints + 1) + " " + channel + " video failed, camera timeout");
                                                    mm_.logs().logMessage("Timepoint " + (numTimePoints + 1) + " " + channel + " video failed, camera timeout");
                                                }
                                            }      
                                        }
                                        core_.stopSequenceAcquisition();
                                        data.close();
                                        core_.clearCircularBuffer();
                                    
                                        if (abortBoolean) {
                                            view.getAcquisitionLabel().setText("Aborted");
                                            view.getStartAcquisitionButton().setEnabled(true);
                                            break acquisition;
                                        }
                                    } catch (Exception e) {
                                        mm_.logs().logMessage("Timepoint " + (numTimePoints + 1) + " " + channel + " video failed, unknown error");
                                        view.getAcquisitionLabel().setText("Timepoint " + (numTimePoints + 1) + " " + channel + " video failed, unknown error, check logs");
                                    }
                                }
                            }
                        }
                        
                        if (regionSettings.getZStackBoolean()) {
                            int zStart = regionSettings.getZStartPosition();
                            int zEnd = regionSettings.getZEndPosition();
                            int stepSize = regionSettings.getStepSize();
                            
                            hardwareCommands.setPLCProperties(stepSize);
                            hardwareCommands.setScanCameraProperties();
                            hardwareCommands.moveStage(xPos, yPos, zStart);
                          
                            for (String channel : settings.getChannelOrderArray()) {
                                if (regionSettings.getZStackChannelArray().contains(channel)) {
                                    view.getAcquisitionLabel().setText("Acquiring " + channel + " z stack");
                                    
                                    String dir = directory + "/Fish" + (fishNum + 1) + "/Timepoint" + (numTimePoints + 1) + "/Pos" + (regionNum + 1) + "/zStack/" + channel;
                                    dir = fileExistsCheck(dir);
                                    data = mm_.data().createSinglePlaneTIFFSeriesDatastore(dir);
                                    int numFrames = Math.round(Math.abs(zEnd - zStart) / stepSize);
                                    boolean sequenceBoolean = false;
                                    int timeout = 0;
                                    core_.setConfig(settings.getChannelGroupName(), channel);
                                    
                                    if (zStart <= zEnd) {
                                        hardwareCommands.scanSetup(zStart - 3, zEnd + 3);
                                    } else {
                                        hardwareCommands.scanSetup(zStart + 3, zEnd - 3);
                                    }
                                    
                                    try{
                                        core_.startSequenceAcquisition(numFrames, 0, false);
                                        hardwareCommands.scanStart();
                                    
                                        while (core_.getRemainingImageCount() > 0 || core_.isSequenceRunning()) {
                                            for (int curFrame = 0; curFrame < numFrames;) {
                                                if (core_.getRemainingImageCount() > 0) {
                                                    TaggedImage tagged = core_.popNextTaggedImage();
                                                    Image image = mm_.data().convertTaggedImage(tagged);
                                                
                                                    double zPosScan = 0;
                                                    if (zEnd >= zStart) {
                                                        zPosScan = zStart + stepSize * curFrame;
                                                    } else {
                                                        zPosScan = zStart - stepSize * curFrame;
                                                    }
                                                    Metadata meta = image.getMetadata().copyBuilderPreservingUUID().xPositionUm(xPos).yPositionUm(yPos).zPositionUm(zPosScan).build();
                                                    Coords coords = image.getCoords().copyBuilder().z(curFrame).build();
                                                    image = image.copyWith(coords, meta);
                                                    data.putImage(image);
                                                    curFrame++;
                                                    timeout = 0;
                                                    if (!core_.isSequenceRunning() & !sequenceBoolean) {
                                                        view.getAcquisitionLabel().setText("Saving " + channel + " z stack");
                                                        sequenceBoolean = true;
                                                    } else {
                                                        core_.sleep(10);
                                                    }
                                            
                                                    if(abortBoolean) {
                                                        core_.stopSequenceAcquisition();
                                                        view.getAcquisitionLabel().setText("Aborted");
                                                        view.getStartAcquisitionButton().setEnabled(true);
                                                        break acquisition;
                                                    }
                                                    
                                                    if (timeout > 1000) {
                                                        core_.stopSequenceAcquisition();
                                                        core_.clearCircularBuffer();
                                                        if (curFrame < numFrames) {
                                                            view.getAcquisitionLabel().setText("Timepoint " + (numTimePoints + 1) + " " + channel + " z stack failed, not enough images acquired");
                                                            mm_.logs().logMessage("Timepoint " + (numTimePoints + 1) + " " + channel + " z stack failed, not enough images acquired");
                                                        } else {
                                                            view.getAcquisitionLabel().setText("Timepoint " + (numTimePoints + 1) + " " + channel + " z stack failed, camera timeout");
                                                            mm_.logs().logMessage("Timepoint " + (numTimePoints + 1) + " " + channel + " z stack failed, camera timeout");
                                                        }
                                                        break;
                                                    }
                                                }
                                                core_.stopSequenceAcquisition();
                                            }
                                        } 
                                        data.close();
                                        core_.clearCircularBuffer();
                                        //Small pause added in between scans because stage was having weird backlash issues
                                        core_.sleep(2000);
                                    
                                        if (abortBoolean) {
                                            view.getAcquisitionLabel().setText("Aborted");
                                            view.getStartAcquisitionButton().setEnabled(true);
                                            break acquisition;
                                        }
                                    } catch (Exception e) {
                                        mm_.logs().logMessage("Timepoint " + (numTimePoints + 1) + " " + channel + " z stack failed, unknown error");
                                        view.getAcquisitionLabel().setText("Timepoint " + (numTimePoints + 1) + " " + channel + " z stack failed, unknown error, check logs");
                                    }
                                }
                            }
                        }
                    }
                    if (!settings.getTimePointsBoolean()) {
                        numTimePoints = settings.getNumTimePoints();
                    }
                    
                    if (abortBoolean) {
                        view.getAcquisitionLabel().setText("Aborted");
                        view.getStartAcquisitionButton().setEnabled(true);
                        break acquisition;
                    }
                    
                    long end = System.nanoTime();
                    long durationMS = (int) Math.round((end - start) / Math.pow(10, 6)) ;
                    double delay = settings.getTimePointsInterval() * 60 * 1000;
                    if (settings.getTimePointsBoolean() & (settings.getNumTimePoints() - numTimePoints) > 1) {
                        while (delay - durationMS > 0) {
                            end = System.nanoTime();
                            durationMS = (int) Math.round((end - start) / Math.pow(10, 6)) ;
                            delay = settings.getTimePointsInterval() * 60 * 1000;
                            
                            int timeLeftSeconds = (int) Math.round((delay - durationMS) / 1000);
                            int numMinutesLeft = (int) Math.floor(timeLeftSeconds / 60);
                            int numSecondsLeft = timeLeftSeconds % 60;
                            
                            view.getAcquisitionLabel().setText("Waiting for next time point: " + numMinutesLeft + " minutes " + numSecondsLeft + " seconds");
                            if (abortBoolean) {
                                view.getAcquisitionLabel().setText("Aborted");
                                view.getStartAcquisitionButton().setEnabled(true);
                                break acquisition;
                            }       
                        }
                    }
                }
                hardwareCommands.setDefaultCameraProperties();
                hardwareCommands.resetJoystick();
                
                view.getAcquisitionLabel().setText("Your acquisition was successful!");
                view.getStartAcquisitionButton().setEnabled(true);
            } catch (Exception ex) {
                Logger.getLogger(Acquisition.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}


