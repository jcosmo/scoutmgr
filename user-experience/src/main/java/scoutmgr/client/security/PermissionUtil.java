package scoutmgr.client.security;

import scoutmgr.client.data_type.security.PermissionType;
import scoutmgr.client.entity.Person;
import scoutmgr.client.entity.security.Permission;
import scoutmgr.client.entity.security.User;

public final class PermissionUtil
{
  private PermissionUtil()
  {

  }

  public static boolean isSiteAdmin( final User user )
  {
    return hasPermission( user, PermissionType.SITE_ADMIN );
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
      if ( permission.getType().equals( permissionType ) )
      {
        return true;
      }
    }
    return false;
  }

  public static boolean hasAnyOf( final User user, final String[] roles )
  {
    for ( String role : roles )
    {
      if ( hasPermission( user, PermissionType.valueOf( role ) ) )
      {
        return true;
      }
    }
    return false;
  }

  public static boolean canCompleteBadgework( final User user, final Person person )
  {
    if ( null != user.getPerson() && user.getPerson().equals( person ))
    {
      return true;
    }

    // Is a section leader for this person?
    if ( user.getPermissions().stream().anyMatch(
      permission -> PermissionType.SECTION_LEADER.equals( permission.getType() ) &&
                    person.getScoutSection().equals( permission.getScoutSection() ) ) )
    {
      return true;
    }

    return false;
  }

  public static boolean canSignBadgework( final User user, final Person person )
  {
    // Is a section leader for this person?
    if ( user.getPermissions().stream().anyMatch(
      permission -> PermissionType.SECTION_LEADER.equals( permission.getType() ) &&
                    person.getScoutSection().equals( permission.getScoutSection() ) ) )
    {
      return true;
    }

    // Permissions for a group this person is in
    if ( user.getPermissions().stream().anyMatch(
      permission -> PermissionType.GROUP_LEADER.equals( permission.getType() ) &&
                    permission.getPersonGroup().getPersonGroupMemberships().stream().anyMatch(
                      m -> m.getPerson().equals( person ) ) ) )
    {
      return true;
    }
    return false;
  }
}
