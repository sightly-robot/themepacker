package de.unihannover.swp2015.robots2.themepacker.util.bridge;

/**
 * Defines a bridge end-point for classes, which should get connected via a bridge.
 * 
 * @author Rico Schrage
 */
public interface BridgeEndpoint {

	/**
	 * Will be invoked when an update-event has been emitted. 
	 * <br>
	 * Will only be invoked when the event type fits.
	 * 
	 * @param event update event
	 * @param attached attached object
	 */
	public void onUpdate(UpdateEvent event, Object... attached);
	
	/**
	 * Will be invoked when an action-event has been emitted. 
	 * <br>
	 * Will only be invoked when the event type fits.
	 * 
	 * @param event action event
	 * @param source object, which caused the event
	 */
	public void onAction(ActionEvent event, final Object source);

	/**
	 * Marker interface to mark a controller bridge end-point.
	 * 
	 * @author Rico Schrage
	 */
	public static interface Controller extends BridgeEndpoint {
		// empty marker interface
	}
	
	/**
	 * Marker interface to mark a view-controller bridge end-point.
	 * 
	 * @author Rico Schrage
	 */
	public static interface ViewController extends BridgeEndpoint {
		// empty marker interface
	}
	
	/**
	 * Marker interface to mark a view bridge end-point.
	 * 
	 * @author Rico Schrage
	 */
	public static interface View extends BridgeEndpoint {
		// empty marker interface
	}

}