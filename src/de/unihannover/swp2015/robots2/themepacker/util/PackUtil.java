package de.unihannover.swp2015.robots2.themepacker.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class PackUtil {
	
	public static void packTheme(final File src, final File target, final String themeName, Settings settings) throws IOException {
		if (!target.exists()) {
			if (!target.mkdir()) {
				throw new IOException("Creating the directory " + target.toString() + " failed!");
			}
		}
		TexturePacker.process(src.getAbsolutePath(), target.getAbsolutePath(), themeName);
	}

	public static Map<String, File> moveFiles(final Map<String, File> files, final File outDirectory) throws IOException {
		if (!outDirectory.exists()) {
			if (!outDirectory.mkdir()) {
				throw new IOException("Creating the directory " + outDirectory.toString() + " failed!");
			}
		}
		if (!outDirectory.isDirectory())
			throw new IllegalArgumentException("A directory is required!");
		
		try {
			final Map<String, File> newMap = new HashMap<>();
			for (final Entry<String, File> entry : files.entrySet()) {
				final File file = files.get(entry.getKey());
				final Path newPath = Files.copy(file.toPath(), new File(outDirectory.getAbsolutePath() + File.separator + file.getName()).toPath(), 
						StandardCopyOption.REPLACE_EXISTING);
				newMap.put(entry.getKey(), newPath.toFile());
			}
			return newMap;
		}	
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void renameFilesToKey(final Map<String, File> map) throws IOException {
		for (final Entry<String, File> entry : map.entrySet()) {
			PackUtil.renameFile(map.get(entry.getKey()), entry.getKey());
		}
	}
	
	private static void renameFile(final File file, final String newName) throws IOException {
		if (!file.renameTo(new File(file.getAbsoluteFile() + File.separator + newName + getFileExtension(file)))) {
			System.out.println("The file " + file + " could not be renamed!");
		}
	}
	
	private static String getFileExtension(File file) {
	    String name = file.getName();
	    try {
	        return name.substring(name.lastIndexOf(".") + 1);
	    } 
	    catch (Exception e) {
	        return "";
	    }
	}
	
}
