package de.unihannover.swp2015.robots2.themepacker;

import java.awt.Dimension;

import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Frame;
import org.apache.pivot.wtk.Theme;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtk.skin.terra.TerraFrameSkin;

public class ThemePacker implements Application {
	
	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;
	
    private Window window = null;
	
	public static void main(String[] args) {
		DesktopApplicationContext.main(ThemePacker.class, args);
		Theme.getTheme().set(Frame.class, TerraFrameSkin.class);
	}

	@Override
	public void startup(Display display, Map<String, String> arg1) throws Exception {      
		display.getHostWindow().setSize(new Dimension(WIDTH, HEIGHT));
		
		BXMLSerializer bxmlSerializer = new BXMLSerializer();
        bxmlSerializer.getNamespace().put("themepacker", this);

        window = (Window) bxmlSerializer.readObject(getClass().getResource("/de/unihannover/swp2015/robots2/themepacker/bxml/ThemePackerView.bxml"));
        window.setPreferredSize(WIDTH, HEIGHT);
        window.open(display);
    }
	
	@Override
	public boolean shutdown(boolean arg0) throws Exception {
		if (window != null) {
			window.close();
        }
        return false;
	}
		
	@Override
	public void resume() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suspend() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
