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
 * 
 * This class holds parameters and methods that interact directly with the
 * hardware components such as the camera, PLC, and stage.
 * 
 * See the following for information on how to program the PLC:
 * http://www.asiimaging.com/downloads/manuals/Programmable_Logic_Card.pdf
 * 
 * Recent Changes:
 * - SYNCREADOUT property added to be able to better control exposure time during acquisition
 * 
 * To do:
 * - Add functionality for Hamamatsu Light Sheet Readout Mode, possibly all triggered through PLC?
 * 
 * 
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
    
    private final String triggerActiveProp;
    private final String scanTriggerSourceProp;
    private final String scanTriggerPolartiyProp;
    private final String defaultTriggerSourceProp;
    private final String defaultTriggerPolarityProp;
    private final String triggerActive;
    private final String triggerSource;
    private final String triggerPolarity;
    private final String trigger;
    private final String triggerProp;
    private final int scanExposure;
    private final int defaultExposure;
    
    private final String zStage;
    private final String xyStage;
    private final String tiger;
    private final String serial;
    private final String scanProperties;
    private String scanRProperties;
    private String scanStart;
    
    private int xPos;
    private int yPos;
    private int zPos;
    private final String zSpeedProperty;
    private final String xSpeedProperty;
    private final String ySpeedProperty;
    private final double xStageSpeed;
    private final double yStageSpeed;
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
        triggerActive = "TRIGGER ACTIVE";
        triggerSource = "TRIGGER SOURCE";
        triggerPolarity = "TriggerPolarity";
        trigger = "Trigger";
        triggerActiveProp = "SYNCREADOUT";
        triggerProp = "NORMAL";
        scanTriggerSourceProp = "EXTERNAL";
        scanTriggerPolartiyProp = "POSITIVE";
        defaultTriggerSourceProp = "INTERNAL";
        defaultTriggerPolarityProp = "NEGATIVE";
        scanExposure = 30;
        defaultExposure = 10;
        
        xyStage = core_.getXYStageDevice();
        zStage = core_.getFocusDevice();
        zSpeedProperty = "MotorSpeed-S(mm/s)";
        xSpeedProperty = "MotorSpeedX-S(mm/s)";
        ySpeedProperty = "MotorSpeedY-S(mm/s)";
        tiger = "TigerCommHub";
        serial = "SerialCommand";
        scanProperties = "2 SCAN Y=0 Z=0 F=0";
        scanStart = "2 SCAN";
        xStageSpeed = 1.0;
        yStageSpeed = 1.0;
        zScanSpeed = 0.03;
        zMoveSpeed = 0.5;
    }
    
    //Sets property of device in listed in MM device property browser
    private void setProperty(String device, String prop, Object value) throws Exception {
        if (value instanceof String) {
            String val = (String) value;
            core_.setProperty(device, prop, val);
        }
        
        if (value instanceof Integer) {
            int val = (int) value;
            core_.setProperty(device, prop, val);
        }
        
        if (value instanceof Double) {
            double val = (double) value;
            core_.setProperty(device, prop, val);
        }
    }
    
    //Sets PLC properties for use during ZStack acquisition. See plugin guide
    //for circuit details.
    public void initializePLC(int stepSize) throws Exception {
        
        int triggerPulseWidth = 20;
        int frameInterval = (int) Math.round((stepSize / zScanSpeed) * 4);
        
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
        setProperty(plcName, propCellConfig, triggerPulseWidth);
        setProperty(plcName, propCellInput1, addrDelay2);
        setProperty(plcName, propCellInput2, addrClk);
                
        setProperty(plcName, propPosition, addrBNC1);
        setProperty(plcName, propCellType, valOutput);
        setProperty(plcName, propCellConfig, addrOneShot);
        setProperty(plcName, propCellInput1, 0);
        setProperty(plcName, propCellInput2, 0);
    }
    
    public void setPLCProperties(int stepSize) throws Exception {
        
        int triggerPulseWidth = 20;
        int frameInterval = (int) Math.round((stepSize / zScanSpeed) * 4);
                
        setProperty(plcName, propPosition, addrDelay2);
        setProperty(plcName, propCellType, valDelay);
        setProperty(plcName, propCellConfig, frameInterval);
        setProperty(plcName, propCellInput1, addrAND);
        setProperty(plcName, propCellInput2, addrClk);
                
        setProperty(plcName, propPosition, addrOneShot);
        setProperty(plcName, propCellType, valOneShot);
        setProperty(plcName, propCellConfig, triggerPulseWidth);
        setProperty(plcName, propCellInput1, addrDelay2);
        setProperty(plcName, propCellInput2, addrClk);
    }
    
    //Sets camera properties for ZStack Acquisition
    public void setScanCameraProperties() throws Exception {
        setProperty(camName, trigger, triggerProp);
        setProperty(camName, triggerPolarity, scanTriggerPolartiyProp);
        setProperty(camName, triggerSource, scanTriggerSourceProp);
        setProperty(camName, triggerActive, triggerActiveProp);
        core_.setExposure(scanExposure);
    }
    
    //Sets camera properties back to default MM settings
    public void setDefaultCameraProperties() throws Exception {
        setProperty(camName, trigger, triggerProp);
        setProperty(camName, triggerPolarity, defaultTriggerPolarityProp);
        setProperty(camName, triggerSource, defaultTriggerSourceProp);
        core_.setExposure(defaultExposure);
    }
    
    public void setZStageSpeed(double zSpeedInmmPerSecond) throws Exception {
        setProperty(zStage, zSpeedProperty, String.valueOf(zSpeedInmmPerSecond));
    }
    
    public void setXYStageSpeed(double xSpeedInmmPerSecond, double ySpeedInmmPerSecond) throws Exception {
        setProperty(xyStage, xSpeedProperty, String.valueOf(xSpeedInmmPerSecond));
        setProperty(xyStage, ySpeedProperty, String.valueOf(ySpeedInmmPerSecond));
    }
    
    //Initializes SCAN command on the ASI Tiger Console for use in ZStack.
    public void scanSetup (int startZ, int endZ) throws Exception {
        double startZmm = Math.round(startZ) / 1000.;
        double endZmm = Math.round(endZ) / 1000.;
        System.out.println(startZmm);
        System.out.println(endZmm);
        scanRProperties = "2 SCANR X=" + startZmm + " Y=" + endZmm;
        setProperty(tiger, serial, scanProperties);
        setProperty(tiger, serial, scanRProperties);
    }
    
    //Starts stage scan using SCAN command
    public void scanStart () throws Exception {
        setZStageSpeed(zScanSpeed);
        setProperty(tiger, serial, scanStart);
    }
    
    public void moveStage(double xPosition, double yPosition, double zPosition) throws Exception {
        setXYStageSpeed(xStageSpeed, yStageSpeed);
        setZStageSpeed(zMoveSpeed);
        
        //Due to how the current capillary holder is, this ensures that
        //capillaries won't hit the objective. See plugin guide for details.
        double currentXPosition = core_.getXPosition();
        if (currentXPosition > xPosition) {
            core_.setPosition(zStage, zPosition);
            core_.waitForDevice(zStage);
            core_.setXYPosition(xPosition, yPosition);
            core_.waitForDevice(xyStage);
        } else {
            core_.setXYPosition(xPosition, yPosition);
            core_.waitForDevice(xyStage);
            core_.setPosition(zStage, zPosition);
            core_.waitForDevice(zStage);
        }
    }
    
    //Gets current x-position in microns from stage
    public int getXPosition() {
        try {
            xPos =  (int) Math.round(core_.getXPosition(xyStage));
        } catch (Exception ex) {
            Logger.getLogger(HardwareCommands.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xPos;
    }
    
    //Gets current y-position in microns from stage
    public int getYPosition() {
        try {
            yPos =  (int) Math.round(core_.getYPosition(xyStage));
        } catch (Exception ex) {
            Logger.getLogger(HardwareCommands.class.getName()).log(Level.SEVERE, null, ex);
        }
        return yPos;
    }
    
    //Gets current z-position in microns from stage
    public int getZPosition() {
        try {
            zPos = (int) Math.round(core_.getPosition(zStage));
        } catch (Exception ex) {
            Logger.getLogger(HardwareCommands.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zPos;
    }
    
    //returns name of xy stage device
    public String getXYStageDevice() {
        return xyStage;
    }
    
    
    //returns name of z stage device
    public String getZStageDevice() {
        return zStage;
    }
    
    public void resetJoystick() throws Exception {
        setProperty(tiger, serial, "J X+");
        setProperty(tiger, serial, "J Y+");
        setProperty(tiger, serial, "J Z+");
    }
}

