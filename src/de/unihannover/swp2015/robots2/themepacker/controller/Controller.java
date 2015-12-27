package de.unihannover.swp2015.robots2.themepacker.controller;

import java.io.File;

import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.TextInput;

import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

import de.unihannover.swp2015.robots2.themepacker.util.PackUtil;

public class Controller {

	private static final String[] NAME_LOOKUP = {"default_bubble", "default_field", "default_2_c", "default_field_four", "default_field_one", 
			"default_field_three", "default_field_two", "default_res_one", "default_res_two", "default_res_three", "default_res_four", 
			"default_res_five", "default_res_six", "default_res_seven", "default_res_eight", "default_res_nine", "default_res_ten"};
	
	private static final File DEFAULT_SRC = new File("temp");
	private static final File DEFAULT_OUT = new File("output");
	private static final Settings DEFAULT_SETTINGS = new Settings();

	static {
		DEFAULT_SETTINGS.maxWidth = 1024*4;
		DEFAULT_SETTINGS.maxHeight = 1024*4;
		DEFAULT_SETTINGS.pot = true;
		DEFAULT_SETTINGS.duplicatePadding = true;	
	}
	
	public void processInput(Map<String, Object> namespace, final int count) {
		if (DEFAULT_SRC.exists())
			DEFAULT_SRC.delete();
		
		File[] files = new File[count];
		for (int i = 0; i < count; ++i) {
			final TextInput ti = (TextInput) namespace.get("file" + i);
			if (ti.getText().isEmpty())
				throw new IllegalStateException("Incomplete Input!");
			files[i] = new File(ti.getText());
		}
		
		PackUtil.moveFiles(files, DEFAULT_SRC);
		PackUtil.renameFiles(DEFAULT_SRC.listFiles(), NAME_LOOKUP);
		PackUtil.packTheme(DEFAULT_SRC, DEFAULT_OUT, "test", DEFAULT_SETTINGS);
	}
	
}
