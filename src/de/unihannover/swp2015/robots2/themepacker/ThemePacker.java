package de.unihannover.swp2015.robots2.themepacker;

import java.awt.Dimension;

import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Frame;
import org.apache.pivot.wtk.Theme;
import org.apache.pivot.wtk.skin.terra.TerraFrameSkin;

import de.unihannover.swp2015.robots2.themepacker.controller.ViewActionHandler;
import de.unihannover.swp2015.robots2.themepacker.controller.PackController;
import de.unihannover.swp2015.robots2.themepacker.util.bridge.Bridge;
import de.unihannover.swp2015.robots2.themepacker.util.bridge.Dock.Controller;
import de.unihannover.swp2015.robots2.themepacker.util.bridge.Dock.View;
import de.unihannover.swp2015.robots2.themepacker.util.bridge.Dock.ViewController;
import de.unihannover.swp2015.robots2.themepacker.view.ThemePackerView;
import de.unihannover.swp2015.robots2.themepacker.window.MainFrame;

/**
 * Main entry class of the application.
 * 
 * @author Rico Schrage
 */
public class ThemePacker implements Application {
	
	/**
	 * Initial width of the window.
	 */
	private static final int WIDTH = 600;

	/**
	 * Initial height of the window.
	 */
	private static final int HEIGHT = 400;
	
	/**
	 * Main window.
	 */
    private MainFrame window = null;
    
    /**
     * Main controller of the application.
     */
    private Controller controller = null;
    
    /**
     * Main view controller of the application.
     */
    private ViewController viewController = null;
    
    /**
     * Main view of the application.
     */
	private View view = null;
	
	/**
	 * Main bridge of the application. Connects controller, view and controller-view.
	 */
	private Bridge bridge = null;
    
	/**
	 * Main entry point.
	 * 
	 * @param args console arguments
	 */
	public static void main(String[] args) {
		DesktopApplicationContext.main(ThemePacker.class, args);
		Theme.getTheme().set(Frame.class, TerraFrameSkin.class);
	}

	@Override
	public void startup(Display display, Map<String, String> arg1) throws Exception {
		display.getHostWindow().setSize(new Dimension(WIDTH, HEIGHT));
		
		BXMLSerializer bxmlSerializer = new BXMLSerializer();
        bxmlSerializer.getNamespace().put("themepacker", this);
      
        window = (MainFrame) bxmlSerializer.readObject(getClass().getResource("/de/unihannover/swp2015/robots2/themepacker/bxml/ThemePackerView.bxml"));
        window.setPreferredSize(WIDTH, HEIGHT);
        window.open(display);
        
        bridge = new Bridge();

        controller = new PackController();
        viewController = new ViewActionHandler();
        view = new ThemePackerView(window);
                
        controller.connectBridge(bridge);
        viewController.connectBridge(bridge);
        view.connectBridge(bridge);
    }
	
	@Override
	public boolean shutdown(boolean arg0) throws Exception {
		if (window != null) {
			window.close();
        }
        return false;
	}

	@Override
	public void resume() throws Exception {
		// nothing to do
	}

	@Override
	public void suspend() throws Exception {
		// nothing to do
	}

}
