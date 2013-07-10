package co.applebloom.apps.signage;

import java.util.ArrayList;

import co.applebloom.apps.signage.components.ScreenFrame;
import co.applebloom.apps.signage.server.ServerThreaded;

public class Main
{
	public ArrayList<ScreenFrame> frames = new ArrayList<ScreenFrame>();
	
	public static void main( String... args ) throws Exception
	{
		new Main();
	}
	
	public Main()
	{
		new Thread( new ServerThreaded() ).start();
		
		for ( int n = 0; n < ScreenFrame.getNumberOfMonitors(); n++ )			
			new FrameThreaded( n );
	}
}
