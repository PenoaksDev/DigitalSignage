package co.applebloom.apps.signage;

import co.applebloom.apps.signage.components.ScreenFrame;

public class FrameThreaded extends Thread
{
	private final ScreenFrame frame;
	private final int index;
	
	public FrameThreaded( final int index )
	{
		super();
		
		this.index = index;
		frame = new ScreenFrame();
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
		frame.initDisplay();
	}
}
