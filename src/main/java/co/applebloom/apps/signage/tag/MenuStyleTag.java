package co.applebloom.apps.signage.tag;

import org.w3c.dom.Element;

import co.applebloom.apps.signage.Main;

@XMLConfig(tagName = "menuStyle")
public class MenuStyleTag implements DSConfig
{
	public String itemCSS = "";
	public String menuCSS = "";
	
	@Override
	public void onConfig( Object parentObj, Element elm )
	{
		itemCSS = elm.getAttribute( "itemCSS" );
		menuCSS = elm.getAttribute( "menuCSS" );
		
		Main.getLogger().info( "CSS Style: " + itemCSS + " / " + menuCSS );
		
		if ( !elm.getAttribute( "bgColor" ).isEmpty() )
		{
			
		}
	}

	@Override
	public String getName()
	{
		return "Head";
	}
}
