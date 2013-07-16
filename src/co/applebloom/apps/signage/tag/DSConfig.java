package co.applebloom.apps.signage.tag;

import org.w3c.dom.Element;

public interface DSConfig
{
	/*
	 * Handle a config tag and act upon the arguments.
	 */
	public void onConfig( Object parentObj, Element elm );
	
	/*
	 * Return the formatted name of this tag class.
	 */
	public String getName();
}
