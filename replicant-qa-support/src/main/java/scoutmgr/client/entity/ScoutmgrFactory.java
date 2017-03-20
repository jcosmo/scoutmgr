package scoutmgr.client.entity;

import com.google.inject.Injector;
import javax.annotation.Nonnull;

public class ScoutmgrFactory
  extends AbstractScoutmgrFactory
{
  public ScoutmgrFactory( @Nonnull final Injector injector )
  {
    super( injector );
  }
}
