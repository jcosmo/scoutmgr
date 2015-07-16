package scoutmgr.client.entity.comparator;

import java.util.Comparator;
import scoutmgr.client.entity.BadgeCategory;

public class BadgeCategoryComparator
  implements Comparator<BadgeCategory>
{
  @Override
  public int compare( final BadgeCategory c1, final BadgeCategory c2 )
  {
    if ( null == c1 && null == c2 )
    {
      return 0;
    }
    else if ( null == c1 )
    {
      return 1;
    }
    else if ( null == c2 )
    {
      return -1;
    }
    return Integer.compare( c1.getRank(), c2.getRank() );
  }
}
