package scoutmgr.client.security;

import scoutmgr.client.data_type.security.PermissionType;
import scoutmgr.client.entity.security.Permission;
import scoutmgr.client.entity.security.User;

public final class PermissionUtil
{
  private PermissionUtil()
  {

  }

  public static boolean isSiteAdmin( final User user )
  {
    return hasPermission(user, PermissionType.SITE_ADMIN);
  }

  public static boolean isUserAdmin( final User user )
  {
    return hasPermission( user, PermissionType.USER_ADMIN )
           || hasPermission( user, PermissionType.SITE_ADMIN );
  }

  private static boolean hasPermission( final User user, final PermissionType permissionType )
  {
    for ( final Permission permission : user.getPermissions() )
    {
      if (permission.getType().equals( permissionType ))
      {
        return true;
      }
    }
    return false;
  }
}
