package scoutmgr.client.view.model;

import java.util.ArrayList;
import java.util.List;
import scoutmgr.client.entity.security.Permission;
import scoutmgr.client.entity.security.User;

public class UserViewModel
  extends AbstractViewModel
{
  private String _userName;
  private String _email;
  private ScoutViewModel _person;
  private String _permissions;

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

    final List<Permission> permissions = user.getPermissions();
    final StringBuilder permissionStr = new StringBuilder(  );
    for ( final Permission permission : permissions )
    {
      if ( permissionStr.length() > 0 )
      {
        permissionStr.append( ", " );
      }
      permissionStr.append( permission.getType().toString() );
    }
    _permissions = permissionStr.toString();
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

  public String getPermissions()
  {
    return _permissions;
  }
}
