package scoutmgr.client.place;

public class NameTokens
{
  public static final String MEMBERS = "/members";
  public static final String MEMBER_EDIT = "/members/edit/{memberID}";
  public static final String EVENTS = "/events";

  public static String getEvents()
  {
    return EVENTS;
  }

  public static String getMembers()
  {
    return MEMBERS;
  }

  public static String getMemberEdit()
  {
    return MEMBER_EDIT;
  }
}
