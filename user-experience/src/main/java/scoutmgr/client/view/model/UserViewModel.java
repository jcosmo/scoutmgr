package scoutmgr.client.view.model;

import scoutmgr.client.entity.security.User;

public class UserViewModel
  extends AbstractViewModel
{
  private String _userName;
  private String _email;
  private ScoutViewModel _person;

  public UserViewModel( final User user )
  {
    super( user, user.getUserName() );
    _userName = user.getUserName();
    _email = user.getEmail();
    if ( null != user.getPerson() )
    {
      _person = new ScoutViewModel( user.getPerson() );
    }
    else
    {
      _person = null;
    }
  }

  public String getUserName()
  {
    return _userName;
  }

  public String getEmail()
  {
    return _email;
  }

  public ScoutViewModel getPerson()
  {
    return _person;
  }
}
