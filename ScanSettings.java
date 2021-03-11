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
    private final StageCommands stageCommands;
    private final PositionList regionList;
    private final ArrayList<Double> xPositionList;
    private final ArrayList<Double> yPositionList;
    private final ArrayList<Double> zPositionList;
    private final ArrayList<Double> scanStartPositions;
    private final ArrayList<Double> scanEndPositions;
    private static MultiStagePosition region;
    private static DecimalFormat dc;
    private static String zStage;
    private static String xyStage;
    
    
    public StagePositions(MMStudio studio) {
        mm_ = (MMStudio) studio;
        core_ = mm_.core();
        stageCommands = new StageCommands(mm_);
        regionList = new PositionList();
        xPositionList = new ArrayList<>();
        yPositionList = new ArrayList<>();
        zPositionList = new ArrayList<>();
        scanStartPositions = new ArrayList<>();
        scanEndPositions = new ArrayList<>();
        zStage =  "ZStage:Z:32";
        xyStage = "XYStage:XY:31";
    }
    
    //gets current X, Y, and Z stage positions and adds it to positionList
    //if position at index alreay exists, it is replaced
    public void setRegion(int regionNum, double xPos, double yPos, double zPos) {
        MultiStagePosition region = new MultiStagePosition(xyStage, xPos, yPos, zStage, zPos);
        
        if (regionList.getNumberOfPositions() == regionNum) {
            regionList.addPosition(region);
        }
        if (regionList.getNumberOfPositions() > regionNum) {
            regionList.replacePosition(regionNum, region);
        }
        
        if (xPositionList.size() == regionNum) {
            xPositionList.add(xPos);
        }
        if (xPositionList.size() > regionNum) {
            xPositionList.set(regionNum, xPos);
        }
        if (yPositionList.size() == regionNum) {
            yPositionList.add(yPos);
        }
        if (yPositionList.size() > regionNum) {
            yPositionList.set(regionNum, yPos);
        }
        if (zPositionList.size() == regionNum) {
            zPositionList.add(zPos);
        }
        if (zPositionList.size() > regionNum) {
            zPositionList.set(regionNum, zPos);
        }
    }
    
    public void setRegion(int regionNum, MultiStagePosition region) {
        if (regionList.getNumberOfPositions() == regionNum) {
            regionList.addPosition(region);
        }
        if (regionList.getNumberOfPositions() > regionNum) {
            regionList.replacePosition(regionNum, region);
        }
    }
    
    public void setXPosition(int regionNum, double xPos) {
        if (xPositionList.size() == regionNum) {
            xPositionList.add(xPos);
        }
        else {
            xPositionList.set(regionNum, xPos);
        }
    }
    
    public void setYPosition(int regionNum, double yPos) {
        if (yPositionList.size() == regionNum) {
            yPositionList.add(yPos);
        }
        else {
            yPositionList.set(regionNum, yPos);
        }
    }
    
    public void setZPosition(int regionNum, double zPos) {
        if (zPositionList.size() == regionNum) {
            zPositionList.add(zPos);
        }
        else {
            zPositionList.set(regionNum, zPos);
        }
    }
    
    //gets current Z stage position, rounds to nearest hundredth micron, adds to start positions array
    //if value at index already exists, it replaces it
    public void setScanStartPosition(double startZ, int regionNum) {
        if (scanStartPositions.size() == regionNum) {
            scanStartPositions.add(startZ);
        }
        else {
            scanStartPositions.set(regionNum, startZ);
        }
    }
    
    //same as setZStartPositions except adds to end positions array
    public void setScanEndPosition(double endZ, int regionNum) {
        if (scanEndPositions.size() == regionNum) {
            scanEndPositions.add(endZ);
        }
        else {
            scanEndPositions.set(regionNum, endZ);
        }
    }
    
    //returns regionList
    public PositionList getPositionList() {
        return regionList;
    }
    
    public ArrayList<Double> getXPositionList() {
        return xPositionList;
    }
    
    public ArrayList<Double> getYPositionList() {
        return yPositionList;
    }
    
    public ArrayList<Double> getZPositionList() {
        return zPositionList;
    }
    
    //returns indexed z start position
    public ArrayList<Double> getScanStartPositionList() {
        return scanStartPositions;
    }
    
    //returns indexed z end position
    public ArrayList<Double> getScanEndPositionList() {
        return scanEndPositions;
    }
    public void positionArrayToRegion(int regionNum) {
        double xPos = xPositionList.get(regionNum);
        double yPos = yPositionList.get(regionNum);
        double zPos = zPositionList.get(regionNum);
        
        MultiStagePosition region = new MultiStagePosition(xyStage, xPos, yPos, zStage, zPos);
        if (regionList.getNumberOfPositions() == regionNum) {
            regionList.addPosition(region);
        }
        else {
            regionList.replacePosition(regionNum, region);
        }
    }
}
    

