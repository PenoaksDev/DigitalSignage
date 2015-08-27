package co.applebloom.apps.signage.video;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import co.applebloom.apps.signage.components.ScreenFrame;

public class VideoItem
{
	private boolean isFileReady = false;
	private File localFile;
	private int playCount = 0;
	
	public void addOne()
	{
		playCount++;
	}
	
	public int getPlayCount()
	{
		return playCount;
	}
	
	public VideoItem(String src, String method)
	{
		src = src.replace( "MED", "HIGH" );
		
		try
		{
			if ( method.equals( "download" ) )
			{
				localFile = new File( ScreenFrame.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/packages/cache/" + new URL( src ).getFile() );
				
				if ( localFile.exists() )
				{
					isFileReady = true;
				}
				else
				{
					// Make sure the video downloader thread is running.
					new VideoDownloader().threadCheck();
					// Add this video to the downloader.
					VideoDownloader.addToPending( src, localFile );
				}
			}
			else if ( method.equals( "stream" ) )
			{
				localFile = new File( src );
				isFileReady = true;
			}
			else
			// Local file
			{
				localFile = new File( src );
				
				if ( localFile.exists() )
					isFileReady = true;
			}
			
			synchronized ( items )
			{
				items.add( this );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public File getFile()
	{
		return localFile;
	}
	
	public boolean isReady()
	{
		return isFileReady;
	}
	
	// Static methods
	
	public static void setReady( File file )
	{
		for ( VideoItem vi : items )
		{
			if ( vi.getFile().equals( file ) )
			{
				vi.isFileReady = true;
			}
		}
	}
	
	private static ArrayList<VideoItem> items = new ArrayList<VideoItem>();
}
