package scoutmgr.server.test.util;

import javax.transaction.UserTransaction;
import org.realityforge.guiceyloops.shared.AbstractModule;

public class ScoutmgrServerModule
  extends AbstractModule
{
  @Override
  protected void configure()
  {
    bindMock( UserTransaction.class );
  }
}
