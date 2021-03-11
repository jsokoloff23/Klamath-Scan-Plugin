/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.micromanager.ScanPlugin;

import java.io.File;
import java.util.ArrayList;
import mmcorej.CMMCore;
import mmcorej.TaggedImage;
import org.micromanager.MultiStagePosition;
import org.micromanager.PositionList;
import org.micromanager.data.Image;
import org.micromanager.data.Datastore;
import org.micromanager.internal.MMStudio;

/**
 *
 * @author Raghu
 */
public class Acquisition {
    private final MMStudio mm_;
    private CMMCore core_;
    private final HardwareCommands hardwareCommands;
    private final Integer stepSize;
    private final PositionList stagePositions;
    private final ArrayList<String> channelArray;
    private final ArrayList<Double> zStartPositions;
    private final ArrayList<Double> zEndPositions;
    private final ArrayList<Boolean> brightFieldArray;
    private final String dir;
    private final String groupName;
    private final Boolean timeSeries;
    private final int delay;
    private final Integer timePointsNum;
    private final String brightFieldPreset;
    private Datastore data;

    
    public Acquisition(ScanSettings acquisitionSettings, String directory, MMStudio studio) {
        
        mm_ = (MMStudio) studio;
        core_ = mm_.core();
        
        hardwareCommands = new HardwareCommands (mm_);
        stepSize = acquisitionSettings.stepSize;
        stagePositions = acquisitionSettings.getPositionList();
        zStartPositions = acquisitionSettings.getScanStartPositionArray();
        zEndPositions = acquisitionSettings.getScanEndPositionArray();
        brightFieldArray = acquisitionSettings.getBrightFieldArray();
        channelArray = acquisitionSettings.getChannelArray();
        dir = directory;
        groupName = acquisitionSettings.channelGroupName;
        timeSeries = acquisitionSettings.timePointsBoolean;
        delay = 1000 * acquisitionSettings.timePointsInterval;
        timePointsNum = acquisitionSettings.numTimePoints;
        brightFieldPreset = "BF";
        

    }
    
    public void startAcquisition() throws Exception {
        hardwareCommands.setPLCProperties(stepSize);
       
        for (int numTimePoints = 0; numTimePoints < timePointsNum; numTimePoints ++) {
            long start = System.nanoTime();
            
            for (int regionNum = 0; regionNum < stagePositions.getNumberOfPositions(); regionNum++) {
                MultiStagePosition region = stagePositions.getPosition(regionNum);
                
                if (brightFieldArray.get(regionNum)) {
                    hardwareCommands.moveStage(region);
                    hardwareCommands.setDefaultCameraProperties();
                    core_.setConfig(groupName, brightFieldPreset);
                    
                    data = mm_.data().createRAMDatastore();
                    Image image = mm_.live().snap(false).get(0);
                    data.putImage(image);
                    
                    String directory = dir + "/" + brightFieldPreset + "/" + "Pos" + regionNum;
                    data.save(Datastore.SaveMode.MULTIPAGE_TIFF, directory);
                    data.close();
                    core_.clearCircularBuffer();
                }
                
                double zStart = zStartPositions.get(regionNum);
                double zEnd = zEndPositions.get(regionNum);
                int numFrames = (int) Math.round(Math.abs(zEnd - zStart));
                    
                hardwareCommands.moveStage(region.getX(), region.getY(), zStart);
                
                for (int channelNum = 0; channelNum < channelArray.size(); channelNum++) {
                    data = mm_.data().createRAMDatastore();
                    hardwareCommands.scanSetup(zStart, zEnd);
                    hardwareCommands.setScanCameraProperties();
                    core_.setConfig(groupName, channelArray.get(channelNum));
                    core_.startContinuousSequenceAcquisition(0);
                    while (core_.isSequenceRunning()) {
                        hardwareCommands.scanStart();
                        for (int curFrame = 0; curFrame < numFrames;) {
                            if (core_.getRemainingImageCount() > 0) {
                                TaggedImage tagged = core_.popNextTaggedImage();
                                Image image = mm_.data().convertTaggedImage(tagged);
                                Image image1 = image.copyAtCoords(image.getCoords().copyBuilder().c(0).p(0).t(0).z(curFrame).build());
                                data.putImage(image1);
                                curFrame++;
                            }
                            else {
                                core_.sleep(10);
                            }
                        }
                        core_.stopSequenceAcquisition();
                        String directory = dir + "/" + channelArray.get(channelNum) + "/" + "Pos" + regionNum;
                        int i = 0;
                        while (true) {
                            i++;
                            File file = new File(directory);
                            if (file.exists()) {
                                directory += "_" + i;
                            }
                            else {
                                break;
                            }
                        }
                        data.save(Datastore.SaveMode.MULTIPAGE_TIFF, directory);
                        data.close();
                        core_.clearCircularBuffer();
                    } 
                }
            }
            long end = System.nanoTime();
            long durationMS = (end - start) / 1000 ;
            if (timeSeries) {
                if (delay - durationMS > 0) {
                    core_.sleep(delay - durationMS);
                }
            }
        }
        hardwareCommands.setDefaultCameraProperties();
    }
}