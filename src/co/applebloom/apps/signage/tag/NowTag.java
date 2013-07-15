package co.applebloom.apps.signage.tag;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;

@XMLComponent( tagName = "now" )
public class NowTag extends JLabel implements DSTag
{
	public NowTag()
	{
		super();
	}
	
	@Override
	public String getName()
	{
		return "Now";
	}

	@Override
	public void onCreate( Object parentObj, org.w3c.dom.Element elm )
	{
		String format = elm.getAttribute( "format" );
		
		if ( format == null )
			format = "HH:mm:SS";
		
		SimpleDateFormat sdf = new SimpleDateFormat( format );
		Date currDate = new Date();
		setText( sdf.format( currDate ) );
	}

	@Override
	public void onEditFinished( Object parentObj, org.w3c.dom.Element elm )
	{
		
	}
}
