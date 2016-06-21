package scoutmgr.client.ioc;

public interface FrontendContext
{
  void initialArrival();

  void login( final String username, final String password, final Runnable onSuccess, final Runnable onFailure );

  void logout();

  boolean isLoggedIn();
}
