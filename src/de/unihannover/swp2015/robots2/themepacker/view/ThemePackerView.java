package de.unihannover.swp2015.robots2.themepacker.view;

import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Mouse;
import org.apache.pivot.wtk.TextInput;

import de.unihannover.swp2015.robots2.themepacker.util.ComponentMouseButtonAdapter;
import de.unihannover.swp2015.robots2.themepacker.util.bridge.ActionEvent;
import de.unihannover.swp2015.robots2.themepacker.util.bridge.Dock;
import de.unihannover.swp2015.robots2.themepacker.window.MainFrame;

/**
 * Main view of the application.
 * 
 * @author Rico Schrage
 */
public class ThemePackerView extends Dock.View {

	/**
	 * Main frame of the application.
	 */
	private MainFrame mainFrame;	

	/**
	 * Constructs the main view of the application.
	 * 
	 * @param root root of the view
	 */
	public ThemePackerView(final MainFrame root) {
		this.mainFrame = root;
		
		this.initTextInput();
		this.initPackButton();
		this.initSourceInput();
	}

	/**
	 * Initializes the source input field.
	 */
	private void initSourceInput() {
		mainFrame.getSourceInput().getComponentMouseButtonListeners().add(new ComponentMouseButtonAdapter() {
			
			public boolean mouseClick(Component src, Mouse.Button button, int x, int y, int count) {
				bridge.emitActionEvent(ActionEvent.FOCUS_SOURCE_INPUT, mainFrame.getSourceInput());
				return false;
			}
		});
	}

	/**
	 * Initializes the pack button.
	 */
	private void initPackButton() {
		mainFrame.getStartButton().getButtonPressListeners().add(new ButtonPressListener() {

			public void buttonPressed(Button arg0) {
				bridge.emitActionEvent(ActionEvent.PACK_INVOKED, mainFrame.getStartButton());
			}			
		});
	}

	/**
	 * Initializes all text input. Attaches a file browser sheet.
	 * 
	 * @param namespace pivot name-space
	 */
	private void initTextInput() {
		for (int i = 0; i < mainFrame.getNumberOfFileInputs(); ++i) {
			final TextInput fileInput = mainFrame.getFileInput(i);
			fileInput.getComponentMouseButtonListeners().add(new ComponentMouseButtonAdapter() {

				public boolean mouseClick(Component src, Mouse.Button button, int x, int y, int count) {
					bridge.emitActionEvent(ActionEvent.FOCUS_TEXT_INPUT, fileInput);
					return false;
				}
			});
		}
	}

}
