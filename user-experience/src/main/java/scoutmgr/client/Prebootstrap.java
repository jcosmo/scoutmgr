package scoutmgr.client;

import com.gwtplatform.mvp.client.PreBootstrapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import scoutmgr.client.ioc.ScoutmgrGwtRpcServicesModule;

public final class Prebootstrap
  implements PreBootstrapper
{
  private static final Logger LOG = Logger.getLogger( Prebootstrap.class.getName() );

  @Override
  public void onPreBootstrap()
  {
    LOG.log( Level.FINE, "Pre Bootstrapping application" );
    ScoutmgrGwtRpcServicesModule.initialize();
  }
}
