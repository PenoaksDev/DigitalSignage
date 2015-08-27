package co.applebloom.apps.signage.tag;

import org.w3c.dom.Element;

public interface DSTag
{
	public void onCreate( Object parentObj, Element elm );
	public void onEditFinished( Object parentObj, Element elm );
	public String getName();
}
