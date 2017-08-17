package scoutmgr.client;

import com.gwtplatform.mvp.client.PreBootstrapper;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Prebootstrap
  implements PreBootstrapper
{
  private static final Logger LOG = Logger.getLogger( Prebootstrap.class.getName() );

  @Override
  public void onPreBootstrap()
  {
    LOG.log( Level.FINE, "Pre Bootstrapping application" );
    try
    {
      new ScoutmgrApp().init();
    }
    catch ( final Exception e )
    {
      LOG.log( java.util.logging.Level.SEVERE, "Unexpected problem preparing to bootstrap the application", e );
      com.google.gwt.user.client.Window.alert( "Error: " + e.getMessage() );
    }
  }
}
