package scoutmgr.server.test.util;

import com.google.inject.Injector;
import javax.annotation.Nonnull;

public class SecurityFactory
  extends AbstractSecurityFactory
{
  protected SecurityFactory( @Nonnull final Injector injector )
  {
    super( injector );
  }
}
