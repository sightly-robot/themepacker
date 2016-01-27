package de.unihannover.swp2015.robots2.themepacker.util;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

/**
 * Contains several IO utilities + pack utilities.
 * 
 * @author Rico Schrage
 *
 */
public class PackUtil {
	
	/** Logger (log4j) */
	private static final Logger LOGGER = LogManager.getLogger();
	
	private PackUtil() {
		//utility class
	}
	
	/**
	 * Packs the textures from the given source folder to the give target folder using {@link TexturePacker}.
	 * 
	 * @param src source folder
	 * @param target target folder 
	 * @param themeName name/id of the theme
	 * @param settings pack settings
	 * @throws IOException thrown when folder could not be created
	 */
	public static void packTheme(final File src, final File target, final String themeName, Settings settings) throws IOException {
		if (!target.exists() && !target.mkdir()) {
			throw new IOException("Creating the directory " + target.toString() + " failed!");
		}
		LOGGER.info("Start packing: src {}, target {}, themeName {}", src.getAbsolutePath(), target.getAbsolutePath(), themeName);
		TexturePacker.process(settings, src.getAbsolutePath(), target.getAbsolutePath(), themeName);
		LOGGER.info("Packing succeeded");
	}
	
}
