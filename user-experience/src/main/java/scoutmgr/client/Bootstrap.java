package scoutmgr.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import scoutmgr.client.ioc.FrontendContext;

public final class Bootstrap
  implements Bootstrapper
{
  private static final Logger LOG = Logger.getLogger( Bootstrap.class.getName() );

  @Inject
  private FrontendContext _frontendContext;

  @Inject
  private PlaceManager _placeManager;

  @Override
  public void onBootstrap()
  {
    LOG.log( Level.FINE, "Bootstrapping application" );

    try
    {
      final Element element = DOM.getElementById( "loading-message" );
      if ( null != element )
      {
        element.removeFromParent();
      }

      _frontendContext.initialArrival();

      _placeManager.revealCurrentPlace();
    }
    catch ( final Exception e )
    {
      LOG.log( Level.SEVERE, "Unexpected problem initializing the application", e );
      Window.alert( "Error: " + e.getMessage() );
    }
  }
}
