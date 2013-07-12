package co.applebloom.apps.signage.components;

import java.util.logging.Logger;

import co.applebloom.apps.signage.Main;
import co.applebloom.apps.signage.rendering.HTMLElement;

import com.impetus.annovention.listener.ClassAnnotationDiscoveryListener;

public class ClassAnnotationDiscovery implements ClassAnnotationDiscoveryListener
{
	private static Logger log = Main.getLogger();
	
	@Override
	public void discovered( String clazz, String annotation )
	{
		log.info( "Discovered Class(" + clazz + ") with Annotation(" + annotation + ")" );
	}
	
	@Override
	public String[] supportedAnnotations()
	{
		return new String[] { HTMLElement.class.getName() };
	}
}
