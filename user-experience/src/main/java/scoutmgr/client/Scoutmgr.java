package scoutmgr.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.gwtplatform.mvp.client.ApplicationController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import scoutmgr.client.ioc.FrontendContext;
import scoutmgr.client.ioc.ScoutmgrGinjector;
import scoutmgr.client.ioc.ScoutmgrGwtRpcServicesModule;

public final class Scoutmgr
  implements EntryPoint
{
  private static final Logger LOG = Logger.getLogger( Scoutmgr.class.getName() );

  private static final ApplicationController _controller = GWT.create( ApplicationController.class );

  @Inject
  FrontendContext _frontendContext;

  public void onModuleLoad()
  {
    try
    {
      ScoutmgrGwtRpcServicesModule.initialize();

      GWT.setUncaughtExceptionHandler( new GWT.UncaughtExceptionHandler()
      {
        public void onUncaughtException( final Throwable e )
        {
          LOG.log( Level.SEVERE, "Unexpected error: " + e, e );
        }
      } );

      final Element element = DOM.getElementById( "loading-message" );
      if ( null != element )
      {
        element.removeFromParent();
      }

      _controller.init();
      _frontendContext.initialArrival();
    }
    catch ( final Exception e )
    {
      LOG.log( Level.SEVERE, "Unexpected problem initializing the application", e );
      Window.alert( "Error: " + e.getMessage() );
    }
  }
}
