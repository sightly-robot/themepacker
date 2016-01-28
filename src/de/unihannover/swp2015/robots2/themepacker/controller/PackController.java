package de.unihannover.swp2015.robots2.themepacker.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

import de.unihannover.swp2015.robots2.themepacker.util.FileUtil;
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
	
	/** Logger (log4j) */
	private static final Logger log = LogManager.getLogger();
	
	/** Source directory, will be deleted after use. */
	private static final File DEFAULT_SRC = new File("temp");
	
	/** Output directory (persistent). */
	private static final File DEFAULT_OUT = new File("output");
	
	/** Pack settings */
	private static final Settings DEFAULT_SETTINGS = new Settings();
	
	/** maps possible textures to given paths */
	private final Map<String, File> texKeyToPath;

	/**
	 * Initializes the pack settings.
	 */
	static {
		DEFAULT_SETTINGS.maxWidth = 1024*4;
		DEFAULT_SETTINGS.maxHeight = 1024*4;
		DEFAULT_SETTINGS.pot = true;
		DEFAULT_SETTINGS.duplicatePadding = true;	
		DEFAULT_SETTINGS.paddingX = 4;
		DEFAULT_SETTINGS.paddingY = 4;
	}
			
	/**
	 * Constructs the pack controller, which is responsible to pack the input files with the TexturePacker.
	 */
	public PackController() {
		this.texKeyToPath = new HashMap<>();
	}
	
	/**
	 * Will be called when the pack button has been pressed.
	 * 
	 * @param source button which invoked packing
	 */
	@BridgeActionRoute(ActionEvent.PACK_INVOKED)
	public void onStartPacking(final Object source) {
		try {
			final Map<String, File> newMap = FileUtil.moveFiles(texKeyToPath, DEFAULT_SRC);
			FileUtil.renameFilesToKey(newMap);
			PackUtil.packTheme(DEFAULT_SRC, DEFAULT_OUT, "test", DEFAULT_SETTINGS);
			if (!FileUtil.deleteDirectory(DEFAULT_SRC)) {
				log.error("Deletion of the temporary folder: {} failed!", DEFAULT_SRC.getAbsolutePath());
			}
		} 
		catch (IOException e) {
			log.error("Moving files failed");
			log.error(e);
		}
	}
	
	/**
	 * Will be called when a text input has been changed.
	 * 
	 * @param name target file name
	 * @param newPath text input
	 */
	@BridgeUpdateRoute(UpdateEvent.FILE_CHANGED)
	public void onFileChanged(final String name, final String newPath) {
		this.texKeyToPath.put(name, new File(newPath));
	}
	
}
