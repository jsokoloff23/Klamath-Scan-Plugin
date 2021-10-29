/* This is the class that interacts with the plugin manager in MicroManager.
 * It tells it what name to list in the plugins menu, what to open when clicked on,
 * and sets up the main instances of MMStudio and CMMCore
 *
 * See: https://valelab4.ucsf.edu/~MM/doc-2.0.0-gamma/mmstudio/org/micromanager/PluginManager.html
 * 
 */

package org.micromanager.ScanPlugin;

import mmcorej.CMMCore;
import org.micromanager.MenuPlugin;
import org.micromanager.Studio;
import org.micromanager.internal.MMStudio;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.SciJavaPlugin;

/**
 *
 * @author Jonah Sokoloff
 */

@Plugin(type = MenuPlugin.class)

//defines class as a menu plugin
public class ContinuousScan implements MenuPlugin, SciJavaPlugin {
   private CMMCore core_;
   private MMStudio mm_;
   private Controller controller;

    @Override
    public String getSubMenu() {
        return "";
    }

    //action performed when plugin is selected in plugin menu
    @Override
    public void onPluginSelected() {
        if (controller == null)  {
            try {
                controller = new Controller(mm_);
            }
            catch (Exception e) {
                    return;
            }
        } else {
            controller.setRegionSettingsFrameVisible(true);
        }
    }
    
    //sets the main instances of MMStudio and CMMCore objects
    @Override
    public void setContext(Studio studio) {
      mm_ = (MMStudio) studio;
      core_ = studio.core();
    }

    @Override
    public String getName() {
        return "Continuous Stage Scan";
    }

    @Override
    public String getHelpText() {
        return "Takes continuous stage scan of multiple regions. Select regions and then select start and end z for each region.";
    }

    @Override
    public String getVersion() {
        return "V1.0";
    }

    @Override
    public String getCopyright() {
        return "University of Oregon, 2020";
    }
}
