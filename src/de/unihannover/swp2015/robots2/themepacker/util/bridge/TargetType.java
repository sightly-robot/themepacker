package de.unihannover.swp2015.robots2.themepacker.util.bridge;

/**
 * Defines all possible adressee's of the bridge.
 * 
 * @author Rico Schrage
 */
public enum TargetType {

	/**
	 * Controller, which handles the logic of the application without caring about the view.
	 */
	CONTROLLER,
	
	/**
	 * Handles the logic of the view, such as displaying file browser on click....
	 */
	VIEW_CONTROLLER,
	
	/**
	 * The view itself.
	 */
	VIEW;
	
}
