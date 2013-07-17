package co.applebloom.apps.signage.tag;

import java.awt.Canvas;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Collections;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import co.applebloom.apps.signage.Main;
import co.applebloom.apps.signage.video.VideoItem;
import co.applebloom.apps.signage.video.VideoItemComparator;

@XMLComponent( tagName = "video" )
public class VideoTag extends Canvas implements DSTag, ComponentListener, MediaPlayerEventListener
{
	private static final long serialVersionUID = 6382725029419302010L;
	private ArrayList<VideoItem> playlist = new ArrayList<VideoItem>();
	private EmbeddedMediaPlayer mediaPlayer;
	
	public VideoTag()
	{
		addComponentListener( this );
		
		MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
		CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface( this );
		mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
		mediaPlayer.addMediaPlayerEventListener( this );
		
		// REMOVEME: TEMP SO NOT TO ANNOY ME!
		mediaPlayer.setVolume( 0 );
		
		mediaPlayer.setVideoSurface( videoSurface );
	}
	
	@Override
	public void onCreate( Object parentObj, Element elm )
	{
		int width = 1050;
		int height = 600;
		
		if ( !elm.getAttribute( "width" ).isEmpty() )
			width = Integer.parseInt( elm.getAttribute( "width" ) );
		
		if ( !elm.getAttribute( "height" ).isEmpty() )
			height = Integer.parseInt( elm.getAttribute( "height" ) );
		
		setSize( width, height );
		
		for ( int i = 0; i < elm.getChildNodes().getLength(); i++ )
		{
			if ( elm.getChildNodes().item( i ).getNodeName().equals( "playlist" ) )
			{
				NodeList nl = elm.getChildNodes().item( i ).getChildNodes();
				
				for ( int ii = 0; ii < nl.getLength(); ii++ )
				{
					if ( nl.item( ii ).getNodeName().equals( "item" ) )
					{
						Main.getLogger().info( "Add playlist item: " + ( (Element) nl.item( ii ) ).getAttribute( "src" ) );
						playlist.add( new VideoItem( ( (Element) nl.item( ii ) ).getAttribute( "src" ), ( (Element) nl.item( ii ) ).getAttribute( "method" ) ) );
					}
				}
			}
		}
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
	
	public VideoItem sortAndNext()
	{
		Collections.sort( playlist, new VideoItemComparator() );
		
		VideoItem vi = playlist.get( 0 );
		
		if ( vi != null )
			vi.addOne();
		
		return vi;
	}
	
	public void playFromPlaylist()
	{
		try
		{
			VideoItem vi = sortAndNext();
			
			if ( vi != null )
			{
				// mediaPlayer.stop();
				mediaPlayer.playMedia( "file://" + vi.getFile() );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void componentResized( ComponentEvent e )
	{
		Main.getLogger().info( "Video component has been resized!" );
		
		playFromPlaylist();
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
	
	@Override
	public void backward( MediaPlayer arg0 )
	{
		Main.getLogger().info( "Video has moved backwards!" );
	}
	
	@Override
	public void buffering( MediaPlayer arg0, float arg1 )
	{
		Main.getLogger().info( "Video is buffering!" );
	}
	
	@Override
	public void endOfSubItems( MediaPlayer arg0 )
	{
		Main.getLogger().info( "Video has reached end of sub items!" );
	}
	
	@Override
	public void error( MediaPlayer arg0 )
	{
		Main.getLogger().info( "Video has errored out!" );
		
		playFromPlaylist();
	}
	
	@Override
	public void finished( MediaPlayer arg0 )
	{
		Main.getLogger().info( "Video has now finished!" );
		
		playFromPlaylist();
	}
	
	@Override
	public void forward( MediaPlayer arg0 )
	{
		Main.getLogger().info( "Video has moved forwards!" );
	}
	
	@Override
	public void lengthChanged( MediaPlayer arg0, long arg1 )
	{
		Main.getLogger().info( "Video has changed length!" );
	}
	
	@Override
	public void mediaChanged( MediaPlayer arg0, libvlc_media_t arg1, String arg2 )
	{
		Main.getLogger().info( "Video has changed media!" );
	}
	
	@Override
	public void mediaDurationChanged( MediaPlayer arg0, long arg1 )
	{
		Main.getLogger().info( "Video has changed duration!" );
	}
	
	@Override
	public void mediaFreed( MediaPlayer arg0 )
	{
		Main.getLogger().info( "Video has released!" );
	}
	
	@Override
	public void mediaMetaChanged( MediaPlayer arg0, int arg1 )
	{
		Main.getLogger().info( "Video has changed metadata!" );
	}
	
	@Override
	public void mediaParsedChanged( MediaPlayer arg0, int arg1 )
	{
		Main.getLogger().info( "Video has changed parse!" );
	}
	
	@Override
	public void mediaStateChanged( MediaPlayer arg0, int arg1 )
	{
		Main.getLogger().info( "Video has changed state!" );
	}
	
	@Override
	public void mediaSubItemAdded( MediaPlayer arg0, libvlc_media_t arg1 )
	{
		Main.getLogger().info( "Video has add sub item!" );
	}
	
	@Override
	public void newMedia( MediaPlayer arg0 )
	{
		Main.getLogger().info( "Video has new media!" );
	}
	
	@Override
	public void opening( MediaPlayer arg0 )
	{
		Main.getLogger().info( "Video is openning!" );
	}
	
	@Override
	public void pausableChanged( MediaPlayer arg0, int arg1 )
	{
		Main.getLogger().info( "Video has changed pausable!" );
	}
	
	@Override
	public void paused( MediaPlayer arg0 )
	{
		Main.getLogger().info( "Video has paused!" );
		mediaPlayer.play();
	}
	
	@Override
	public void playing( MediaPlayer arg0 )
	{
		Main.getLogger().info( "Video is now Playing!" );
	}
	
	@Override
	public void positionChanged( MediaPlayer arg0, float arg1 )
	{
		//Main.getLogger().info( "Video has changed position!" );
	}
	
	@Override
	public void seekableChanged( MediaPlayer arg0, int arg1 )
	{
		Main.getLogger().info( "Video has changed seekable!" );
	}
	
	@Override
	public void snapshotTaken( MediaPlayer arg0, String arg1 )
	{
		Main.getLogger().info( "Video has taken snapshot!" );
	}
	
	@Override
	public void subItemFinished( MediaPlayer arg0, int arg1 )
	{
		Main.getLogger().info( "Video has finished sub item!" );
	}
	
	@Override
	public void subItemPlayed( MediaPlayer arg0, int arg1 )
	{
		Main.getLogger().info( "Video has played sub item!" );
	}
	
	@Override
	public void timeChanged( MediaPlayer arg0, long arg1 )
	{
		//Main.getLogger().info( "Video has changed time!" );
	}
	
	@Override
	public void titleChanged( MediaPlayer arg0, int arg1 )
	{
		Main.getLogger().info( "Video has changed title!" );
	}
	
	@Override
	public void videoOutput( MediaPlayer arg0, int arg1 )
	{
		Main.getLogger().info( "Video has video output!" );
	}
	
	@Override
	public void stopped( MediaPlayer arg0 )
	{
		Main.getLogger().info( "Media player has stopped!" );
		
		playFromPlaylist();
	}
}
