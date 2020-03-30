package org.micromanager.ScanPlugin;

import mmcorej.CMMCore;
import org.micromanager.MultiStagePosition;
import org.micromanager.internal.MMStudio;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Raghu
 */
public class StageCommands {
    private final MMStudio mm_;
    private CMMCore core_;
    private final String zStage;
    private final String xyStage;
    private final String speedProperty;
    private final String tiger;
    private final String serial;
    private final String scanProperties;
    private String scanRProperties;
    private String scanStart;
    private final Double scanSpeed;
    private final Double moveSpeed;
    
    public StageCommands(MMStudio studio){
        mm_ = (MMStudio) studio;
        core_ = studio.core();
        xyStage = core_.getXYStageDevice();
        zStage = core_.getFocusDevice();
        speedProperty = "MotorSpeed=S(mm/s)";
        tiger = "TigerCommHub";
        serial = "SerialCommand";
        scanProperties = "2 SCAN Y=0 Z=9 F=0";
        scanStart = "2 SCAN";
        scanSpeed = 0.03;
        moveSpeed = 0.3;
    }
    
    private void setProperty(String device, String prop, String value) throws Exception {
        core_.setProperty(device, prop, value);
    }
    
    public void setZStageSpeed(double speedInmmPerSecond) throws Exception {
        setProperty(zStage, speedProperty, String.valueOf(speedInmmPerSecond));
    }
    
    public void scanSetup (double startZ, double endZ) throws Exception {
        double startZmm = startZ * 0.001;
        double endZmm = endZ * 0.001;
        scanRProperties = "2 SCANR X=" + startZmm + " Y=" + endZmm;
        setProperty(tiger, serial, scanProperties);
        setProperty(tiger, serial, scanRProperties);
    }
    
    public void scanStart () throws Exception {
        setZStageSpeed(scanSpeed);
        setProperty(tiger, serial, scanStart);
    }
    
    public void moveStage(MultiStagePosition region) throws Exception {
        setZStageSpeed(moveSpeed);
        core_.setPosition(zStage, region.getZ());
        core_.setXYPosition(xyStage, region.getX(), region.getY());
    }
    
}
