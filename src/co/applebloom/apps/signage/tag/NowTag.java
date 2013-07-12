package co.applebloom.apps.signage.tag;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.text.Element;

import co.applebloom.apps.signage.rendering.HTMLElement;

@HTMLElement(tagName="now")
public class NowTag extends DSTag{
	
	public NowTag(){
		super("now");
	}
	
	public Component createComponent(Element element){
		JLabel text = new JLabel("now");
		//TODO: populate with current date/time
		
		return text;
	}
}