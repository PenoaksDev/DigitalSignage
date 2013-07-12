package co.applebloom.apps.signage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import co.applebloom.apps.signage.components.ScreenFrame;
import co.applebloom.apps.signage.configuration.file.YamlConfiguration;
import co.applebloom.apps.signage.server.Server;

public class Main
{
	private static Logger log = Logger.getLogger( "DigitalSignage" );
	private static File fileConfig = new File( Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/signage.yml" );
	private static YamlConfiguration config;
	private static Server server;
	
	public ArrayList<ScreenFrame> frames = new ArrayList<ScreenFrame>();
	
	public static Logger getLogger()
	{
		return log;
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
		
		// Why does this not work?
		Runtime.getRuntime().addShutdownHook( new Thread(){
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
		});
		
		server = new Server();
		server.setPort( config.getInt( "general.port", 8080 ) );
		server.setIp( config.getString( "general.ip", "0.0.0.0" ) );
		
		switch ( config.getString( "general.mode" ) )
		{
			case "dual":
				server.start();
				startClient();
				break;
			case "server":
				server.start();
				break;
			case "client":
				startClient();
				break;
		}
		

	}
	
	private void startClient()
	{
		System.out.println( "display: " + config.getList( "display" ) );
		
		for ( int n = 0; n < ScreenFrame.getNumberOfMonitors(); n++ )
			new FrameThreaded( n, "default/screenA" );
	}
}
