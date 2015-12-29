package de.unihannover.swp2015.robots2.themepacker.util.bridge;

public abstract class Dock implements BridgeEndpoint {
	
	protected Bridge bridge;
	
	public boolean isConnected() {
		return bridge != null;
	}
	
	@Override
	public void connectBridge(Bridge bridge) {
		if (isConnected()) {
			throw new IllegalStateException("The dock is already connected!");
		}
		
		this.bridge = bridge;
	}

	@Override
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
	
	public static abstract class Controller extends Dock implements BridgeEndpoint.Controller {
		
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
	
	public static abstract class ViewController extends Dock implements BridgeEndpoint.ViewController {
		
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
	
	public static abstract class View extends Dock implements BridgeEndpoint.View {
		
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
