package co.applebloom.apps.signage.tag;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import org.w3c.dom.Element;

import co.applebloom.apps.signage.Main;
import cookxml.core.DecodeEngine;
import cookxml.core.interfaces.Adder;

@XMLComponent( tagName = "item", customAdder = true )
public class MenuItemTag extends JLabel implements DSTag, Adder
{
	@Override
	public void onCreate( Object parentObj, Element elm )
	{
		String type = elm.getAttribute( "type" );
		int fontSize = 12;
		Color fontColor = Color.BLACK;
		String fontName = "Ubuntu";
		int fontAlign = JLabel.LEFT;
		
		if ( type.isEmpty() )
			type = "default";
		
		switch ( type )
		{
			case "title":
				fontSize = 46;
		}
		
		if ( !elm.getAttribute( "size" ).isEmpty() )
			fontSize = Integer.parseInt( elm.getAttribute( "size" ) );
		
		if ( !elm.getAttribute( "color" ).isEmpty() )
			fontColor = Main.parseColor( elm.getAttribute( "color" ) );
		
		if ( fontColor == null )
			fontColor = Color.BLACK;
		
		if ( !elm.getAttribute( "align" ).isEmpty() )
			fontAlign = Main.parseAlignment( elm.getAttribute( "align" ) );
		
		setText( "<html><p style=\"" + elm.getAttribute( "style" ) + "\">" + elm.getAttribute( "title" ) + "</p></html>" );
		setFont( new Font( fontName, Font.PLAIN, fontSize ) );
		setVerticalTextPosition( JLabel.CENTER );
		setHorizontalTextPosition( fontAlign );
		setForeground( fontColor );
	}
	
	@Override
	public void onEditFinished( Object parentObj, Element elm )
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean add( String parentNS, String parentTag, Object parent, Object foal, DecodeEngine decodeEngine ) throws Exception
	{
		Main.getLogger().info( "Was asked to add " + foal.getClass() + " to parent " + parent.getClass() );
		
		return true;
	}
	
}
