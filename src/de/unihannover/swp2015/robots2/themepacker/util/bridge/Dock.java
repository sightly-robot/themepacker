package de.unihannover.swp2015.robots2.themepacker.util.bridge;

/**
 * Provides a strict implementation of {@link BridgeEndpoint}. 
 * 
 * @author Rico Schrage
 */
public abstract class Dock implements BridgeEndpoint {
	
	/**
	 * The connected bridge. Can be null.
	 */
	protected Bridge bridge;
	
	/**
	 * @return true if the dock is connected
	 */
	public boolean isConnected() {
		return bridge != null;
	}
	
	/**
	 * Connects the end-point to the give bridge.
	 * 
	 * @param bridge {@link Bridge}
	 */
	public void connectBridge(final Bridge bridge) {
		if (isConnected()) {
			throw new IllegalStateException("The dock is already connected!");
		}
		
		this.bridge = bridge;
	}
	
	/**
	 * Disconnects the end-point to the given bridge.
	 * 
	 * @param bridge {@link Bridge}
	 */
	public void disconnectBridge(Bridge bridge) {
		if (bridge != this.bridge) {
			throw new IllegalArgumentException("The given bridge is not conencted to this dock!");
		}
		
		this.bridge = null;
	}

	@Override
	public void onUpdate(UpdateEvent event, Object... attached) {
		// optional as alternative to the reflection system
	}

	@Override
	public void onAction(ActionEvent event, final Object source) {
		// optional as alternative to the reflection system
	}
	
	/**
	 * Dock made for controller. When connecting this class to a bridge, it will be registered as controller.
	 * 
	 * @author Rico Schrage
	 */
	public abstract static class Controller extends Dock implements BridgeEndpoint.Controller {
		
		@Override
		public void connectBridge(Bridge bridge) { 
			super.connectBridge(bridge);
			
			bridge.registerController(this);
		}
		
		@Override
		public void disconnectBridge(Bridge bridge) { 
			super.connectBridge(bridge);
			
			bridge.unregisterController(this);
		}
		
	}	
	
	/**
	 * Dock made for view-controller. When connecting this class to a bridge, it will be registered as view-controller.
	 * 
	 * @author Rico Schrage
	 */
	public abstract static class ViewController extends Dock implements BridgeEndpoint.ViewController {
		
		@Override
		public void connectBridge(Bridge bridge) { 
			super.connectBridge(bridge);
			
			bridge.registerViewController(this);
		}
		
		@Override
		public void disconnectBridge(Bridge bridge) { 
			super.connectBridge(bridge);
			
			bridge.unregisterViewController(this);
		}
		
	}	
	
	/**
	 * Dock made for view's. When connecting this class to a bridge, it will be registered as view.
	 * 
	 * @author Rico Schrage
	 */
	public abstract static class View extends Dock implements BridgeEndpoint.View {
		
		@Override
		public void connectBridge(Bridge bridge) { 
			super.connectBridge(bridge);
			
			bridge.registerView(this);
		}
		
		@Override
		public void disconnectBridge(Bridge bridge) { 
			super.connectBridge(bridge);
			
			bridge.unregisterView(this);
		}
		
	}

}
