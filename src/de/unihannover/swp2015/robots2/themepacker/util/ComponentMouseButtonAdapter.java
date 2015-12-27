package de.unihannover.swp2015.robots2.themepacker.util;

import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentMouseButtonListener;
import org.apache.pivot.wtk.Mouse.Button;

public class ComponentMouseButtonAdapter implements ComponentMouseButtonListener {

	@Override
	public boolean mouseClick(Component arg0, Button arg1, int arg2, int arg3, int arg4) {
		// do nothing
		return false;
	}

	@Override
	public boolean mouseDown(Component arg0, Button arg1, int arg2, int arg3) {
		// do nothing
		return false;
	}

	@Override
	public boolean mouseUp(Component arg0, Button arg1, int arg2, int arg3) {
		// do nothing
		return false;
	}

}
