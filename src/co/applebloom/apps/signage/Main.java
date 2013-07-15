package co.applebloom.apps.signage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import co.applebloom.apps.signage.components.ScreenFrame;
import co.applebloom.apps.signage.components.TagLoader;
import co.applebloom.apps.signage.configuration.ConfigurationSection;
import co.applebloom.apps.signage.configuration.file.YamlConfiguration;
import co.applebloom.apps.signage.server.Server;

public class Main
{
	private static Logger log = Logger.getLogger( "DigitalSignage" );
	private static File fileConfig = new File( Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/signage.yml" );
	private static YamlConfiguration config;
	private static Server server;
	private static TagLoader tagLoader = new TagLoader();
	
	public static Logger getLogger()
	{
		return log;
	}
	
	public static TagLoader getTagLoader()
	{
		return tagLoader;
	}
	
	public static YamlConfiguration getAppConfig()
	{
		return config;
	}
	
	public static void main( String... args ) throws Exception
	{
		new Main();
	}
	
	public Main()
	{
		// Initalize configuration
		if ( fileConfig.exists() )
		{
			config = YamlConfiguration.loadConfiguration( fileConfig );
			config.addDefault( "general.mode", "dual" );
			config.addDefault( "general.ip", "0.0.0.0" );
			config.addDefault( "general.port", "8080" );
		}
		else
		{
			config = new YamlConfiguration();
			config.set( "general.mode", "dual" );
			config.set( "general.ip", "0.0.0.0" );
			config.set( "general.port", "8080" );
			
			try
			{
				config.save( fileConfig );
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
		
		// Why does this not work? or does it?
		Runtime.getRuntime().addShutdownHook( new Thread()
		{
			public void run()
			{
				try
				{
					Main.getLogger().log( Level.INFO, "Application if now closing." );
					Main.getAppConfig().save( fileConfig );
				}
				catch ( IOException e )
				{
					e.printStackTrace();
				}
			}
		} );
		
		// Initalize the server for use in server or dual modes.
		//server = new Server();
		//server.setPort( config.getInt( "general.port", 8080 ) );
		//server.setIp( config.getString( "general.ip", "0.0.0.0" ) );
		
		switch ( config.getString( "general.mode" ) )
		{
			case "dual":
				//server.start();
				startClient();
				break;
			case "server":
				//server.start();
				break;
			case "client":
				startClient();
				break;
		}
		
	}
	
	private void startClient()
	{
		// Find each section of the display config.
		ConfigurationSection cs = config.getConfigurationSection( "display" );
		
		// Loop though each section of the display config.
		for ( String key : cs.getKeys( false ) )
		{
			// Retrive the info. getInt is not returning the correct value.
			int id = Integer.parseInt( cs.getString( key + ".id", "0" ) );
			String source = cs.getString( key + ".source", "default/default" );
			
			// Make sure the source path is valid.
			if ( !source.isEmpty() )
			{
				// Make sure that we don't accidently put two screens on one display.
				while ( screens.containsKey( id ) )
					id++;
				
				// Check that we have a screen psyically available for this id.
				if ( id < ScreenFrame.getNumberOfMonitors() )
				{
					System.out.println( "Loading screen #" + id + " with source " + source + "." );
					
					// Initalize and track a reference to each screen loaded.
					screens.put( id, new FrameThreaded( id, source ) );
				}
			}
		}
	}
	
	// A HashMap to keep track of all the screens loaded. 
	private static HashMap<Integer, FrameThreaded> screens = new HashMap<Integer, FrameThreaded>();
}
