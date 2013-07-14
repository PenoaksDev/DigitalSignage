package co.applebloom.apps.signage.components;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import co.applebloom.apps.signage.Main;

@SuppressWarnings( "serial" )
public class MediaFrame extends JComponent implements ComponentListener
{
	private EmbeddedMediaPlayerComponent player;
	
	public MediaFrame()
	{
		super();
		this.setSize( 1050, 600 );
		this.addComponentListener( this );
	}
	
	public void playMedia( String filename )
	{
		player.getMediaPlayer().playMedia( "file://" + MediaFrame.class.getClassLoader().getResource( "resources" ).toExternalForm().substring( 5 ) + "/" + filename );
	}

	@Override
	public void componentResized( ComponentEvent e )
	{
		Main.getLogger().info( "Video component has been resized!" );
		
		if ( player == null )
		{
			player = new EmbeddedMediaPlayerComponent();
			this.add( player );
			player.setVisible( true );
			player.setSize( 1050, 600 );
			player.setVisible( true );
			
			playMedia( "fridayafternext_http.mp4" );
		}
	}

	@Override
	public void componentMoved( ComponentEvent e )
	{
		Main.getLogger().info( "Video component has been moved!" );
	}

	@Override
	public void componentShown( ComponentEvent e )
	{
		Main.getLogger().info( "Video componetn has been made visible!" );
	}

	@Override
	public void componentHidden( ComponentEvent e )
	{
		Main.getLogger().info( "Video component has been made hidden!" );
	}
}
