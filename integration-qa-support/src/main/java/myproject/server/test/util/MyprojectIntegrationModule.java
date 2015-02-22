package myproject.server.test.util;

import com.google.inject.AbstractModule;
import javax.inject.Singleton;
import myproject.server.net.MyprojectChangeRecorder;

public class MyprojectIntegrationModule
  extends AbstractModule
{
  @Override
  protected void configure()
  {
    bind( MyprojectChangeRecorder.class ).to( NoopMyprojectChangeRecorderImpl.class ).in( Singleton.class );
  }
}
