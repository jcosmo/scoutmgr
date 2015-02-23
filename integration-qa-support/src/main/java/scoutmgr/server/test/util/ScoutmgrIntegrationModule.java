package scoutmgr.server.test.util;

import com.google.inject.AbstractModule;
import javax.inject.Singleton;
import scoutmgr.server.net.NoopScoutmgrChangeRecorderImpl;
import scoutmgr.server.net.ScoutmgrChangeRecorder;

public class ScoutmgrIntegrationModule
  extends AbstractModule
{
  @Override
  protected void configure()
  {
    bind( ScoutmgrChangeRecorder.class ).to( NoopScoutmgrChangeRecorderImpl.class ).in( Singleton.class );
  }
}
