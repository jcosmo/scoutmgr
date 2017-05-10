package scoutmgr.server.service.security;

import scoutmgr.server.data_type.security.PermissionType;
import scoutmgr.server.entity.Person;
import scoutmgr.server.entity.security.User;

public final class PermissionUtil
{
  private PermissionUtil()
  {

  }

  public static boolean maySignOff( final User user, final Person person )
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
