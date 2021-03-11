/* This class sets up and sends properties to the camera and PLC with parameters
 * ContScanFrame
 */
package org.micromanager.ScanPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;
import mmcorej.CMMCore;
import org.micromanager.MultiStagePosition;
import org.micromanager.internal.MMStudio;

/**
 *
 * @author Raghu
 */
public class HardwareCommands {
    private final MMStudio mm_;
    private final CMMCore core_;
    public final String plcName;
    public final String camName;
    private final String propPosition;
    private final String propCellType;
    private final String propCellConfig;
    private final String propCellInput1;
    private final String propCellInput2;
    private final String valAND;
    private final String valOR;
    private final String valOneShot;
    private final String valDelay;
    private final String valOutput;
    private final String valInput;
    private final int addrClk;
    private final int addrBNC1;
    private final int addrStageTTL;
    private final int addrDelay1;
    private final int addrOR;
    private final int addrAND;
    private final int addrDelay2;
    private final int addrOneShot;
    private final String scanTriggerSourceProp;
    private final String scanTriggerPolartiyProp;
    private final String defaultTriggerSourceProp;
    private final String defaultTriggerPolarityProp;
    private final String triggerSource;
    private final String triggerPolarity;
    private final String trigger;
    private final String triggerProp;
    private final String zStage;
    private final String xyStage;
    private final String tiger;
    private final String serial;
    private final String scanProperties;
    private String scanRProperties;
    private String scanStart;
    private double xPos;
    private double yPos;
    private double zPos;
    private final String zSpeedProperty;
    private final String xSpeedProperty;
    private final String ySpeedProperty;
    private final double xStageSpeed;
    private final double yStageSpeed;
    private final int exposure;
    private final double zScanSpeed;
    private final double zMoveSpeed;
    
    public HardwareCommands(MMStudio studio) {
        mm_ = (MMStudio) studio;
        core_ = mm_.core();
        
        plcName = "PLogic:E:36";
        propPosition = "PointerPosition";
        propCellType = "EditCellCellType";
        propCellConfig = "EditCellConfig";
        propCellInput1 = "EditCellInput1";
        propCellInput2 = "EditCellInput2";
        valAND = "5 - 2-input AND";
        valOR = "6 - 2-input OR";
        valOneShot = "8 - one shot";
        valDelay = "9 - delay";
        valOutput = "2 - output (push-pull)";
        valInput = "0 - input";
        addrClk = 192;
        addrBNC1 = 33;
        addrStageTTL = 46;
        addrDelay1 = 1;
        addrOR = 2;
        addrAND = 3;
        addrDelay2 = 4;
        addrOneShot = 5;
        
        camName = core_.getCameraDevice();
        triggerSource = "TRIGGER SOURCE";
        triggerPolarity = "TriggerPolarity";
        trigger = "Trigger";
        triggerProp = "NORMAL";
        scanTriggerSourceProp = "EXTERNAL";
        scanTriggerPolartiyProp = "POSITIVE";
        defaultTriggerSourceProp = "INTERNAL";
        defaultTriggerPolarityProp = "NEGATIVE";
        exposure = 10;
        
        xyStage = core_.getXYStageDevice();
        zStage = core_.getFocusDevice();
        zSpeedProperty = "MotorSpeed-S(mm/s)";
        xSpeedProperty = "MotorSpeedX-S(mm/s)";
        ySpeedProperty = "MotorSpeedY-S(mm/s)";
        tiger = "TigerCommHub";
        serial = "SerialCommand";
        scanProperties = "2 SCAN Y=0 Z=9 F=0";
        scanStart = "2 SCAN";
        xStageSpeed = 2.0;
        yStageSpeed = 2.0;
        zScanSpeed = 0.03;
        zMoveSpeed = 1.0;
    }
    
    private void setProperty(String device, String prop, String value) throws Exception {
        core_.setProperty(device, prop, value);
    }
    private void setProperty(String device, String prop, int value) throws Exception {
        core_.setProperty(device, prop, value);
    }
    private void setProperty(String device, String prop, double value) throws Exception {
        core_.setProperty(device, prop, value);
    }
    
