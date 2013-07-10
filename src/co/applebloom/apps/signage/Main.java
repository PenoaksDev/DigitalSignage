package co.applebloom.apps.signage;

import co.applebloom.apps.signage.components.ScreenFrame;
import co.applebloom.apps.signage.server.ServerThreaded;

public class Main
{
	public static void main( String... args ) throws Exception
	{
		new Main();
	}
	
	public Main()
	{
		new Thread( new ServerThreaded() ).start();
		
		ScreenFrame frame = new ScreenFrame();
		//ScreenFrame frame2 = new ScreenFrame();
		
		if ( ScreenFrame.getNumberOfMonitors() > 1 )
		{
			frame.setScreen(1);
		}
		
		frame.setVisible( true );
		//frame2.setVisible( true );
		
		frame.initDisplay();
	}
}
