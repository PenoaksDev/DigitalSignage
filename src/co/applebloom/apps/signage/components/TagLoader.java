package co.applebloom.apps.signage.components;

import java.util.HashMap;
import java.util.logging.Logger;

import javax.swing.SwingConstants;

import org.w3c.dom.Element;

import co.applebloom.apps.signage.Main;
import co.applebloom.apps.signage.tag.DSTag;
import co.applebloom.apps.signage.tag.XMLComponent;

import com.impetus.annovention.ClasspathDiscoverer;
import com.impetus.annovention.Discoverer;
import com.impetus.annovention.listener.ClassAnnotationDiscoveryListener;

import cookxml.cookswing.CookSwing;
import cookxml.core.DecodeEngine;
import cookxml.core.interfaces.Creator;
import cookxml.core.setter.ConstantSetter;

public class TagLoader implements Creator, ClassAnnotationDiscoveryListener
{
	HashMap<String, String> registeredTags = new HashMap<String, String>();
	private static Logger log = Main.getLogger();
	
	public TagLoader()
	{
		Discoverer discoverer = new ClasspathDiscoverer();
		discoverer.addAnnotationListener( this );
		discoverer.discover();
	}
	
	@Override
	public void discovered( String clazz, String annotation )
	{
		log.info( "Discovered Class(" + clazz + ") with Annotation(" + annotation + ")" );
		
		if ( annotation.equals( XMLComponent.class.getName() ) )
		{
			try
			{
				XMLComponent anno = Class.forName( clazz ).getAnnotation( XMLComponent.class );
				
				ConstantSetter swingConstantsSetter = new ConstantSetter( SwingConstants.class );
				CookSwing.getSwingTagLibrary().setSetter( anno.tagName(), "orientation", swingConstantsSetter );
				
				CookSwing.getSwingTagLibrary().setCreator( anno.tagName(), this );
				registeredTags.put( anno.tagName(), clazz );
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String[] supportedAnnotations()
	{
		return new String[] { XMLComponent.class.getName() };
	}
	
	@Override
	public Object create( String parentNS, String parentTag, Element elm, Object parentObj, DecodeEngine decodeEngine ) throws Exception
	{
		if ( registeredTags.containsKey( elm.getTagName() ) )
		{
			log.info( "Created Class(" + Class.forName( registeredTags.get( elm.getTagName() ) ).getName() + ") with Tag(" + elm.getTagName() + ")" );
			
			DSTag tag = (DSTag) Class.forName( registeredTags.get( elm.getTagName() ) ).newInstance();
			tag.onCreate( parentObj, elm );
			
			/*
			XMLComponent anno = tag.getClass().getAnnotation( XMLComponent.class );
			if ( anno.customAdder() )
			{
				return null;
			}
			*/
			
			return tag;
		}
		
		return null;
	}
	
	@Override
	public Object editFinished( String parentNS, String parentTag, Element elm, Object parentObj, Object obj, DecodeEngine decodeEngine ) throws Exception
	{
		if ( obj instanceof DSTag )
		{
			log.info( "Finished Class(" + Class.forName( registeredTags.get( elm.getTagName() ) ).getName() + ") with Tag(" + elm.getTagName() + ")" );
			
			( (DSTag) obj ).onEditFinished( parentObj, elm );
			
			return obj;
		}
		
		return null;
	}
}
