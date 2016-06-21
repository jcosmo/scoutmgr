package scoutmgr.client.ioc;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import java.util.logging.Logger;
import javax.inject.Inject;
import scoutmgr.client.event.MetadataLoadedEvent;
import scoutmgr.client.net.ScoutmgrDataLoaderService;
import scoutmgr.client.service.ScoutmgrAsyncErrorCallback;

public class FrontendContextImpl
  implements FrontendContext
{
  final static Logger LOG = Logger.getLogger( FrontendContextImpl.class.getName() );

  @Inject
  EventBus _eventBus;

  @Inject
  PlaceManager _placeManager;

  @Inject
  ScoutmgrDataLoaderService _dataloader;

  @Inject
  LoginManager _loginManager;

  @Override
  public void initialArrival()
  {
    _loginManager.initialArrival( this::onLogin, this::onAutoLoginFail,
                                  (error) -> _placeManager.revealErrorPlace("Error on startup: " + error) );
  }

  @Override
  public void login( final String username,
                     final String password,
                     final Runnable successfulLoginAction,
                     final Runnable unsuccessfulLoginAction )
  {
    _loginManager.login( username,
                         password,
                         () -> {
                           onLogin();
                           runIfPresent( successfulLoginAction );
                         },
                         unsuccessfulLoginAction,
                         null );
  }

  private void onLogin()
  {
    connectAndLoadMetadata( this::postLogin );
  }

  private void onAutoLoginFail()
  {
    connectAndLoadMetadata( _placeManager::revealCurrentPlace );
  }

  private void connectAndLoadMetadata( final Runnable postLoad )
  {
    _dataloader.connect( () -> {
      LOG.info( "Connected to data loader" );
      _dataloader.getSession().subscribeToMetadata( () -> {
        LOG.info( "Subscribed to Metadata" );
        _eventBus.fireEvent( new MetadataLoadedEvent() );
        runIfPresent( postLoad );
      } );
    } );
  }

  protected void postLogin()
  {
    _placeManager.revealCurrentPlace();
  }

  private void runIfPresent( final Runnable successfulLoginAction )
  {
    if ( null != successfulLoginAction )
    {
      successfulLoginAction.run();
    }
  }

  @Override
  public void logout()
  {
    _loginManager.completeLogout( _placeManager::revealCurrentPlace );
  }

  public boolean isLoggedIn()
  {
    return _loginManager.isLoggedOn();
  }
}

