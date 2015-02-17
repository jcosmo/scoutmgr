package scoutmgr.client.entity;

import com.google.inject.Injector;
import javax.annotation.Nonnull;
import scoutmgr.client.test.util.ScoutmgrFactorySet;

public class ScoutmgrFactory
  extends AbstractScoutmgrFactory
{
  public ScoutmgrFactory( @Nonnull final ScoutmgrFactorySet factorySet,
                           @Nonnull final Injector injector )
  {
    super( factorySet, injector );
  }
}
