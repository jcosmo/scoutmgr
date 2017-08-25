package scoutmgr.client;

import com.google.gwt.event.shared.EventBus;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.realityforge.gwt.appcache.client.ApplicationCache;
import org.realityforge.gwt.appcache.client.event.UpdateReadyEvent;
import org.realityforge.replicant.client.net.gwt.BaseFrontendContext;
import scoutmgr.client.ioc.FrontendContext;
import scoutmgr.client.ioc.ScoutmgrGwtRpcServicesModule;

@Generated( "Domgen" )
public abstract class AbstractScoutmgrApp<I extends BaseFrontendContext>
{
  private final String _applicationURL;
  private final String _serverUrl;
  private EventBus _eventBus;
  private I _frontendContext;

  protected AbstractScoutmgrApp( )
  {
    final String hostBaseURL = com.google.gwt.core.client.GWT.getHostPageBaseURL();
    final String hostURL = hostBaseURL.substring( 0, hostBaseURL.indexOf( '/', hostBaseURL.indexOf( "://" ) + 3 ) );
    final String moduleBaseURL = com.google.gwt.core.client.GWT.getModuleBaseURL();
    final String moduleHostURL = moduleBaseURL.substring( 0, moduleBaseURL.indexOf( '/', moduleBaseURL.indexOf( "://" ) + 3 ) );
    if( !hostURL.equals( moduleHostURL ) )
    {
      _applicationURL = hostBaseURL;
    }
    else
    {
      _applicationURL = moduleBaseURL.substring( 0, moduleBaseURL.length() - com.google.gwt.core.client.GWT.getModuleName().length() - 1 );
    }
    _serverUrl = _applicationURL.substring( 0, _applicationURL.indexOf( '/', _applicationURL.indexOf( "://" ) + 3 ) );
  }

  public String getApplicationURL()
  {
    return _applicationURL;
  }

  public String getServerUrl()
  {
    return _serverUrl;
  }

  public void setEventBus( final EventBus eventBus )
  {
    _eventBus = eventBus;
  }

  public void setFrontendContext( final I frontendContext )
  {
    _frontendContext = frontendContext;
  }

  @Nullable
  protected final EventBus getEventBus()
  {
    return _eventBus;
  }

  @Nullable
  protected final I getFrontendContext()
  {
    return _frontendContext;
  }

  protected final void fireEvent( @Nonnull final com.google.gwt.event.shared.GwtEvent<?> event )
  {
    final EventBus eventBus = getEventBus();
    if ( null != eventBus )
    {
      eventBus.fireEvent( event );
    }
  }

  protected final void fireEventFromSource( @Nonnull final com.google.gwt.event.shared.GwtEvent<?> event, @Nonnull final Object source )
  {
    final EventBus eventBus = getEventBus();
    if ( null != eventBus )
    {
      eventBus.fireEventFromSource( event, source );
    }
  }

  public void init()
  {
    initGwtRpcServices();
  }

  public void start()
  {
    preStart();
    setupAppCache();
    setupUncaughtExceptionHandler();
    prepareServices();
    prepareUI();
    postStart();
  }

  private void setupUncaughtExceptionHandler()
  {
    com.google.gwt.core.client.GWT.setUncaughtExceptionHandler( new com.google.gwt.core.client.GWT.UncaughtExceptionHandler()
    {
      @Override
      public void onUncaughtException( final Throwable e )
      {
        AbstractScoutmgrApp.this.onUncaughtException( e );
      }
    } );
  }

  private void setupAppCache()
  {
    final ApplicationCache cache = ApplicationCache.getApplicationCacheIfSupported();
    if ( null != cache )
    {
      cache.addUpdateReadyHandler( new UpdateReadyEvent.Handler()
      {
        @Override
        public void onUpdateReadyEvent( @Nonnull final UpdateReadyEvent event )
        {
          cache.swapCache();
          onAppcacheUpdateReadyEvent();
        }
      } );
    }
  }

  protected abstract void onAppcacheUpdateReadyEvent();

  protected abstract void postStart();

  protected abstract void preStart();

  protected void prepareServices()
  {
    getFrontendContext().connect();
  }

  protected abstract void prepareUI();

  protected void onUncaughtException( @Nonnull final Throwable e )
  {
    org.realityforge.gwt.lognice.BrowserExceptionUtil.log( e );
  }

  protected void initGwtRpcServices()
  {
    ScoutmgrGwtRpcServicesModule.getSessionContext().setBaseURL( getApplicationURL() );
    ScoutmgrGwtRpcServicesModule.initialize();
  }
}
