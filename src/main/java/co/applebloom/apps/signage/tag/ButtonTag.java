package co.applebloom.apps.signage.tag;

import javax.swing.JButton;

import org.w3c.dom.Element;

@XMLComponent( tagName = "button" )
public class ButtonTag extends JButton implements DSTag
{
	public ButtonTag()
	{
		super( "Button : text unknown" );
	}
	
	@Override
	public String getName()
	{
		return "Button";
	}
	
	@Override
	public void onCreate( Object parentObj, Element elm )
	{
		setText( elm.getFirstChild().getNodeValue() );
	}
	
	@Override
	public void onEditFinished( Object parentObj, Element elm )
	{
		
	}
}
