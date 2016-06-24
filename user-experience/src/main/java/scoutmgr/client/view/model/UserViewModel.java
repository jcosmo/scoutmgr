package scoutmgr.client.view.model;

import scoutmgr.client.entity.security.User;

public class UserViewModel
  extends AbstractViewModel
{
  private String _userName;

  public UserViewModel( final User user )
  {
    super( user, user.getUserName() );
    _userName = user.getUserName();
  }

  public String getUserName()
  {
    return _userName;
  }
}
