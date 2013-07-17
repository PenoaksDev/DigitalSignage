package co.applebloom.apps.signage.tag;

import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.w3c.dom.Element;

import co.applebloom.apps.signage.Main;
import co.applebloom.apps.signage.ScheduledSlide;

@XMLComponent( tagName = "scheduler" )
public class SchedulerTag extends JPanel implements DSTag
{
	private static Thread concurrentAction;
	private ArrayList<ScheduledSlide> slides = new ArrayList<ScheduledSlide>();
	private int currentIndex = 0;
	private int defaultInterval = 5000;
	
	public SchedulerTag()
	{
		super();
		setOpaque( false );
		
		try
		{
			// TODO: Needs some preventive options
			((FlowLayout) getLayout()).setVgap( 0 );
			((FlowLayout) getLayout()).setHgap( 0 );
		}
		catch ( Exception e )
		{}
	}
	
	@Override
	public void onCreate( Object parentObj, Element elm )
	{
		try
		{
			// TODO: Needs some preventive options
			((FlowLayout) ((Container) parentObj).getLayout()).setVgap( 0 );
			((FlowLayout) ((Container) parentObj).getLayout()).setHgap( 0 );
			
			defaultInterval = Integer.parseInt( elm.getAttribute( "interval" ) );
		}
		catch ( Exception e )
		{}
		
		for ( int i = 0; i < elm.getChildNodes().getLength(); i++ )
		{
			if ( elm.getChildNodes().item( i ) instanceof Element )
			{
				Element e = (Element) elm.getChildNodes().item( i );
				
				if ( e.getTagName().toLowerCase() != "slide" )
				{
					Main.getLogger().warning( "SCHEDULER tags can only contain SLIDE tags. Please correct this." );
					elm.removeChild( e );
				}
				else
				{
					ScheduledSlide ss = new ScheduledSlide( e );
					
					if ( ss.isValid() )
						slides.add( ss );
				}
			}
		}
	}
	
	private ScheduledSlide getNext()
	{
		if ( slides.size() < 1 )
			return null;
		
		if ( slides.size() == 1 )
			return slides.get( 0 );
		
		currentIndex++;
		
		if ( currentIndex >= slides.size() )
			currentIndex = 0;
		
		return slides.get( currentIndex );
	}
	
	@Override
	public void onEditFinished( Object parentObj, Element elm )
	{
		try
		{
			// Double make sure there are no children under this JPanel.
			removeAll();
			
			for ( ScheduledSlide s : slides )
			{
				s.setVisible( false );
				add( s );
			}
			
			concurrentAction = new SlideMover( this );
			concurrentAction.setName( "Slide Scheduler Thread" );
			concurrentAction.start();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void hideAllSlides()
	{
		for ( ScheduledSlide s : slides )
			s.setVisible( false );
	}
	
	private class SlideMover extends Thread
	{
		private SchedulerTag parent;
		private ScheduledSlide previousSlide;
		
		public SlideMover(SchedulerTag par)
		{
			parent = par;
		}
		
		@Override
		public void run()
		{
			while ( !Thread.currentThread().isInterrupted() )
			{
				try
				{
					ScheduledSlide s = parent.getNext();
					
					if ( previousSlide != null )
						previousSlide.display( false );
					
					s.display();
					
					//Main.getLogger().info( "Showing " + (parent.currentIndex + 1) + " of " + parent.slides.size() + " - " + s );
					
					previousSlide = s;
					
					int interval = s.getInterval();
					
					if ( interval == 0 )
						interval = parent.defaultInterval;
					
					Thread.sleep( interval );
				}
				catch ( Exception e )
				{
					e.printStackTrace();
				}
			}
		}
	}
}
