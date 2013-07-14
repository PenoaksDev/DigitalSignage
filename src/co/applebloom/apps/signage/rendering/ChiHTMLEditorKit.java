package co.applebloom.apps.signage.rendering;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.text.html.parser.DTD;
import javax.swing.text.html.parser.ParserDelegator;

import sun.awt.AppContext;
import co.applebloom.apps.signage.Main;
import co.applebloom.apps.signage.tag.HTMLElement;

import com.impetus.annovention.ClasspathDiscoverer;
import com.impetus.annovention.Discoverer;
import com.impetus.annovention.listener.ClassAnnotationDiscoveryListener;

@SuppressWarnings( "serial" )
public class ChiHTMLEditorKit extends HTMLEditorKit implements ClassAnnotationDiscoveryListener
{
	Set<String> registeredTags = new HashSet<String>();
	
	private static Logger log = Main.getLogger();
	
	@Override
	public void discovered( String clazz, String annotation )
	{
		log.info( "Discovered Class(" + clazz + ") with Annotation(" + annotation + ")" );
		
		if ( annotation.equals( HTMLElement.class.getName() ) )
		{
			registeredTags.add( clazz );
		}
	}
	
	@Override
	public String[] supportedAnnotations()
	{
		return new String[] { HTMLElement.class.getName() };
	}
	
	public ChiHTMLEditorKit()
	{
		super();
		
		Discoverer discoverer = new ClasspathDiscoverer();
		discoverer.addAnnotationListener( this );
		discoverer.discover();
	}
	
	public Document createDefaultDocument()
	{
		StyleSheet styles = getStyleSheet();
		StyleSheet ss = new StyleSheet();
		
		ss.addStyleSheet( styles );
		
		ChiHTMLDocument doc = new ChiHTMLDocument( ss );
		doc.setParser( getParser() );
		doc.setAsynchronousLoadPriority( 4 );
		doc.setTokenThreshold( 100 );
		return doc;
	}
	
	public ViewFactory getViewFactory()
	{
		return new ChiHTMLFactory();
	}
	
	Parser defaultParser;
	
	protected Parser getParser()
	{
		if ( defaultParser == null )
		{
			defaultParser = new ChiParserDelegator();
		}
		return defaultParser;
	}
	
	class ChiHTMLFactory extends HTMLFactory implements ViewFactory
	{
		public ChiHTMLFactory()
		{
			super();
		}
		
		public View create( Element element )
		{
			HTML.Tag kind = (HTML.Tag) ( element.getAttributes().getAttribute( javax.swing.text.StyleConstants.NameAttribute ) );
			
			if ( kind instanceof HTML.UnknownTag )
			{
				for ( String clazz : registeredTags )
				{
					try
					{
						HTMLElement anno = Class.forName( clazz ).getAnnotation( HTMLElement.class );
						
						if ( element.getName().equalsIgnoreCase( anno.tagName() ) )
							return (View) newView( clazz, element );
					}
					catch ( ClassNotFoundException e )
					{
						e.printStackTrace();
					}
				}
			}
			return super.create( element );
		}
		
		public View newView( String clazz, Element element )
		{
			try
			{
				Class myClass = Class.forName( clazz );
				
				Object[] parameters = { element };
				return (View) myClass.getConstructors()[0].newInstance( parameters );
				
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				return null;
			}
		}
	}
}

@SuppressWarnings( "serial" )
class ChiHTMLDocument extends HTMLDocument
{
	public ChiHTMLDocument(StyleSheet styles)
	{
		super( styles );
	}
	
	public HTMLEditorKit.ParserCallback getReader( int pos )
	{
		Object desc = getProperty( Document.StreamDescriptionProperty );
		if ( desc instanceof URL )
		{
			setBase( (URL) desc );
		}
		return new ChiHTMLReader( pos );
	}
	
	class ChiHTMLReader extends HTMLDocument.HTMLReader
	{
		public ChiHTMLReader(int offset)
		{
			super( offset );
		}
		
		public void handleStartTag( HTML.Tag t, MutableAttributeSet a, int pos )
		{
			if ( t.toString().equals( "button" ) )
			{
				registerTag( t, new BlockAction() );
			}
			super.handleStartTag( t, a, pos );
		}
	}
}

@SuppressWarnings( "serial" )
class ChiParserDelegator extends ParserDelegator
{
	public ChiParserDelegator()
	{
		try
		{
			Field f = javax.swing.text.html.parser.ParserDelegator.class.getDeclaredField( "DTD_KEY" );
			f.setAccessible( true );
			Object o = f.get( null );
			DTD dtd = (DTD) AppContext.getAppContext().get( o );
			;
			javax.swing.text.html.parser.Element div = dtd.getElement( "div" );
			dtd.defineElement( "button", div.getType(), true, true, div.getContent(), null, null, div.getAttributes() );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
}
