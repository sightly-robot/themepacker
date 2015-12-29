package de.unihannover.swp2015.robots2.themepacker.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

import de.unihannover.swp2015.robots2.themepacker.util.PackUtil;
import de.unihannover.swp2015.robots2.themepacker.util.bridge.ActionEvent;
import de.unihannover.swp2015.robots2.themepacker.util.bridge.BridgeActionRoute;
import de.unihannover.swp2015.robots2.themepacker.util.bridge.BridgeUpdateRoute;
import de.unihannover.swp2015.robots2.themepacker.util.bridge.Dock;
import de.unihannover.swp2015.robots2.themepacker.util.bridge.UpdateEvent;

/**
 * Main controller of the application, which is responsible for packing the textures. Furthermore it
 * manages the model, which represents the state of the application.
 * 
 * @author Rico Schrage
 */
public class PackController extends Dock.Controller {
	
	/**
	 * Source directory, will be deleted after use.
	 */
	private static final File DEFAULT_SRC = new File("temp");
	
	/**
	 * Output directory (persistent). 
	 */
	private static final File DEFAULT_OUT = new File("output");
	
	/**
	 * Pack settings
	 */
	private static final Settings DEFAULT_SETTINGS = new Settings();
	
	/**
	 * Initializes the pack settings.
	 */
	static {
		DEFAULT_SETTINGS.maxWidth = 1024*4;
		DEFAULT_SETTINGS.maxHeight = 1024*4;
		DEFAULT_SETTINGS.pot = true;
		DEFAULT_SETTINGS.duplicatePadding = true;	
	}
		
	private final Map<String, File> texKeyToPath;
	
	/**
	 * Constructs the pack controller, which is responsible to pack the input files with the TexturePacker.
	 */
	public PackController() {
		this.texKeyToPath = new HashMap<>();
	}
	
	@BridgeActionRoute(ActionEvent.PACK_INVOKED)
	public void onStartPacking(final Object source) {
		try {
			final Map<String, File> newMap = PackUtil.moveFiles(texKeyToPath, DEFAULT_SRC);
			PackUtil.renameFilesToKey(newMap);
			PackUtil.packTheme(DEFAULT_SRC, DEFAULT_OUT, "test", DEFAULT_SETTINGS);
			if (!DEFAULT_SRC.delete()) {
				System.out.println("Deletion of the temporary folder failed!");
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@BridgeUpdateRoute(UpdateEvent.FILE_CHANGED)
	public void onFileChanged(final String name, final String newPath) {
		this.texKeyToPath.put(name, new File(newPath));
	}
	
}
