package scoutmgr.client.place;

public class NameTokens
{
  public static final String CRASH = "/error";
  public static final String EVENTS = "/events";

  public static final String SCOUT_LIST = "/admin/scouts";
  public static final String SCOUT = "/admin/scout/{id}";
  public static final String NEW_SCOUT = "/admin/scout";

  public static final String ADMIN_BADGES = "/admin/badges";
  public static final String ADMIN_BADGES_LEVEL = "/admin/badges/{level}";
  public static final String ADMIN_BADGE = "/admin/badge/{id}";
  public static final String ADMIN_NEW_BADGE = "/admin/badge";

  public static String getEvents()
  {
    return EVENTS;
  }

  public static String getScoutList()
  {
    return SCOUT_LIST;
  }

  public static String getScout()
  {
    return SCOUT;
  }

  public static String getNewScout()
  {
    return NEW_SCOUT;
  }

  public static String getAdminBadges()
  {
    return ADMIN_BADGES;
  }

  public static String getAdminBadgesLevel()
  {
    return ADMIN_BADGES_LEVEL;
  }
}
