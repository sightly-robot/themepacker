package de.unihannover.swp2015.robots2.themepacker.util.bridge;

import java.util.ArrayList;
import java.util.List;

/**
 * Events, which will be thrown by GUI elements (without an update of the model). They indicate, that some actions have to be done.
 * 
 * @author Rico Schrage
 */
public enum ActionEvent {
	
	/**
	 * Will be emitted, when the GUI requests to pack the files.
	 * <br>
	 * Invocation: onAction(PACK_INVOKED, PushButton).
	 */
	PACK_INVOKED(TargetType.CONTROLLER),
	
	/**
	 * Will be emitted, when a text input gets focused.
	 * <br>
	 * Invocation: onAction(FOCUS_TEXT_INPUT, TextInput).
	 */
	FOCUS_TEXT_INPUT(TargetType.VIEW_CONTROLLER),
	
	/**
	 * Will be emitted, when a text input gets focused.
	 * <br>
	 * Invocation: onAction(FOCUS_SOURCE_INPUT, TextInput).
	 */
	FOCUS_SOURCE_INPUT(TargetType.VIEW_CONTROLLER);
	
	/**
	 * Supported targets
	 */
	private final List<TargetType> targetTypeList;
	
	/**
	 * Constructs an ActionEvent.
	 * 
	 * @param types supported targets
	 */
	private ActionEvent(final TargetType... types) {
		this.targetTypeList = new ArrayList<>();
		
		for (final TargetType type : types) {
			targetTypeList.add(type);
		}
	}
	
	/**
	 * Checks if the given target <code>type</code> is supported by the event.
	 * 
	 * @param type {@link TargetType}
	 * @return true if the target is supported
	 */
	public boolean isTarget(final TargetType type) {
		return targetTypeList.contains(type);
	}
	
}
