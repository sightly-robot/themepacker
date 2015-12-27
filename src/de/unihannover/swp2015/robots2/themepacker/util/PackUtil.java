package de.unihannover.swp2015.robots2.themepacker.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class PackUtil {
	
	public static void packTheme(final File src, final File target, final String themeName, Settings settings) {
		TexturePacker.process(src.getAbsolutePath(), target.getAbsolutePath(), themeName);
	}

	public static void moveFiles(final File[] files, final File outDirectory) {
		if (!outDirectory.exists())
			outDirectory.mkdir();
		if (!outDirectory.isDirectory())
			throw new IllegalArgumentException("A directory is required!");
		
		try {
			for (final File file : files) {
				Files.copy(file.toPath(), new File(outDirectory.getAbsolutePath() + File.separator + file.getName()).toPath(), 
						StandardCopyOption.REPLACE_EXISTING);
			}
		}	
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void renameFiles(final File[] files, String[] lookup) {
		for (int i = 0; i < files.length; ++i) {
			PackUtil.renameFile(files[i], lookup[i]);
		}
	}
	
	private static void renameFile(final File file, final String newName) {
		file.renameTo(new File(file.getAbsoluteFile() + File.separator + newName + getFileExtension(file)));
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
