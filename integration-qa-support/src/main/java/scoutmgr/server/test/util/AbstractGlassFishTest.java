package scoutmgr.server.test.util;

import javax.transaction.TransactionSynchronizationRegistry;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public abstract class AbstractGlassFishTest
  extends AbstractScoutmgrEntityTest
{
  @BeforeSuite
  public final void setUpServices()
    throws Exception
  {
    AppServer.setUpAppServer();
  }

  @AfterSuite
  public final void tearDownServices()
  {
    AppServer.tearDownAppServer();
  }

  @BeforeMethod
  public void preTest()
    throws Exception
  {
    super.preTest();
    s( TransactionSynchronizationRegistry.class ).putResource( "ReplicationActive", "Test" );
    usesTransaction();
  }

  protected final String getServiceURL( final String service )
  {
    return AppServer.getServiceURL( service );
  }

  protected final String getSiteURL()
  {
    return AppServer.getSiteURL();
  }
}
