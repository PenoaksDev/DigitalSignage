package co.applebloom.apps.signage.rendering;

import java.awt.Component;
import java.lang.reflect.Field;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.text.BadLocationException;
import javax.swing.text.ComponentView;
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

public class ChiHTMLEditorKit extends HTMLEditorKit
{
	
	public ChiHTMLEditorKit()
	{
		super();
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
			
			if ( kind instanceof HTML.UnknownTag && element.getName().equals( "button" ) )
			{
				
				return new ComponentView( element )
				{
					protected Component createComponent()
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
				};
				
			}
			return super.create( element );
		}
	}
}

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
			DTD dtd = (DTD) AppContext.getAppContext().get( o );;
			javax.swing.text.html.parser.Element div = dtd.getElement( "div" );
			dtd.defineElement( "button", div.getType(), true, true, div.getContent(), null, null, div.getAttributes() );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
}