    public void setPLCProperties(int stepSize) throws Exception {
        
        int exp = exposure * 4;
        int frameInterval = (int) (1 / zScanSpeed) * 4;
        
        setProperty(plcName, propPosition, addrDelay1);
        setProperty(plcName, propCellType, valDelay);
        setProperty(plcName, propCellConfig, 0);
        setProperty(plcName, propCellInput1, addrStageTTL);
        setProperty(plcName, propCellInput2, addrClk);
                
        setProperty(plcName, propPosition, addrOR);
        setProperty(plcName, propCellType, valOR);
        setProperty(plcName, propCellConfig, 0);
        setProperty(plcName, propCellInput1, addrDelay1);
        setProperty(plcName, propCellInput2, addrDelay2);
                
        setProperty(plcName, propPosition, addrAND);
        setProperty(plcName, propCellType, valAND);
        setProperty(plcName, propCellConfig, 0);
        setProperty(plcName, propCellInput1, addrOR);
        setProperty(plcName, propCellInput2, addrStageTTL);
                
        setProperty(plcName, propPosition, addrDelay2);
        setProperty(plcName, propCellType, valDelay);
        setProperty(plcName, propCellConfig, frameInterval);
        setProperty(plcName, propCellInput1, addrAND);
        setProperty(plcName, propCellInput2, addrClk);
                
        setProperty(plcName, propPosition, addrOneShot);
        setProperty(plcName, propCellType, valOneShot);
        setProperty(plcName, propCellConfig, exp);
        setProperty(plcName, propCellInput1, addrDelay2);
        setProperty(plcName, propCellInput2, addrClk);
                
        setProperty(plcName, propPosition, addrBNC1);
        setProperty(plcName, propCellType, valOutput);
        setProperty(plcName, propCellConfig, addrOneShot);
        setProperty(plcName, propCellInput1, 0);
        setProperty(plcName, propCellInput2, 0);
    }
    
    public void setScanCameraProperties() throws Exception {
        setProperty(camName, trigger, triggerProp);
        setProperty(camName, triggerPolarity, scanTriggerPolartiyProp);
        setProperty(camName, triggerSource, scanTriggerSourceProp);
    }
    
    public void setDefaultCameraProperties() throws Exception {
        setProperty(camName, trigger, triggerProp);
        setProperty(camName, triggerPolarity, defaultTriggerPolarityProp);
        setProperty(camName, triggerSource, defaultTriggerSourceProp);
    }
    
    public void setZStageSpeed(double zSpeedInmmPerSecond) throws Exception {
        setProperty(zStage, zSpeedProperty, String.valueOf(zSpeedInmmPerSecond));
    }
    
    public void setXYStageSpeed(double xSpeedInmmPerSecond, double ySpeedInmmPerSecond) throws Exception {
        setProperty(xyStage, xSpeedProperty, String.valueOf(xSpeedInmmPerSecond));
        setProperty(xyStage, ySpeedProperty, String.valueOf(ySpeedInmmPerSecond));
    }
    
    public void scanSetup (double startZ, double endZ) throws Exception {
        double startZmm = startZ * 0.001;
        double endZmm = endZ * 0.001;
        scanRProperties = "2 SCANR X=" + startZmm + " Y=" + endZmm;
        setProperty(tiger, serial, scanProperties);
        setProperty(tiger, serial, scanRProperties);
    }
    
    public void scanStart () throws Exception {
        setZStageSpeed(zScanSpeed);
        setProperty(tiger, serial, scanStart);
    }
    
    public void moveStage(MultiStagePosition region) throws Exception {
        setZStageSpeed(zMoveSpeed);
        setXYStageSpeed(xStageSpeed, yStageSpeed);
        core_.setPosition(zStage, region.getZ());
        core_.setXYPosition(xyStage, region.getX(), region.getY());
    }
    
    public void moveStage(double xPos, double yPos, double zPos) throws Exception {
        setZStageSpeed(zMoveSpeed);
        setXYStageSpeed(xStageSpeed, yStageSpeed);
        core_.setPosition(zStage, zPos);
        core_.setXYPosition(xyStage, xPos, yPos);
    }
    
    public double getXPosition() {
        try {
            xPos =  Math.round(core_.getXPosition(xyStage) * 100) / 100;
        } catch (Exception ex) {
            Logger.getLogger(HardwareCommands.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xPos;
    }
    
    public double getYPosition() {
        try {
            yPos =  Math.round(core_.getYPosition(xyStage) * 100) / 100;
        } catch (Exception ex) {
            Logger.getLogger(HardwareCommands.class.getName()).log(Level.SEVERE, null, ex);
        }
        return yPos;
    }
    
    public double getZPosition() {
        try {
            zPos =  Math.round(core_.getPosition(zStage) * 100) / 100;
        } catch (Exception ex) {
            Logger.getLogger(HardwareCommands.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zPos;
    }
    
    public MultiStagePosition getStagePosition() {
        xPos = getXPosition();
        yPos = getYPosition();
        zPos = getZPosition();
        return new MultiStagePosition(xyStage, xPos, yPos, zStage, zPos);
        
    }
}

