package scoutmgr.client.test.security.util;

import com.google.inject.Injector;
import javax.annotation.Nonnull;
import scoutmgr.client.entity.AbstractScoutmgrFactory;
import scoutmgr.client.test.security.util.AbstractSecurityStructFactory;

public class SecurityStructFactory
  extends AbstractSecurityStructFactory
{
  public SecurityStructFactory( @Nonnull final Injector injector )
  {
    super( injector );
  }
}
