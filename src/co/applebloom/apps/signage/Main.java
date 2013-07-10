package co.applebloom.apps.signage;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import co.applebloom.apps.signage.components.ScreenFrame;

public class Main
{
	public static void main( String... args ) throws Exception
	{
		new Main();
	}
	
	public Main()
	{
		ScreenFrame frame = new ScreenFrame();
		//ScreenFrame frame2 = new ScreenFrame();
		
		if ( ScreenFrame.getNumberOfScreens() > 1 )
		{
			frame.setScreen(1);
		}
		
		frame.setVisible( true );
		//frame2.setVisible( true );
		
		frame.initDisplay();
		
		//Fullscreen code
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = g.getScreenDevices();
		
		devices[0 /*index*/ ].setFullScreenWindow(frame);
	}
}
