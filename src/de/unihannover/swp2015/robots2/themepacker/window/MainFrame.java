package de.unihannover.swp2015.robots2.themepacker.window;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Frame;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.TextInput;

/**
 * The main frame of the theme packer.
 * 
 * @author Rico Schrage
 */
public class MainFrame extends Frame implements Bindable {

	/**
	 * List of the input fields, which contain the paths to the textures.
	 */
	private final List<TextInput> fileInputs;
	
	@BXML 
	private TextInput srcInput;
	
	@BXML 
	private PushButton startButton;
	
	/**
	 * Constructs the main frame.
	 */
	public MainFrame() {
		super();
		
		this.fileInputs = new ArrayList<>();
	}
	
	/**
	 * Getter for <code>srcInput</code>.
	 * 
	 * @return TextInput object, which contains the source path as string.
	 */
	public TextInput getSourceInput() {
		return srcInput;
	}

	/**
	 * Getter for the file input fields.
	 * 
	 * @param index index of the file input field
	 * @return file input with the index <code>index</code>
	 */
	public TextInput getFileInput(final int index) {
		return fileInputs.get(index);
	}
	
	/**
	 * @return number of text inputs inputs
	 */
	public int getNumberOfFileInputs() {
		return fileInputs.size();
	}
	
	/**
	 * Getter for <code>startButton</code>
	 * 
	 * @return PushButton, which starts the packing process
	 */
	public PushButton getStartButton() {
		return startButton;
	}
	
	@Override
	public void initialize(Map<String, Object> namespace, URL url, Resources res) {
		for (int i = 0; i < namespace.getCount(); ++i) {
			final String id = "file" + i;
			if (namespace.containsKey(id)) {
				fileInputs.add((TextInput) namespace.get(id));
			}
		}
	}
	
}
