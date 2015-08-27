package co.applebloom.apps.signage;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.InputStream;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.w3c.dom.Element;

import cookxml.cookswing.CookSwing;

public class ScheduledSlide extends JPanel implements ComponentListener
{
	private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
	private static final long serialVersionUID = 580658968836553706L;
	private int interval = 0;
	private String src = "";
	private boolean valid = true;
	private JLabel label;
	
	public ScheduledSlide(Element elm)
	{
		super( new GridLayout() );
		
		addComponentListener( this );
		
		// TODO: Create more custom panel arguments
		setOpaque( false );
		
		try
		{
			interval = Integer.parseInt( elm.getAttribute( "interval" ) );
		}
		catch ( Exception e )
		{}
		
		src = elm.getAttribute( "src" );
		
		Pattern pattern = Pattern.compile(IMAGE_PATTERN);
		
		if ( src.toLowerCase().endsWith( ".xml" ) ) // XML file = Frames
		{
			InputStream xml;
			try
			{
				xml = Main.getResourceLoader().getInputStream( src.replace( "@", "" ) );
				
				if ( xml != null )
				{
					CookSwing cookSwing = new CookSwing();
					add( cookSwing.render( xml ) );
				}
				else
				{
					valid = false;
				}
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				valid = false;
			}
		}
		else if ( pattern.matcher( src ).matches() ) // Images
		{
			Image img = Main.getResourceLoader().getImage( src.replace( "@", "" ) );
			
			if ( img != null )
			{
				label = new JLabel( new ImageIcon( img ) );
				
				// Set this to parent size
				label.setSize( getSize() );
				
				add( label );
			}
			else
			{
				Main.getLogger().warning( "We could not load the resource \"" + src + "\" from the resource pack." );
				valid = false;
			}
		}
		else
		{	
			
		}
	}
	
	public boolean isValid()
	{
		return valid;
	}
	
	public int getInterval()
	{
		return interval;
	}
	
	public void display()
	{
		display( true );
	}
	
	public void display( boolean v )
	{
		setVisible( v );
	}
	
	@Override
	public void componentResized( ComponentEvent e )
	{
		if ( label != null )
			label.setSize( getSize() );
	}
	
	@Override
	public void componentMoved( ComponentEvent e )
	{
	}
	
	@Override
	public void componentShown( ComponentEvent e )
	{
	}
	
	@Override
	public void componentHidden( ComponentEvent e )
	{
	}
}
