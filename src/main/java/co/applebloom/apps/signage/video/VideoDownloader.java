package co.applebloom.apps.signage.video;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import co.applebloom.apps.signage.Main;

public class VideoDownloader
{
	private static LinkedHashMap<String, File> pending = new LinkedHashMap<String, File>();
	private static Thread concurrentAction;
	
	public void threadCheck()
	{
		if ( concurrentAction == null )
		{
			concurrentAction = new Downloader();
			concurrentAction.setName( "Video Downloader Thread" );
			concurrentAction.start();
		}
	}
	
	public static void shutdownThread()
	{
		concurrentAction.interrupt();
		concurrentAction = null;
	}
	
	public static Entry<String, File> getNext()
	{
		return pending.size() > 0 ? pending.entrySet().iterator().next() : null;
	}
	
	public static void addToPending( String src, File localFile )
	{
		pending.put( src, localFile );
	}
	
	private class Downloader extends Thread
	{
		@Override
		public void run()
		{
			while ( !Thread.currentThread().isInterrupted() )
			{
				try
				{
					if ( pending.size() > 0 )
					{
						Entry<String, File> next = getNext();
						
						if ( next != null )
						{
							Main.getLogger().info( "Trying to download \"" + next.getKey() + "\" and save it to \"" + next.getValue().getAbsolutePath() + "\"" );
							next.getValue().delete();
							next.getValue().getParentFile().mkdirs();
							next.getValue().createNewFile();
							URL website = new URL( next.getKey() );
							ReadableByteChannel rbc = Channels.newChannel( website.openStream() );
							FileOutputStream fos = new FileOutputStream( next.getValue().getAbsolutePath() );
							fos.getChannel().transferFrom( rbc, 0, Long.MAX_VALUE );
							fos.close();
							
							Main.getLogger().info( "Done downloading \"" + next.getValue().getAbsolutePath() + "\"" );
							
							VideoItem.setReady( next.getValue() );
							
							pending.remove( next.getKey() );
						}
						Thread.sleep( 1000 );
					}
				}
				catch ( Exception e )
				{
					e.printStackTrace();
				}
			}
		}
	}
}
