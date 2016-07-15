package scoutmgr.client.ioc;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.entity.Person;
import scoutmgr.client.entity.security.User;
import scoutmgr.client.event.MetadataLoadedEvent;
import scoutmgr.client.event.security.UserLoadedEvent;
import scoutmgr.client.net.ScoutmgrDataLoaderService;

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

  @Inject
  EntityRepository _entityRepository;

  private User _user;
  private Person _person;

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
    // Assume that the place requires no data loaded!
    _placeManager.revealCurrentPlace();
  }

  private void connectAndLoadMetadata( final Runnable postLoad )
  {
    _dataloader.connect( () -> {
      final String sessionID = _dataloader.getSession().getSessionID();
      final Integer userID = _loginManager.getUserID();
      assert null != userID;

      LOG.info( "Connected to data loader" );
      _dataloader.getSession().subscribeToMetadata( () -> {
        LOG.info( "Subscribed to Metadata" );
        _eventBus.fireEvent( new MetadataLoadedEvent() );

        _dataloader.getSession().subscribeToUser( userID, () ->
        {
          _user = _entityRepository.getByID( User.class, userID );
          LOG.info( "User record retrieved for '" + _user.getUserName() + "'." );

          if ( null != _user.getPerson() )
          {
            _person = _user.getPerson();
            LOG.info( "Person downloaded for user '" + _user.getUserName() + "'." );
          }
          _eventBus.fireEvent( new UserLoadedEvent( _user ) );
        } );
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

  @Override
  public Integer getLoggedInUserID()
  {
    if ( !isLoggedIn() )
    {
      throw new RuntimeException( "Accessing user when not logged in" );
    }
    return _loginManager.getUserID();
  }
}

