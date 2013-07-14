package co.applebloom.apps.signage.tag;

import java.awt.Component;

import javax.swing.text.ComponentView;
import javax.swing.text.Element;

import co.applebloom.apps.signage.components.MediaFrame;

@HTMLElement(tagName="video")
public class VideoTag extends ComponentView implements DSTag
{
	private boolean initalized = false;
	private MediaFrame frame;
	
	public VideoTag(Element elem)
	{
		super( elem );
	}
	
	@Override
	public Component createComponent()
	{
		if ( initalized )
			return null;
		
		String format = (String) getElement().getAttributes().getAttribute( "format" );
		
		
		
		//frame.playMedia( "fridayafternext_http.mp4" );
		
		initalized = true;
		
		frame = new MediaFrame();
		return frame;
	}
	
	@Override
	public String getName()
	{
		return "Video";
	}
}
