/* This class sets up and sends properties to the camera and PLC with parameters
 * ContScanFrame
 */
package org.micromanager.ScanPlugin;

import mmcorej.CMMCore;
import org.micromanager.internal.MMStudio;

/**
 *
 * @author Raghu
 */
public class DeviceSetup {
    private final MMStudio mm_;
    private final CMMCore core_;
    public final String camName;
    public final String plcName;
    private final String triggerSource;
    private final String triggerPolarity;
    private final String trigger;
    private final String triggerSourceProp;
    private final String triggerPolartiyProp;
    private final String triggerProp;
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
    
    public DeviceSetup(MMStudio studio) {
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
        triggerSourceProp = "EXTERNAL";
        triggerPolartiyProp = "POSITIVE";
        triggerProp = "NORMAL";
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
    public void setPLCProperties(int exp, int stepSize) throws Exception {
        int exposure = exp * 4;
        int frameInterval = stepSize * 33 * 4;
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
        setProperty(plcName, propCellConfig, exposure);
        setProperty(plcName, propCellInput1, addrDelay2);
        setProperty(plcName, propCellInput2, addrClk);
                
        setProperty(plcName, propPosition, addrBNC1);
        setProperty(plcName, propCellType, valOutput);
        setProperty(plcName, propCellConfig, addrOneShot);
        setProperty(plcName, propCellInput1, 0);
        setProperty(plcName, propCellInput2, 0);
    }
    public void setCameraProperties() throws Exception {
        setProperty(camName, trigger, triggerProp);
        setProperty(camName, triggerPolarity, triggerPolartiyProp);
        setProperty(camName, triggerSource, triggerSourceProp);
    }
}

