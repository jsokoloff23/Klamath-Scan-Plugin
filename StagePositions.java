/* This class sets up the position list as well as start and end positions
 * for Z-Stacks
 */
package org.micromanager.ScanPlugin;

import org.micromanager.internal.MMStudio;
import mmcorej.CMMCore;
import org.micromanager.MultiStagePosition;
import org.micromanager.PositionList;
import java.util.ArrayList;
import java.text.DecimalFormat;

/**
 *
 * @author Jonah Sokoloff
 */
    public class StagePositions {
   
    private static MMStudio mm_;
    private static CMMCore core_;
    private final PositionList regionList;
    private final ArrayList<Double> zStartPositions;
    private final ArrayList<Double> zEndPositions;
    private static MultiStagePosition region;
    private static DecimalFormat dc;
    private static String zStage;
    private static String xyStage;
    
    
    public StagePositions(MMStudio studio) {
        mm_ = (MMStudio) studio;
        core_ = mm_.core();
        regionList = new PositionList();
        zStartPositions = new ArrayList<>();
        zEndPositions = new ArrayList<>();
        zStage = "ZStage:Z:32";
        xyStage = "XYStage:XY:31";
    }
    
    //gets current X, Y, and Z stage positions and adds it to positionList
    //if position at index alreay exists, it replaces it
    public void setRegion(int regionNum) throws Exception {
        double xPos = (Math.round(core_.getXPosition(xyStage) * 100)) / 100;
        double yPos = (Math.round(core_.getYPosition(xyStage) * 100)) / 100;
        double zPos = (Math.round(core_.getPosition(zStage) * 100)) / 100;
        region = new MultiStagePosition(xyStage, xPos, yPos, zStage, zPos);
        
        if (regionList.getPosition(regionNum) != null) {
            regionList.replacePosition(regionNum, region);
        }
        else {
            regionList.addPosition(regionNum, region);
        }
    }
    
    //gets current Z stage position, rounds to nearest hundredth micron, adds to start positions array
    //if value at index already exists, it replaces it
    public void setZStartPosition(int regionNum) throws Exception {
        double startZ = (Math.round(core_.getPosition(zStage) * 100)) / 100;
        if (zStartPositions.size() < regionNum) {
            zStartPositions.add(startZ);
        }
        else {
            zStartPositions.set(regionNum, startZ);
        }
    }
    
    //same as setZStartPositions except adds to end positions array
    public void setZEndPosition(int regionNum) throws Exception {
        double endZ = (Math.round(core_.getPosition(zStage) * 100)) / 100;
        if (zEndPositions.size() < regionNum) {
            zEndPositions.add(endZ);
        }
        else {
            zEndPositions.set(regionNum, endZ);
        }
    }
    
    //returns regionList
    public PositionList getPositionList() {
        return regionList;
    }
    
    //returns indexed z start position
    public double getZStartPosition(int regionNum) {
        return zStartPositions.get(regionNum);
    }
    
    //returns indexed z end position
    public double getZEndPosition(int regionNum) {
        return zEndPositions.get(regionNum);
    }
}
