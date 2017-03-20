package scoutmgr.client.entity.security;

import com.google.inject.Injector;
import javax.annotation.Nonnull;

public class SecurityFactory
  extends AbstractSecurityFactory
{
  public SecurityFactory( @Nonnull final Injector injector )
  {
    super( injector );
  }
}
