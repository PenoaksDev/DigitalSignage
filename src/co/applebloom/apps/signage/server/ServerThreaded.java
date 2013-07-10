package co.applebloom.apps.signage.server;

public class ServerThreaded implements Runnable
{
	@Override
	public void run()
	{
		new Server();
	}
}
