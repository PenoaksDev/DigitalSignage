package co.applebloom.apps.signage.tag;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import org.w3c.dom.Element;

import co.applebloom.apps.signage.Main;

@XMLComponent( tagName = "menuSection" )
public class MenuSectionTag extends JPanel implements DSTag
{
	private GridBagConstraints contraints = new GridBagConstraints();
	
	public MenuSectionTag()
	{
		super(); // new GridBagLayout()
	}
	
	@Override
	public void onCreate( Object parentObj, Element elm )
	{
		setOpaque( false );
	}
	
	@Override
	public void onEditFinished( Object parentObj, Element elm )
	{
		
	}

	@Override
	public String getName()
	{
		return "Menu Section";
	}
}
