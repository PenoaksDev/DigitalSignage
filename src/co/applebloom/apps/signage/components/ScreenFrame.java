package co.applebloom.apps.signage.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import co.applebloom.apps.signage.rendering.ChiHTMLEditorKit;

public class ScreenFrame extends JFrame implements Cloneable
{
	private static final long serialVersionUID = -8415026498095675707L;
	
	private JEditorPane edit = new JEditorPane();
	private JLabel loadingComponent = null;
	
	public ScreenFrame()
	{
		super( "Digital Signage App" );
		
		setLocationRelativeTo( null );
		setUndecorated( true );
		setScreen( 0 );
		
		setBackground( Color.WHITE );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		loadingScreen( true );
	}
	
	public void initDisplay()
	{
		setLoadingText( "Searching for the Display Package" );
		
		getContentPane().add( new JScrollPane( edit ), BorderLayout.CENTER );
		
		String htmlText = "<html>\n" + "<body>\n" + "<p>\n" + "Text before button\n" + "<button>Button Text</button>\n" + "Text after button\n" + "</p>\n" + "</body>\n" + "</html>";
		
		edit.setEditable( false );
		edit.setEditorKit( new ChiHTMLEditorKit() );
		edit.setText( htmlText );
		
		
		setLoadingText( "DONE" );
		
		loadingScreen( false );
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
		if ( str.isEmpty() )
		{
			loadingComponent.setText( "Initalizing Digital Signage..." );
		}
		else
		{
			loadingComponent.setText( "Initalizing Digital Signage... " + str + "..." );
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
	
	public static int getNumberOfScreens()
	{
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		return g.getScreenDevices().length;
	}
	
	// TODO: Finish this
	@Override
	public ScreenFrame clone() throws CloneNotSupportedException
	{
		ScreenFrame sf = (ScreenFrame) super.clone();
		
		return sf;
	}
}
