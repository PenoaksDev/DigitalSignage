package co.applebloom.apps.signage;

import co.applebloom.apps.signage.components.ScreenFrame;

public class FrameThreaded extends Thread
{
	private final ScreenFrame frame;
	private final int index;
	private final String screenSource;
	
	public FrameThreaded( final int index, String screenSource )
	{
		super();
		
		this.index = index;
		frame = new ScreenFrame();
		this.screenSource = screenSource;
		setName( "Running Thread for Frame on Monitor #" + index );
		start();
	}
	
	public ScreenFrame getFrame()
	{
		return frame;
	}
	
	@Override
	public void run()
	{
		frame.setVisible( true );
		frame.setFullscreen( index );
		frame.initDisplay( screenSource );
	}
}
