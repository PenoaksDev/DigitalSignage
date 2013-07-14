package co.applebloom.apps.signage.tag;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.text.BadLocationException;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;

@HTMLElement( tagName = "button" )
public class ButtonTag extends ComponentView implements DSTag
{
	public ButtonTag(Element element)
	{
		super( element );
	}
	
	public Component createComponent()
	{
		JButton button = new JButton( "Button : text unknown" );
		
		try
		{
			int start = getElement().getStartOffset();
			int end = getElement().getEndOffset();
			String text = getElement().getDocument().getText( start, end - start );
			button.setText( text );
		}
		catch ( BadLocationException e )
		{
			e.printStackTrace();
		}
		
		return button;
	}
	
	@Override
	public String getName()
	{
		return "Button";
	}
}
