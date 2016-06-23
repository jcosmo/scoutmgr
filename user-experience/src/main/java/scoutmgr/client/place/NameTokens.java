package scoutmgr.client.place;

public class NameTokens
{
  public static final String LOGIN = "/login";
  public static final String CRASH = "/error";
  public static final String EVENTS = "/events";

  public static final String SCOUT = "/scout/{id}";

  public static final String ADMIN = "/admin";

  public static final String ADMIN_SCOUTS = "/admin/scouts";
  public static final String ADMIN_SCOUT = "/admin/scout/{id}";
  public static final String ADMIN_NEW_SCOUT = "/admin/scout";

  public static final String ADMIN_BADGES = "/admin/badges";
  public static final String ADMIN_BADGES_LEVEL = "/admin/badges/{level}";
  public static final String ADMIN_BADGE = "/admin/badge/{id}";
  public static final String ADMIN_NEW_BADGE = "/admin/badge";

  public static String getEvents()
  {
    return EVENTS;
  }

  public static String getAdmin()
  {
    return ADMIN;
  }

  public static String getAdminScouts()
  {
    return ADMIN_SCOUTS;
  }

  public static String getAdminScout()
  {
    return ADMIN_SCOUT;
  }

  public static String getAdminNewScout()
  {
    return ADMIN_NEW_SCOUT;
  }

  public static String getAdminBadges()
  {
    return ADMIN_BADGES;
  }

  public static String getAdminBadgesLevel()
  {
    return ADMIN_BADGES_LEVEL;
  }

  public static String getScout()
  {
    return SCOUT;
  }
}
