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
    public class ScanSettings {
   
    private static MMStudio mm_;
    private static CMMCore core_;
    private final HardwareCommands hardwareCommands;
    private final PositionList regionList;
    private final ArrayList<Double> xPositionArray;
    private final ArrayList<Double> yPositionArray;
    private final ArrayList<Double> zPositionArray;
    private final ArrayList<Double> scanStartPositionArray;
    private final ArrayList<Double> scanEndPositionArray;
    private final ArrayList<Boolean> brightFieldArray;
    private final ArrayList<String> channelArray;
    public static String channelGroupName;
    private static MultiStagePosition region;
    private static DecimalFormat dc;
    public static Integer stepSize;
    private static String zStage;
    private static String xyStage;
    public static Boolean timePointsBoolean;
    public static Integer numTimePoints;
    public static Integer timePointsInterval;
    
    
    public ScanSettings(MMStudio studio) {
        mm_ = (MMStudio) studio;
        core_ = mm_.core();
        hardwareCommands = new HardwareCommands(mm_);
        regionList = new PositionList();
        xPositionArray = new ArrayList<>();
        yPositionArray = new ArrayList<>();
        zPositionArray = new ArrayList<>();
        scanStartPositionArray = new ArrayList<>();
        scanEndPositionArray = new ArrayList<>();
        zStage =  "ZStage:Z:32";
        xyStage = "XYStage:XY:31";
        brightFieldArray = new ArrayList<>();
        channelArray = new ArrayList<>();
        channelGroupName = "Channel";
        timePointsBoolean = false;
        timePointsInterval = 0;
        stepSize = 1;
        numTimePoints = 1;
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
        
        if (xPositionArray.size() == regionNum) {
            xPositionArray.add(xPos);
        }
        if (xPositionArray.size() > regionNum) {
            xPositionArray.set(regionNum, xPos);
        }
        if (yPositionArray.size() == regionNum) {
            yPositionArray.add(yPos);
        }
        if (yPositionArray.size() > regionNum) {
            yPositionArray.set(regionNum, yPos);
        }
        if (zPositionArray.size() == regionNum) {
            zPositionArray.add(zPos);
        }
        if (zPositionArray.size() > regionNum) {
            zPositionArray.set(regionNum, zPos);
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
        if (xPositionArray.size() == regionNum) {
            xPositionArray.add(xPos);
        }
        else {
            xPositionArray.set(regionNum, xPos);
        }
    }
    
    public void setYPosition(int regionNum, double yPos) {
        if (yPositionArray.size() == regionNum) {
            yPositionArray.add(yPos);
        }
        else {
            yPositionArray.set(regionNum, yPos);
        }
    }
    
    public void setZPosition(int regionNum, double zPos) {
        if (zPositionArray.size() == regionNum) {
            zPositionArray.add(zPos);
        }
        else {
            zPositionArray.set(regionNum, zPos);
        }
    }
    
    //gets current Z stage position, rounds to nearest hundredth micron, adds to start positions array
    //if value at index already exists, it replaces it
    public void setScanStartPosition(double startZ, int regionNum) {
        if (scanStartPositionArray.size() == regionNum) {
            scanStartPositionArray.add(startZ);
        }
        else {
            scanStartPositionArray.set(regionNum, startZ);
        }
    }
    
    //same as setZStartPositions except adds to end positions array
    public void setScanEndPosition(double endZ, int regionNum) {
        if (scanEndPositionArray.size() == regionNum) {
            scanEndPositionArray.add(endZ);
        }
        else {
            scanEndPositionArray.set(regionNum, endZ);
        }
    }
    
    //returns regionList
    public PositionList getPositionList() {
        return regionList;
    }
    
    public ArrayList<Double> getXPositionArray() {
        return xPositionArray;
    }
    
    public ArrayList<Double> getYPositionArray() {
        return yPositionArray;
    }
    
    public ArrayList<Double> getZPositionArray() {
        return zPositionArray;
    }
    
    //returns indexed z start position
    public ArrayList<Double> getScanStartPositionArray() {
        return scanStartPositionArray;
    }
    
    //returns indexed z end position
    public ArrayList<Double> getScanEndPositionArray() {
        return scanEndPositionArray;
    }
    public void positionArrayToRegion(int regionNum) {
        double xPos = xPositionArray.get(regionNum);
        double yPos = yPositionArray.get(regionNum);
        double zPos = zPositionArray.get(regionNum);
        
        MultiStagePosition region = new MultiStagePosition(xyStage, xPos, yPos, zStage, zPos);
        if (regionList.getNumberOfPositions() == regionNum) {
            regionList.addPosition(region);
        }
        else {
            regionList.replacePosition(regionNum, region);
        }
    }
    
    public void initializeBrightFieldArray() {
        if (brightFieldArray.size() < regionList.getNumberOfPositions()) {
            for (int regions = 0; regions < regionList.getNumberOfPositions() ; regions++) {
                brightFieldArray.add(false);
            }
        }
    }
    
    public void setBrightFieldArrayElement(boolean bf, int regionNum) {
        if (brightFieldArray.size() < regionNum + 1) {
                brightFieldArray.add(bf);
            }
            else {
                brightFieldArray.set(regionNum, bf);
            }
    }
    
    public ArrayList<Boolean> getBrightFieldArray() {
        return brightFieldArray;
    }
    
    public void addChannel(String channel) {
        channelArray.add(channel);
    }
    
    public ArrayList<String> getChannelArray() {
        return channelArray;
    }
    
    
}
    

