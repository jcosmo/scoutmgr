package scoutmgr.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import java.util.logging.Level;
import java.util.logging.Logger;
import scoutmgr.client.ioc.ScoutmgrGinjector;
import scoutmgr.client.ioc.ScoutmgrGwtRpcServicesModule;

public final class Scoutmgr
  implements EntryPoint
{
  private static final Logger LOG = Logger.getLogger( Scoutmgr.class.getName() );

  public void onModuleLoad()
  {
    try
    {
      ScoutmgrGwtRpcServicesModule.initialize();

      final ScoutmgrGinjector injector = GWT.create( ScoutmgrGinjector.class );

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

      LOG.info( "DataLoadService.connect starting..." );
      injector.getDataLoaderService().connect( new Runnable()
      {
        @Override
        public void run()
        {
          LOG.info( "Successfully connected to data loader service" );
          Window.alert( "Successfully connected to data loader service" );
        }
      } );

      RootLayoutPanel.get().add( injector.getMainPanel() );
    }
    catch ( final Exception e )
    {
      LOG.log( Level.SEVERE, "Unexpected problem initializing the application", e );
      Window.alert( "Error: " + e.getMessage() );
    }
  }
}
