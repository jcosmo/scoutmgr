package scoutmgr.client.ioc;

import scoutmgr.client.entity.Person;
import scoutmgr.client.entity.security.User;

public interface FrontendContext
{
  void initialArrival();

  void login( final String username, final String password, final Runnable onSuccess, final Runnable onFailure );

  void logout();

  boolean isLoggedIn();

  Integer getLoggedInUserID();

  User getUser();

  Person getPerson();
}
