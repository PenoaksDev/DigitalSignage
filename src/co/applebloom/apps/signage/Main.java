package co.applebloom.apps.signage;

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
	}
}
