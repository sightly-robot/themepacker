package de.unihannover.swp2015.robots2.themepacker.util.bridge;

import java.util.ArrayList;
import java.util.List;

/**
 * Events, which can be emitted to a bridge. They indicate that something has changed.
 * <br>
 * It supports attaching associated objects. 
 * 
 * @author Rico Schrage
 */
public enum UpdateEvent {

	/**
	 * Will be emitted, when the source folder has changed. The attached object is the new/changed source folder as {@link java.lang.String}.
	 * <br>
	 * Invocation: onUpdate(SRC_CHANGED, String).
	 */
	SRC_CHANGED(TargetType.CONTROLLER, TargetType.VIEW_CONTROLLER),
	
	/**
	 * Will be emitted, when one of the file paths has changed. The attached object is the new/changed file path as {@link java.lang.String}.
	 * <br>
	 * Invocation: onUpdate(FILE_CHANGED, String).
	 */
	FILE_CHANGED(TargetType.CONTROLLER);
	
	/**
	 * List of the targets which are supported.
	 */
	private final List<TargetType> targetTypeList;
	
	/**
	 * @param types supported targets
	 */
	private UpdateEvent(final TargetType... types) {
		this.targetTypeList = new ArrayList<>();
		
		for (final TargetType type : types) {
			targetTypeList.add(type);
		}
	}
	
	/**
	 * @param type {@link TargetType}
	 * @return if the target is supported
	 */
	public boolean isTarget(final TargetType type) {
		return targetTypeList.contains(type);
	}
	
}
