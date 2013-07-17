package co.applebloom.apps.signage;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.w3c.dom.Element;

public class ScheduledSlide extends JPanel implements ComponentListener
{
	private int interval = 0;
	private String src = "";
	private boolean valid = true;
	private JLabel label;
	
	public ScheduledSlide(Element e)
	{
		super( new GridLayout() );
		
		addComponentListener( this );
		
		// TODO: Create more custom panel arguments
		//setOpaque( false );
		setBackground( Color.CYAN );
		
		interval = Integer.parseInt( e.getAttribute( "interval" ) );
		
		src = e.getAttribute( "src" );
		
		if ( src.startsWith( "@frame" ) )
		{
			String xml = Main.getResourceLoader().getText( src.replace( "@", "" ) );
			
			if ( xml == null )
				valid = false;
		}
		else if ( src.startsWith( "@image" ) )
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
		if ( interval == 0 )
			return 5000;
		
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
	{}

	@Override
	public void componentShown( ComponentEvent e )
	{}

	@Override
	public void componentHidden( ComponentEvent e )
	{}
}
