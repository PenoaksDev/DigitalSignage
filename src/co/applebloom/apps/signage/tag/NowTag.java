package co.applebloom.apps.signage.tag;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;

@HTMLElement( tagName = "now" )
public class NowTag extends ComponentView implements DSTag
{
	public NowTag(Element element)
	{
		super( element );
	}
	
	@Override
	public Component createComponent()
	{
		String format = (String) getElement().getAttributes().getAttribute( "format" );
		
		if ( format == null )
			format = "HH:mm:SS";
		
		JLabel label = new JLabel( format );
		
		SimpleDateFormat sdf = new SimpleDateFormat( format );
		Date currDate = new Date();
		label.setText( sdf.format( currDate ) );
		
		return label;
	}
	
	@Override
	public String getName()
	{
		return "Now";
	}
}
