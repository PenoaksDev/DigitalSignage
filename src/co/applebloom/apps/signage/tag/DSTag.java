package co.applebloom.apps.signage.tag;

import java.awt.Component;
import java.util.HashMap;
import javax.swing.text.Element;

public abstract class DSTag{
	
	public DSTag(String tag){
		name = tag.toLowerCase();
		tagMap.put(name, this);
	}
	
	public abstract Component createComponent(Element element);
	
	public String getName(){
		return name;
	}
	
	public boolean equals(Object obj){
		if(obj instanceof DSTag){
			return name.equals(((DSTag)obj).name);
		}else{
			return false;
		}
	}
	
	public int hashCode(){
		return name.hashCode();
	}
	
	protected final String name;
	
	public static HashMap<String, DSTag> tagMap = new HashMap<String, DSTag>();
}