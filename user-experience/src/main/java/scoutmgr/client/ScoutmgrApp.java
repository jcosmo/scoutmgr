package scoutmgr.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import scoutmgr.client.ioc.FrontendContext;
import scoutmgr.client.ioc.LoginManager;
import scoutmgr.client.ioc.ScoutmgrClientModule;

public class ScoutmgrApp
  extends AbstractScoutmgrApp<FrontendContext>
{
  private PlaceManager _placemanager;
  private LoginManager _loginManager;

  ScoutmgrApp()
  {
    super();
    ScoutmgrClientModule.setApp( this );
  }

  public void setPlacemanager( final PlaceManager placemanager )
  {
    _placemanager = placemanager;
  }

  public void setLoginManager( final LoginManager loginManager )
  {
    _loginManager = loginManager;
  }

  @Override
  protected void onAppcacheUpdateReadyEvent()
  {
/*
    final UpdateEvent event =
      new UpdateEvent( "Application update available",
                       "A new version of the application is available. Please click 'OK' to load it." );
    fireEvent( event );
 */
  }

  @Override
  protected void postStart()
  {
    getLoginManager().setListener( this );
    getLoginManager().initialArrival();
  }

  @Override
  protected void preStart()
  {
    setLoginManager( GWT.create( LoginManager.class ) );
  }

  @Override
  protected void prepareUI()
  {
    DOM.getElementById( "loading-message" ).removeFromParent();
/* TODO
    ScoutmgrResourceBundle.INSTANCE.style().ensureInjected();
    RootLayoutPanel.get().add( _injector.getMainPanel() );
    ensureInjector().getActivityManager().setDisplay( ensureInjector().getMainPanel() );
  */
  }

  public void login( final String username, final String password )
  {
    getLoginManager().login( username, password );
  }

  public void logout()
  {
    getLoginManager().completeLogout( );
  }

  public boolean isLoggedIn()
  {
    return getLoginManager().isLoggedOn() &&
           getFrontendContext().getUser() != null;
  }

  public Integer getLoggedInUserID()
  {
    if ( !isLoggedIn() )
    {
      throw new RuntimeException( "Accessing user when not logged in" );
    }
    return getLoginManager().getUserID();
  }

  public void onAuthSuccess( final LoginManager loginManager, final boolean authenticated )
  {
    if (authenticated)
    {
      connectAndLoadMetadata();
    }
    else
    {
      _placemanager.revealCurrentPlace();
    }
  }

  public void onAuthError( final LoginManager loginManager, final Throwable error )
  {
  }

  public void onAuthLogout( final LoginManager loginManager )
  {
    /*
    if ( null != _user )
    {
      final String sessionID = _dataloader.getSession().getSessionID();
      _dataSubscriptionService.unsubscribeFromUser( sessionID, _user.getID() );
    }
    _user = null;
    _person = null;
    _eventBus.fireEvent( new UserLoggedOutEvent() );
    _placeManager.revealCurrentPlace();
    */
  }

  private LoginManager getLoginManager()
  {
    return _loginManager;
  }

  private void connectAndLoadMetadata(  )
  {
    /*
    _dataloader.connect(
      () ->
      {
        final String sessionID = _dataloader.getSession().getSessionID();
        final Integer userID = _loginManager.getUserID();
        assert null != userID;

        LOG.info( "Connected to data loader" );

        _dataSubscriptionService.subscribeForUser(
          sessionID,
          userID,
          ( result ) ->
          {
            LOG.info( "Subscribed to Metadata" );
            _eventBus.fireEvent( new MetadataLoadedEvent() );
            _user = _entityRepository.getByID( User.class, userID );
            LOG.info( "User record retrieved for '" + _user.getUserName() + "'." );

            if ( null != _user.getPerson() )
            {
              _person = _user.getPerson();
              LOG.info( "Person downloaded for user '" + _user.getUserName() + "'." );
            }
            else
            {
              LOG.info( "No person associated with user" );
              _person = null;
            }
            _eventBus.fireEvent( new UserLoadedEvent( _user ) );
            runIfPresent( postLoad );
          }
        );
      } );
    */
  }
}
