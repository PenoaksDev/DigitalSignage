package co.applebloom.apps.signage.server;

import co.applebloom.apps.signage.FrameThreaded;
import co.applebloom.apps.signage.Main;

public class ServiceBean implements IService
{
	@Override
	public String hello()
	{
		return "Hello, Rainbow Dash!!! :D";
	}

	@Override
	public String reloadScreen( int monitorNo )
	{
		FrameThreaded frame = Main.getFrame( monitorNo );
		
		if ( frame != null )
			return frame.getFrame().reload();
		
		return "An error has occured!";
	}
}
