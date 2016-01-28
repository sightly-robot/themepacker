package de.unihannover.swp2015.robots2.themepacker.controller;

import java.io.File;

import org.apache.pivot.wtk.FileBrowserSheet;
import org.apache.pivot.wtk.FileBrowserSheet.Mode;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.TextInput;

import de.unihannover.swp2015.robots2.themepacker.util.bridge.ActionEvent;
import de.unihannover.swp2015.robots2.themepacker.util.bridge.BridgeActionRoute;
import de.unihannover.swp2015.robots2.themepacker.util.bridge.BridgeUpdateRoute;
import de.unihannover.swp2015.robots2.themepacker.util.bridge.Dock;
import de.unihannover.swp2015.robots2.themepacker.util.bridge.UpdateEvent;

/**
 * Handles actions on the view.
 * 
 * @author Rico Schrage
 */
public class ViewActionHandler extends Dock.ViewController {

	/** Path of the root text input */
	private String rootPath = null;
	
	/**
	 * Will be called when the root path has changed
	 * 
	 * @param newPath new root path
	 */
	@BridgeUpdateRoute(UpdateEvent.SRC_CHANGED)
	public void onSourceChanged(final String newPath) {
		this.rootPath = newPath;
	}
	
	/**
	 * Called when a text input (except root input) has been focused.
	 * 
	 * @param source text input which has been focused
	 */
	@BridgeActionRoute(ActionEvent.FOCUS_TEXT_INPUT)
	public void onFocusTextInput(final TextInput source) {
		final FileBrowserSheet fileBrowserSheet = new FileBrowserSheet();
		fileBrowserSheet.setMode(Mode.OPEN);
		if (rootPath != null && !rootPath.isEmpty()) {
			fileBrowserSheet.setRootDirectory(new File(rootPath));
		}
		fileBrowserSheet.open(source.getWindow(), new SheetCloseListener() {

			@Override
			public void sheetClosed(final Sheet sheet) {
				if (sheet.getResult()) {
					final String path = fileBrowserSheet.getSelectedFile().getAbsolutePath();
					source.setText(path);
					bridge.emitEvent(UpdateEvent.FILE_CHANGED, source.getName(), path);
				}
			}

		});
	}
	
	/**
	 * Called when the root text input has been focused.
	 * 
	 * @param source root text input
	 */
	@BridgeActionRoute(ActionEvent.FOCUS_SOURCE_INPUT)
	public void onFocusSourceInput(final TextInput source) {
		final FileBrowserSheet fileBrowserSheet = new FileBrowserSheet();
		fileBrowserSheet.setMode(Mode.SAVE_TO);
		fileBrowserSheet.open(source.getWindow(), new SheetCloseListener() {

			@Override
			public void sheetClosed(final Sheet sheet) {
				if (sheet.getResult()) {
					final String path = fileBrowserSheet.getSelectedFile().getAbsolutePath();
					source.setText(path);
					bridge.emitEvent(UpdateEvent.SRC_CHANGED, path);
				}
			}

		});
	}
	
}