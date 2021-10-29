/* This class sets up the position list as well as start and end positions
 * for Z-Stacks
 */
package org.micromanager.ScanPlugin;

import java.util.ArrayList;
import org.micromanager.internal.MMStudio;
import mmcorej.CMMCore;
import org.micromanager.MultiStagePosition;

/**
 *
 * @author Jonah Sokoloff
 */
    public class AcquisitionSettings {
   
    private static MMStudio mm_;
    private static CMMCore core_;
    public static Integer stepSize;
    public static Boolean timePointsBoolean;
    public static Integer numTimePoints;
    public static Integer timePointsInterval;
    private final ArrayList<RegionSettings> regionSettingsArray;
    private String channelGroupName;
    private final int numImages;
    private ArrayList channelOrderArray;
    private String directory;
    
    
    public AcquisitionSettings(MMStudio studio) {
        mm_ = (MMStudio) studio;
        core_ = mm_.core();
        regionSettingsArray = new ArrayList();
        channelGroupName = "Channel";
        directory = "F:\\";
        channelOrderArray = new ArrayList();
        timePointsBoolean = false;
        timePointsInterval = 0;
        numTimePoints = 1;
        numImages = 0;
    }
    
    public void addRegionSettings(RegionSettings regionSettings, int fishNum, int fishRegionNum, int index) {
        Boolean foundBoolean = false;
        for (RegionSettings regionSettingsElement: regionSettingsArray) {
            if (regionSettingsElement.getFishNumber() == fishNum & regionSettingsElement.getRegionNumber() == fishRegionNum ) {
                foundBoolean = true;
            }
        }
        
        if (foundBoolean) {
            regionSettingsArray.set(index, regionSettings);
        }
        
        else {
            if (regionSettingsArray.size() < index + 1) {
                regionSettingsArray.add(regionSettings);
            } else {
                regionSettingsArray.add(index, regionSettings);
            }
        }
    }
    
    public void removeRegionSettings(RegionSettings regionSettings, int fishNum, int fishRegionNum, int index) {
        regionSettingsArray.remove(index);
        
        Boolean foundBoolean = false;
        for (RegionSettings regionSettingsElement : regionSettingsArray) {
            if (regionSettingsElement.getFishNumber() == fishNum) {
                foundBoolean = true;
                if (regionSettingsElement.getRegionNumber() > fishRegionNum) {
                    regionSettingsElement.setRegionNumber(regionSettingsElement.getRegionNumber() - 1);
                }
            }
        }
        if (!foundBoolean) {
            for (RegionSettings regionSettingsElement : regionSettingsArray) {
                    if (regionSettingsElement.getFishNumber() > fishNum) {
                        regionSettingsElement.setFishNumber(regionSettingsElement.getFishNumber() - 1);
                }
            }
        }
    }

    public void setTimePointsBoolean(boolean timePoints) {
        timePointsBoolean = timePoints;
    }
    
    public void setTimePointsInterval(int interval) {
        timePointsInterval = interval;
    }
    
    public void setNumTimePoints(int num) {
        numTimePoints = num;
    }
    
    public void setStepSize(int step) {
        stepSize = step;
    }
    
    public void setChannelGroupName(String channelGroup) {
        channelGroupName = channelGroup;
    }
    
    public void setChannelOrderArray(ArrayList channelArray) {
        channelOrderArray = channelArray;
    }
    
    public void setDirectory(String dir) {
        directory = dir;
    }
    
    public RegionSettings getRegionSettings(int regionNum) {
        return regionSettingsArray.get(regionNum);
    }
    
    public ArrayList<RegionSettings> getRegionSettingsArray() {
        return regionSettingsArray;
    }
    
    public String getChannelGroupName() {
        return channelGroupName;
    }
    
    public int getNumTimePoints() {
        return numTimePoints;
    }
    
    public int getTimePointsInterval() {
        return timePointsInterval;
    }
    
    public String getDirectory() {
        return directory;
    }
    
    public ArrayList<String> getChannelOrderArray() {
        return channelOrderArray;
    }
    
    public Boolean getTimePointsBoolean() {
        return timePointsBoolean;
    }
}
    

