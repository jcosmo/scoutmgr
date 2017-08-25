package scoutmgr.client;

import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import scoutmgr.client.ioc.FrontendContext;
import scoutmgr.client.ioc.ScoutmgrGinjector;

public final class Bootstrap
  implements Bootstrapper
{
  private static final Logger LOG = Logger.getLogger( Bootstrap.class.getName() );

  @Inject
  private ScoutmgrApp _scoutmgrApp;
  @Inject
  private PlaceManager _placeManager;
  @Inject
  private FrontendContext _frontendContext;

  @Override
  public void onBootstrap()
  {
    LOG.log( Level.FINE, "Bootstrapping application" );
    _scoutmgrApp.setPlacemanager( _placeManager );
    _scoutmgrApp.setFrontendContext( _frontendContext );
    _scoutmgrApp.start();
    LOG.log( Level.FINE, "Application started" );
  }
}
