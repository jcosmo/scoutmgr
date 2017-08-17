package scoutmgr.client;

import com.google.gwt.user.client.DOM;
import javax.annotation.Nullable;
import scoutmgr.client.ioc.LoginManager;
import scoutmgr.client.ioc.ScoutmgrClientModule;
import scoutmgr.client.ioc.ScoutmgrGinjector;

public class ScoutmgrApp
  extends AbstractScoutmgrApp
{
  private ScoutmgrGinjector _injector;

  ScoutmgrApp()
  {
    super();
    ScoutmgrClientModule.setApp( this );
  }

  void setInjector( final ScoutmgrGinjector injector )
  {
    _injector = injector;
  }

  @Nullable
  @Override
  protected ScoutmgrGinjector getInjector()
  {
    return _injector;
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
    getInjector().getLoginManager().setListener( this );
    getInjector().getLoginManager().initialArrival();
  }

  @Override
  protected void preStart()
  {
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
    getInjector().getLoginManager().login( username, password );
  }

  private void runIfPresent( final Runnable successfulLoginAction )
  {
    if ( null != successfulLoginAction )
    {
      successfulLoginAction.run();
    }
  }

  public void logout()
  {
    getLoginManager().completeLogout( );
  }

  public boolean isLoggedIn()
  {
    return getLoginManager().isLoggedOn() &&
           getInjector().getFrontendContext().getUser() != null;
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
      getInjector().getPlaceManager().revealCurrentPlace();
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
    return getInjector().getLoginManager();
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
