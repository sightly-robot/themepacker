package de.unihannover.swp2015.robots2.themepacker.util.bridge;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Bridge between view and controller. Forwards input actions and update
 * requests.
 * 
 * @author Rico Schrage
 */
public class Bridge {

	/**
	 * Contains all registered {@link Controller}.
	 */
	private final List<BridgeEndpoint.Controller> controllerList;

	/**
	 * Contains all registered {@link ViewActionHandler}.
	 */
	private final List<BridgeEndpoint.ViewController> viewControllerList;

	/**
	 * Contains all registered {@link View}'s.
	 */
	private final List<BridgeEndpoint.View> viewList;

	/**
	 * Constructs a bridge.
	 */
	public Bridge() {
		this.controllerList = new ArrayList<>();
		this.viewControllerList = new ArrayList<>();
		this.viewList = new ArrayList<>();
	}

	/**
	 * Register an {@link Controller}.
	 * 
	 * @param obs {@link Controller}, which should get registered
	 */
	public void registerController(final BridgeEndpoint.Controller obs) {
		controllerList.add(obs);
	}

	/**
	 * Unregister an {@link Controller}.
	 * 
	 * @param obs {@link Controller}, which should get unregistered
	 */
	public void unregisterController(final BridgeEndpoint.Controller obs) {
		controllerList.remove(obs);
	}

	/**
	 * Register an {@link ViewActionHandler}.
	 * 
	 * @param obs {@link ViewActionHandler}, which should get registered
	 */
	public void registerViewController(final BridgeEndpoint.ViewController obs) {
		viewControllerList.add(obs);
	}

	/**
	 * Unregister an {@link ViewActionHandler}.
	 * 
	 * @param obs {@link ViewActionHandler}, which should get unregistered
	 */
	public void unregisterViewController(final BridgeEndpoint.ViewController obs) {
		viewControllerList.remove(obs);
	}

	/**
	 * Register an {@link ViewActionHandler}.
	 * 
	 * @param obs {@link ViewActionHandler}, which should get registered
	 */
	public void registerView(final BridgeEndpoint.View obs) {
		viewList.add(obs);
	}

	/**
	 * Unregister an {@link ViewActionHandler}.
	 * 
	 * @param obs {@link ViewActionHandler}, which should get unregistered
	 */
	public void unregisterView(final BridgeEndpoint.View obs) {
		viewList.remove(obs);
	}

	/**
	 * Emits an event to all observer. The events can be either received via {@link BridgeEndpoint#onUpdate(UpdateEvent, Object...)} or
	 * with the help of annotations {@link BridgeUpdateRoute}.
	 * <br>
	 * Hint: Every event will only be delivered once!
	 * 
	 * @param event type of the event
	 * @param attached objects, which are related to the update.
	 * @see 
	 */
	public void emitEvent(final UpdateEvent event, final Object... attached) {
		if (event.isTarget(TargetType.CONTROLLER)) {
			for (int i = 0; i < controllerList.size(); ++i) {
				BridgeEndpoint.Controller controller = controllerList.get(i);
				if (!emitReflectionEvent(controller, event, attached)) {
					controller.onUpdate(event, attached);
				}
			}
		}

		if (event.isTarget(TargetType.VIEW_CONTROLLER)) {
			for (int i = 0; i < viewControllerList.size(); ++i) {
				BridgeEndpoint.ViewController viewController = viewControllerList.get(i);
				if (!emitReflectionEvent(viewController, event, attached)) {
					viewController.onUpdate(event, attached);
				}
			}
		}

		if (event.isTarget(TargetType.VIEW)) {
			for (int i = 0; i < viewList.size(); ++i) {
				BridgeEndpoint.View view = viewList.get(i);
				if (!emitReflectionEvent(view, event, attached)) {
					view.onUpdate(event, attached);
				}
			}
		}
	}
	
	/**
	 * Helper method, which emits the give event to the appropriate annotated method.
	 * 
	 * @param target recipient of the event
	 * @param event {@link UpdateEvent}
	 * @param attached attached objects
	 * @return true if an appropriate method has been found, false otherwise
	 */
	private boolean emitReflectionEvent(final BridgeEndpoint target, final UpdateEvent event, final Object... attached) {
		for (final Method method : target.getClass().getMethods()) {
			if (method.isAnnotationPresent(BridgeUpdateRoute.class) && method.getAnnotation(BridgeUpdateRoute.class).value() == event) {
				try {
					method.invoke(target, attached);
					return true;
				} 
				catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * Emits an action event to all connected listeners. The events can be either received via {@link BridgeEndpoint#onAction(ActionEvent)} or
	 * with the help of annotations {@link BridgeActionRoute}.
	 * <br>
	 * Hint: Every event will only be delivered once!
	 * 
	 * @param action type of the event
	 */
	public void emitActionEvent(final ActionEvent action, final Object source) {
		if (action.isTarget(TargetType.CONTROLLER)) {
			for (int i = 0; i < controllerList.size(); ++i) {
				BridgeEndpoint.Controller view = controllerList.get(i);
				if (!emitReflectionActionEvent(view, action, source)) {
					view.onAction(action, source);
				}
			}
		}

		if (action.isTarget(TargetType.VIEW_CONTROLLER)) {
			for (int i = 0; i < viewControllerList.size(); ++i) {
				BridgeEndpoint.ViewController viewController = viewControllerList.get(i);
				if (!emitReflectionActionEvent(viewController, action, source)) {
					viewController.onAction(action, source);
				}
			}
		}

		if (action.isTarget(TargetType.VIEW)) {
			for (int i = 0; i < viewList.size(); ++i) {
				BridgeEndpoint.View view = viewList.get(i);
				if (!emitReflectionActionEvent(view, action, source)) {
					view.onAction(action, source);
				}
			}
		}
	}
	
	/**
	 * Helper method, which emits the give event to the appropriate annotated method.
	 * 
	 * @param target recipient of the event
	 * @param event {@link ActionEvent}
	 * @param attached attached objects
	 * @return true if an appropriate method has been found, false otherwise
	 */
	private boolean emitReflectionActionEvent(final BridgeEndpoint target, final ActionEvent event, final Object source) {
		for (final Method method : target.getClass().getMethods()) {
			if (method.isAnnotationPresent(BridgeActionRoute.class) && method.getAnnotation(BridgeActionRoute.class).value() == event) {
				try {
					method.invoke(target, source);
					return true;
				}
				catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

}
