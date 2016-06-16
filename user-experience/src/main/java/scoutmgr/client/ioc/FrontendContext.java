package scoutmgr.client.ioc;

public interface FrontendContext
{
  void initialArrival();

  void login( final String username, final String password );

  void logout();

  boolean isLoggedIn();
}
