package de.unihannover.swp2015.robots2.themepacker.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contains IO utilities related to {@link File}
 * 
 * @author Rico Schrage
 */
public class FileUtil {

	/** Logger (log4j) */
	private static final Logger LOGGER = LogManager.getLogger();

	private FileUtil() {
		//utility class
	}

	/**
	 * Moves all files in the map to the given output directory.
	 * 
	 * @param files mapped files
	 * @param outDirectory output directory
	 * @return map, which contains the old keys, which are mapped to the moved files
	 * @throws IOException thrown when the output folder could not be created
	 */
	public static Map<String, File> moveFiles(final Map<String, File> files, final File outDirectory) throws IOException {
		if (!outDirectory.exists() && !outDirectory.mkdir()) {
			throw new IOException("Creating the directory " + outDirectory.toString() + " failed!");
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
			LOGGER.error(e);
		}
		return null;
	}
	
	/**
	 * Renames all files to the give keys.
	 * 
	 * @param map key->files map
	 * @throws IOException thrown when renaming fails
	 */
	public static void renameFilesToKey(final Map<String, File> map) throws IOException {
		for (final Entry<String, File> entry : map.entrySet()) {
			renameFile(map.get(entry.getKey()), entry.getKey());
		}
	}
	
	/**
	 * Rename a file to a new give name.
	 * 
	 * @param file file
	 * @param newName new name
	 * @throws IOException thrown if renaming fails
	 */
	private static void renameFile(final File file, final String newName) throws IOException {
		String newFile = file.getParent() + File.separator + newName + "." + getFileExtension(file);
		if (!file.renameTo(new File(newFile))) {
			LOGGER.error("The file {} could not be renamed to {} ({})!", file, newFile, file);
		}
	}
	
	/**
	 * Deletes a directory plus content.
	 * 
	 * @param folder directory you want to delete
	 * @return true if succeeded, false otherwise
	 */
	public static boolean deleteDirectory(final File folder) {
		if (!folder.isDirectory()) {
			throw new IllegalArgumentException("You have to pass a folder!");
		}
		
		deleteDirectoryContentRecursive(folder);
		return folder.delete();
	}
	
	/**
	 * Deletes the content of a folder recursively but does not delete the folder itself.
	 * 
	 * @param folder folder you want to delete the content of
	 */
	private static void deleteDirectoryContentRecursive(final File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					file.delete();
				}
				else if (file.isDirectory()) {
					deleteDirectoryContentRecursive(file);
				}
			}
		}
	}
	
	/**
	 * Helper to get the file extension using the name of the file.
	 * 
	 * @param file file you want the extension from
	 * @return file extension of the give file
	 */
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
