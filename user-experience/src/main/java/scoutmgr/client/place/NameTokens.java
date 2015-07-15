package scoutmgr.client.place;

public class NameTokens
{
  public static final String CRASH = "/error";
  public static final String MEMBERS = "/members";
  public static final String MEMBER = "/member/{id}";
  public static final String NEW_MEMBER = "/member";
  public static final String EVENTS = "/events";

  public static String getEvents()
  {
    return EVENTS;
  }

  public static String getMembers()
  {
    return MEMBERS;
  }

  public static String getMember()
  {
    return MEMBER;
  }

  public static String getNewMember()
  {
    return NEW_MEMBER;
  }
}
