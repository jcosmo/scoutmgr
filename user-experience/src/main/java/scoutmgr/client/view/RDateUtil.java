package scoutmgr.client.view;

import org.realityforge.gwt.datatypes.client.date.RDate;

public class RDateUtil
{
  public static String formatRDate( final RDate date )
  {
    return date.getDay() + "/" + date.getMonth() + "/" + date.getYear();
  }
}
