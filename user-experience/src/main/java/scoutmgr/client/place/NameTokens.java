package scoutmgr.client.place;

public class NameTokens
{
  public static final String CRASH = "/error";
  public static final String SCOUT_LIST = "/scouts";
  public static final String SCOUT = "/scout/{id}";
  public static final String NEW_SCOUT = "/scout";
  public static final String EVENTS = "/events";

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
}
