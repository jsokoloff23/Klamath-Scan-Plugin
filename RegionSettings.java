/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.micromanager.ScanPlugin;

import java.util.ArrayList;
import org.micromanager.MultiStagePosition;


/**
 *
 * @author Raghu
 * 
 * This class stores all of the 
 */
public class RegionSettings {
    private int xPosition;
    private int yPosition;
    private int zPosition;
    private int fishNumber;
    private int regionNumber;
    private boolean zStackBoolean;
    private int zStartPosition;
    private int zEndPosition;
    private int stepSize;
    private ArrayList<String> zStackChannelArray;
    private boolean videoBoolean;
    private double videoDurationInSeconds;
    private double videoExposureTime;
    private ArrayList<String> videoChannelArray;
    private boolean snapBoolean;
    private ArrayList<String> snapChannelArray;
    
    public RegionSettings() {
        xPosition = 0;
        yPosition = 0;
        zPosition = 0;
        
        fishNumber = 0;
        regionNumber = 0;
        
        zStackBoolean = false;
        zStartPosition = 0;
        zEndPosition = 0;
        stepSize = 1;
        zStackChannelArray = new ArrayList<String>();
        
        videoBoolean = false;
        videoDurationInSeconds = 0.0;
        videoExposureTime = 0.0;
        videoChannelArray = new ArrayList<String>();
        
        snapBoolean = false;
        snapChannelArray = new ArrayList<String>();
    }
    
    public void setXPosition(int xPos) {
        xPosition = xPos;
    }
    
    public void setYPosition(int yPos) {
        yPosition = yPos;
    }
    
    public void setZPosition(int zPos) {
        zPosition = zPos;
    }
    
    public void setFishNumber(int fishNum) {
        fishNumber = fishNum;
    }
    
    public void setRegionNumber(int regionNum) {
        regionNumber = regionNum;
    }
    
    public void setZStackBoolean(boolean zStack) {
        zStackBoolean = zStack;
    }
    
    public void setVideoBoolean(boolean video) {
        videoBoolean = video;
    }
    
    public void setZStartPosition(int zPos) {
        zStartPosition = zPos;
    }
    
    public void setZEndPosition (int zPos) {
        zEndPosition = zPos;
    }
    
    public void setStepSize (int step) {
        stepSize = step;
    }
 
    public void setVideoDurationInSeconds(double duration) {
        videoDurationInSeconds = duration;
    }
    
    public void setVideoExposureTime(double exp) {
        videoExposureTime = exp;
    }
    
    public void setZStackChannelArray(ArrayList<String> channelArray) {
        zStackChannelArray = channelArray;
    }
    
    public void setVideoChannelArray(ArrayList<String> channelArray) {
        videoChannelArray = channelArray;
    } 
    
    public void setSnapBoolean(Boolean snap) {
        snapBoolean = snap;
    }    
    
    public void setSnapChannelArray(ArrayList<String> channelArray) {
        snapChannelArray = channelArray;
    }    
    
    public int getXPosition() {
        return xPosition;
    }
    
    public int getYPosition() {
        return yPosition;
    }
    
    public int getZPosition() {
        return zPosition;
    }
    
    public int getFishNumber() {
        return fishNumber;
    }
    
    public int getRegionNumber() {
        return regionNumber;
    }
    
    public boolean getZStackBoolean() {
        return zStackBoolean;
    }
    
    public boolean getVideoBoolean() {
        return videoBoolean;
    }
    
    public int getZStartPosition() {
        return zStartPosition;
    }
    
    public int getZEndPosition() {
        return zEndPosition;
    }
    
    public int getStepSize() {
        return stepSize;
    }
    
    public double getVideoDurationInSeconds() {
        return videoDurationInSeconds;
    }
    
    public double getVideoExposureTime() {
        return videoExposureTime;
    }
    
    public ArrayList<String> getZStackChannelArray() {
        return zStackChannelArray;
    }
    
    public ArrayList<String> getVideoChannelArray() {
        return videoChannelArray;
    }
    
    public boolean getSnapBoolean() {
        return snapBoolean;
    }
    
    public ArrayList<String> getSnapChannelArray() {
        return snapChannelArray;
    }
}
