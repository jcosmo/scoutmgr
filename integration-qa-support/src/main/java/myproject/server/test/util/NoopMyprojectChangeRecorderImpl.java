package myproject.server.test.util;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Typed;
import myproject.server.net.MyprojectChangeRecorder;

@ApplicationScoped
@Typed( MyprojectChangeRecorder.class )
public class NoopMyprojectChangeRecorderImpl
  implements MyprojectChangeRecorder
{
  public void recordEntityMessageForEntity( @Nonnull final Object entity, final boolean isUpdate )
  {
  }
}
