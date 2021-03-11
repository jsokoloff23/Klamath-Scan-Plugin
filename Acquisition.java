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
    private final int stepSize;
    private final StagePositions stagePositions;
    public int exposureMS;
    public final ArrayList<Boolean> brightFieldArray;
    public final String dir;
    private final DeviceSetup deviceSetup;
    private final StageCommands stageCommands;
    private Datastore data;
    private final ArrayList<String> channelArray;
    private final String groupName;
    private final Boolean timeSeries;
    private final Integer delay;
    private final double stageScanSpeed;
    private final double stageMoveSpeed;
    private final Integer timePointsNum;
    private final String brightFieldPreset;
    
    public Acquisition(int step, String group, ArrayList brightFieldImage, Boolean timePoints, Integer numTimePoints, Integer interval, 
           String directory, ArrayList channels, StagePositions positions, MMStudio studio) {
        
        mm_ = (MMStudio) studio;
        core_ = mm_.core();
        deviceSetup = new DeviceSetup (mm_);
        stageCommands = new StageCommands(mm_);
        stepSize = step;
        groupName = group;
        brightFieldArray = brightFieldImage;
        brightFieldPreset = "BF";
        timeSeries = timePoints;
        delay = 1000 * interval;
        timePointsNum = numTimePoints;
        dir = directory;
        channelArray = channels;
        stagePositions = positions;
        exposureMS = 10;
        stageScanSpeed = 0.03;
        stageMoveSpeed = 1.0;
    }
    
    public void startAcquisition() throws Exception {
        
        stageCommands.scanSpeed = stageScanSpeed;
        stageCommands.moveSpeed = stageMoveSpeed;
        deviceSetup.setPLCProperties(exposureMS, stepSize, stageScanSpeed);
        
        PositionList positionList = stagePositions.getPositionList();
        ArrayList<Double> zStartPositions = stagePositions.getScanStartPositionList();
        ArrayList<Double> zEndPositions = stagePositions.getScanEndPositionList();
        
        for (int numTimePoints = 0; numTimePoints < timePointsNum; numTimePoints ++) {
            long start = System.nanoTime();
            
            for (int regionNum = 0; regionNum < positionList.getNumberOfPositions(); regionNum++) {
                MultiStagePosition region = positionList.getPosition(regionNum);
                
                if (brightFieldArray.get(regionNum)) {
                    stageCommands.moveStage(region);
                    deviceSetup.setDefaultCameraProperties();
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
                    
                stageCommands.moveStage(region.getX(), region.getY(), zStart);
                
                for (int channelNum = 0; channelNum < channelArray.size(); channelNum++) {
                    data = mm_.data().createRAMDatastore();
                    stageCommands.scanSetup(zStart, zEnd);
                    deviceSetup.setScanCameraProperties();
                    core_.setConfig(groupName, channelArray.get(channelNum));
                    core_.startContinuousSequenceAcquisition(0);
                    while (core_.isSequenceRunning()) {
                        stageCommands.scanStart();
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
        deviceSetup.setDefaultCameraProperties();
    }
}