package myproject.client.util;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Callback invoked on completion of every job
 */
public class GlobalAsyncCallback
  implements AsyncCallback
{
  private static final Logger LOG = Logger.getLogger( GlobalAsyncCallback.class.getName() );

  @Override
  public void onFailure( final Throwable caught )
  {
    LOG.log( Level.SEVERE, "Error invoking remote service", caught );
    Window.alert( "Error invoking remote service: " + caught.getMessage() );
  }

  @Override
  public void onSuccess( final Object result )
  {
  }
}
