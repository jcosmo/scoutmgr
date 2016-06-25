package scoutmgr.client.view.model;

import scoutmgr.client.entity.security.User;

public class UserViewModel
  extends AbstractViewModel
{
  private String _userName;
  private String _email;

  public UserViewModel( final User user )
  {
    super( user, user.getUserName() );
    _userName = user.getUserName();
    _email = user.getEmail();
  }

  public String getUserName()
  {
    return _userName;
  }

  public String getEmail()
  {
    return _email;
  }
}
