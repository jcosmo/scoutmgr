package scoutmgr.server.test.util;

import com.google.inject.Module;
import java.util.ArrayList;
import java.util.Collections;
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

  @Override
  protected Module[] getModules()
  {
    final ArrayList<Module> modules = new ArrayList<>();
    Collections.addAll( modules, super.getModules() );
    modules.add( new MyprojectIntegrationModule() );
    return modules.toArray( new Module[ modules.size() ] );
  }

  protected final String getServiceURL( final String service )
  {
    return AppServer.getServiceURL( service );
  }

  protected final String getSiteURL()
  {
    return AppServer.getSiteURL();
  }

  @Override
  protected Module[] getModules()
  {
    final ArrayList<Module> modules = new ArrayList<>();
    Collections.addAll( modules, super.getModules() );
    modules.add( new ScoutmgrIntegrationModule() );
    return modules.toArray( new Module[ modules.size() ] );
  }
}
