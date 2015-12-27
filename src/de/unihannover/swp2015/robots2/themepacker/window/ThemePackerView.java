package de.unihannover.swp2015.robots2.themepacker.window;

import java.io.File;
import java.net.URL;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentMouseButtonListener;
import org.apache.pivot.wtk.FileBrowserSheet;
import org.apache.pivot.wtk.FileBrowserSheet.Mode;
import org.apache.pivot.wtk.Frame;
import org.apache.pivot.wtk.Mouse.Button;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.TextInput;

import de.unihannover.swp2015.robots2.themepacker.controller.Controller;
import de.unihannover.swp2015.robots2.themepacker.util.ComponentMouseButtonAdapter;

public class ThemePackerView extends Frame implements Bindable {

	/** id of the source input */
	private static final int SRC_ID = 23;

	@BXML
	private PushButton pushButton = null;

	private Controller controller;
	
	@Override
	public void initialize(Map<String, Object> namespace, URL arg1, Resources arg2) {
		this.controller = new Controller();
		this.initTextInput(namespace);
		this.initButton(namespace);
	}

	/**
	 * Initializes all buttons.
	 * 
	 * @param namespace pivot name-space
	 */
	private void initButton(Map<String, Object> namespace) {
		pushButton.getButtonPressListeners().add(new ButtonPressListener() {

			@Override
			public void buttonPressed(org.apache.pivot.wtk.Button arg0) {
				controller.processInput(namespace, SRC_ID);
			}
		});
	}

	/**
	 * Initializes all text input. Attaches a file browser sheet.
	 * 
	 * @param namespace pivot name-space
	 */
	private void initTextInput(final Map<String, Object> namespace) {
		// iterate over all text inputs
		for (int i = 0; i < namespace.getCount() - 2; ++i) {
			final TextInput ti = (TextInput) namespace.get("file" + i);
			// to make i accessible in the anonymous classes
			final int cookie = i;
			// clear listener-list
			for (ComponentMouseButtonListener c : ti.getComponentMouseButtonListeners()) {
				ti.getComponentMouseButtonListeners().remove(c);
			}
			ti.getComponentMouseButtonListeners().add(new ComponentMouseButtonAdapter() {

				@Override
				public boolean mouseClick(Component component, Button button, int x, int y, int count) {
					// create and open a file browser
					final FileBrowserSheet fileBrowserSheet = new FileBrowserSheet();
					fileBrowserSheet.setMode(cookie == SRC_ID ? Mode.SAVE_TO : Mode.OPEN);
					final String rootPath = ((TextInput) namespace.get("file" + SRC_ID)).getText();
					if (!rootPath.isEmpty()) {
						fileBrowserSheet.setRootDirectory(new File(rootPath));
					}
					fileBrowserSheet.open(ThemePackerView.this, new SheetCloseListener() {

						@Override
						public void sheetClosed(Sheet sheet) {
							// load result
							if (sheet.getResult()) {
								ti.setText(fileBrowserSheet.getSelectedFile().getAbsolutePath());

								// if src has updated the root directory have to
								// be set again
								if (cookie == SRC_ID) {
									initTextInput(namespace);
								}
							}
						}

					});
					return false;
				}

			});
		}
	}

}
