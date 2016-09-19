package scoutmgr.client.net;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import scoutmgr.client.data_type.PeopleFilterDTO;

public class ScoutmgrSessionContextImpl
  implements ScoutmgrSessionContext
{
  @Inject
  public ScoutmgrSessionContextImpl()
  {
  }

  @Override
  public boolean doesPeopleMatchEntity( @Nonnull final PeopleFilterDTO filter, @Nonnull final Object entity )
  {
    // TODO: implement filter properly
    return true;
  }
}
