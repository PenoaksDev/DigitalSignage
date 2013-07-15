package co.applebloom.apps.signage.tag;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import org.w3c.dom.Element;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import co.applebloom.apps.signage.Main;

@XMLComponent( tagName = "video" )
public class VideoTag extends EmbeddedMediaPlayerComponent implements DSTag, ComponentListener
{
	@Override
	public void onCreate( Object parentObj, Element elm )
	{
		addComponentListener( this );
	}
	
	@Override
	public void onEditFinished( Object parentObj, Element elm )
	{
		
	}
	
	@Override
	public String getName()
	{
		return "Video";
	}
	
	@Override
	public void componentResized( ComponentEvent e )
	{
		Main.getLogger().info( "Video component has been resized!" );
		
		getMediaPlayer().playMedia( "file://" + VideoTag.class.getClassLoader().getResource( "resources" ).toExternalForm().substring( 5 ) + "/fridayafternext_http.mp4" );
	}

	@Override
	public void componentMoved( ComponentEvent e )
	{
		Main.getLogger().info( "Video component has been moved!" );
	}

	@Override
	public void componentShown( ComponentEvent e )
	{
		Main.getLogger().info( "Video component has been made visible!" );
	}

	@Override
	public void componentHidden( ComponentEvent e )
	{
		Main.getLogger().info( "Video component has been made hidden!" );
	}
}
