package scoutmgr.client.entity.security;

import com.google.inject.Injector;
import javax.annotation.Nonnull;
import scoutmgr.client.entity.AbstractScoutmgrFactory;
import scoutmgr.client.test.util.ScoutmgrFactorySet;

public class SecurityFactory
  extends AbstractSecurityFactory
{
  public SecurityFactory( @Nonnull final ScoutmgrFactorySet factorySet,
                          @Nonnull final Injector injector )
  {
    super( factorySet, injector );
  }
}
