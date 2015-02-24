package scoutmgr.server.test.util;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Typed;
import scoutmgr.server.net.ScoutmgrChangeRecorder;

@ApplicationScoped
@Typed( ScoutmgrChangeRecorder.class )
public class NoopScoutmgrChangeRecorderImpl
  implements ScoutmgrChangeRecorder
{
  public void recordEntityMessageForEntity( @Nonnull final Object entity, final boolean isUpdate )
  {
  }
}
