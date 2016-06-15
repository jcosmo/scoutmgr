package scoutmgr.client.ioc;

public interface FrontendContext
{
  void initialArrival();

  void login();

  void logout();

  boolean isLoggedIn();
}
