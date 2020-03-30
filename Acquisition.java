/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.micromanager.ScanPlugin;

import java.io.File;
import mmcorej.CMMCore;
import mmcorej.TaggedImage;
import org.micromanager.MultiStagePosition;
import org.micromanager.PositionList;
import org.micromanager.data.Datastore;
import org.micromanager.data.Image;
import org.micromanager.data.RewritableDatastore;
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
    private final DeviceSetup deviceSetup;
    private final StageCommands stageCommands;
    private RewritableDatastore data;
    private final String[] channelArray;
    private final String groupName;
    
    public Acquisition(int step, String group, String[] channels, StagePositions positions, MMStudio studio) {
        mm_ = (MMStudio) studio;
        core_ = mm_.core();
        deviceSetup = new DeviceSetup (mm_);
        stageCommands = new StageCommands(mm_);
        stepSize = step;
        groupName = group;
        channelArray = channels;
        stagePositions = positions;
        exposureMS = 10;
    }
    
    public void startAcquisition(String dir, boolean display, boolean save) throws Exception {
        deviceSetup.setCameraProperties();
        deviceSetup.setPLCProperties(exposureMS, stepSize);
        data = mm_.data().createRewritableRAMDatastore();
        PositionList positionList = stagePositions.getPositionList();
        for (int regionNum = 0; regionNum < positionList.getNumberOfPositions(); regionNum++) {
            MultiStagePosition region = positionList.getPosition(regionNum);
            stageCommands.moveStage(region);
            core_.sleep(2000);
            double zStart = stagePositions.getZStartPosition(regionNum);
            double zEnd = stagePositions.getZEndPosition(regionNum);
            stageCommands.scanSetup(zStart, zEnd);
            int numFrames = (int) Math.round(Math.abs(zEnd-zStart));
            core_.startContinuousSequenceAcquisition(0);
            while (core_.isSequenceRunning(deviceSetup.camName)) {
                for (int channelNum = 0; channelNum < channelArray.length; channelNum++) {
                    core_.setConfig(groupName, channelArray[channelNum]);
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
                    if (save == true) {
                        String directory = dir + "Channel" + channelNum + "Pos" + regionNum;
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
                    }
                    if (display == true) {
                        mm_.displays().createDisplay(data);
                    }
                    data.deleteAllImages();
                }
            }
        }
    }
}
