package scoutmgr.client;

import com.gwtplatform.mvp.client.Bootstrapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import scoutmgr.client.ioc.ScoutmgrGinjector;

public final class Bootstrap
  implements Bootstrapper
{
  private static final Logger LOG = Logger.getLogger( Bootstrap.class.getName() );

  @Inject
  private ScoutmgrApp _scoutmgrApp;
  //@Inject
  //private ScoutmgrGinjector _injector;

  @Override
  public void onBootstrap()
  {
    LOG.log( Level.FINE, "Bootstrapping application" );
    //_scoutmgrApp.setInjector( _injector );
    _scoutmgrApp.start();
    LOG.log( Level.FINE, "Application started" );
  }
}
