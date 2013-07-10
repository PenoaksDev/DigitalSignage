package co.applebloom.apps.signage.server;

import com.caucho.resin.BeanEmbed;
import com.caucho.resin.HttpEmbed;
import com.caucho.resin.ResinEmbed;
import com.caucho.resin.WebAppEmbed;

public class Server
{
	public Server()
	{
		ServiceBean service = new ServiceBean();
		
		ResinEmbed resin = new ResinEmbed();
		
		resin.addBean(new BeanEmbed(service, "signage"));
		
		HttpEmbed http = new HttpEmbed( 8080 );
		resin.addPort( http );
		
		WebAppEmbed webApp = new WebAppEmbed( "/", Server.class.getClassLoader().getResource( "webroot" ).toExternalForm().substring( 5 ) );
		resin.addWebApp( webApp );
		
		resin.start();
		resin.join();
	}
}
