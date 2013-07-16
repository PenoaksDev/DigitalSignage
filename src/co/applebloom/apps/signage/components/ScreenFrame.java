package co.applebloom.apps.signage.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import co.applebloom.apps.signage.Main;
import co.applebloom.apps.signage.ResourceLoader;
import cookxml.cookswing.CookSwing;

public class ScreenFrame extends JFrame
{
	private static final long serialVersionUID = -8415026498095675707L;
	private JLabel loadingComponent = null;
	private ResourceLoader pack;
	
	public ScreenFrame()
	{
		super( "Digital Signage App" );
		
		// TODO Good idea to enable.
		// setAlwaysOnTop( true );
		setLocationRelativeTo( null );
		setUndecorated( true );
		setScreen( 0 );
		
		setBackground( Color.WHITE );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		loadingScreen( true );
		setLoadingText( "Creating Display" );
	}
	
	public void initDisplay( String packSource )
	{
		try
		{
			setLoadingText( "Loading the Resource Pack" );
			
			pack = ResourceLoader.buildLoader( ScreenFrame.class.getProtectionDomain().getCodeSource().getLocation().getPath() + System.getProperty( "file.separator", "/" ) + "packages" + System.getProperty( "file.separator", "/" ) + packSource );
			
			if ( pack == null )
			{
				showCritical( "Could not load the resource pack" );
				return;
			}
			
			setLoadingText( "Rendering Source" );
			CookSwing cookSwing = new CookSwing();
			Container c = cookSwing.render( pack.getInputStream( "source.xml" ) );
			
			setLoadingText( "Finishing Up" );
			c.setVisible( true );
			getContentPane().add( c );
			
			setLoadingText( "Done" );
			loadingScreen( false );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			showCritical( e.getMessage() );
		}
	}
	
	public String reload()
	{
		Main.getLogger().info( "We tried to reload the monitor!" );
		
		this.removeAll();
		loadingComponent = null;
		
		loadingScreen( true );
		//initDisplay( packSource );
		
		return "We tried to reload the monitor!";
	}
	
	private void showCritical( String text )
	{
		loadingScreen( false );
		
		ImageIcon ii = new ImageIcon( ScreenFrame.class.getClassLoader().getResource( "resources" ).toExternalForm().substring( 5 ) + "/symbol-error.png" );
		JLabel criticalComponent = new JLabel( ii );
		criticalComponent.setFont( new Font( "Ubuntu", Font.BOLD, 36 ) );
		criticalComponent.setOpaque( true );
		criticalComponent.setBackground( Color.RED );
		criticalComponent.setForeground( Color.WHITE );
		criticalComponent.setText( "CRITICAL!!! " + text );
		getContentPane().add( criticalComponent, BorderLayout.CENTER );
	}
	
	private void createLoadingScreen()
	{
		ImageIcon ii = new ImageIcon( ScreenFrame.class.getClassLoader().getResource( "resources" ).toExternalForm().substring( 5 ) + "/wheelofdeath.gif" );
		loadingComponent = new JLabel( ii );
		loadingComponent.setFont( new Font( "Ubuntu", Font.BOLD, 36 ) );
		loadingComponent.setOpaque( true );
		loadingComponent.setBackground( Color.BLACK );
		loadingComponent.setForeground( Color.RED );
		setLoadingText( "" );
		getContentPane().add( loadingComponent, BorderLayout.CENTER );
	}
	
	public void loadingScreen( boolean visible )
	{
		if ( loadingComponent == null )
			createLoadingScreen();
		
		loadingComponent.setVisible( visible );
	}
	
	public void setLoadingText( String str )
	{
		if ( str == null || loadingComponent == null )
			return;
		
		if ( str.isEmpty() )
		{
			loadingComponent.setText( "Initalizing Digital Signage..." );
		}
		else
		{
			loadingComponent.setText( "Initalizing Digital Signage... " + str + "..." );
		}
	}
	
	public void setFullscreen()
	{
		setFullscreen( 0, true );
	}
	
	public void setFullscreen( int index )
	{
		setFullscreen( index, true );
	}
	
	public void setFullscreen( boolean yes )
	{
		setFullscreen( 0, yes );
	}
	
	public void setFullscreen( int index, boolean yes )
	{
		try
		{
			GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice[] devices = g.getScreenDevices();
			devices[index].setFullScreenWindow( yes ? this : null );
		}
		catch ( ArrayIndexOutOfBoundsException e )
		{
			System.err.println( "We tried to create a display on monitor #" + index + " which returned an ArrayIndexOutOfBounds, FAILED!" );
		}
	}
	
	public void setScreen( int index )
	{
		try
		{
			Rectangle r = getScreenBounds( index );
			
			setSize( r.width, r.height );
			setLocation( r.x, r.y );
		}
		catch ( ArrayIndexOutOfBoundsException e )
		{
			System.err.println( "We tried to create a display on monitor #" + index + " which returned an ArrayIndexOutOfBounds, FAILED!" );
		}
	}
	
	public static Rectangle getScreenBounds( int index ) throws ArrayIndexOutOfBoundsException
	{
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = g.getScreenDevices();
		
		return devices[index].getDefaultConfiguration().getBounds();
	}
	
	public static int getNumberOfMonitors()
	{
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		return g.getScreenDevices().length;
	}
}
