package scoutmgr.client.ioc;

import com.google.gwt.user.client.Cookies;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import scoutmgr.client.ScoutmgrApp;
import scoutmgr.client.service.security.AuthenticationService;
import scoutmgr.shared.Constants;

public class LoginManager
{
  @Inject
  private AuthenticationService _authenticationService;

  /**
   * The user object identifying the logged in user.
   */
  @Nullable
  private Integer _userID;
  private ScoutmgrApp _listener;

  public final boolean isLoggedOn()
  {
    return null != _userID;
  }

  public void initialArrival()
  {
    final String token = getAuthToken();
    if ( null == token )
    {
      unauthenticatedFlow();
    }
    else
    {
      _authenticationService.reAuthenticate( token,
                                             userID -> onReAuthenticateResponse( userID, token ) );
    }
  }

  public void login( @Nonnull final String username,
                     @Nonnull final String password )
  {
    _authenticationService.authenticate( username,
                                         password,
                                         token -> {
                                           if ( null != token )
                                           {
                                             completeLogin( token.getUserID(), token.getToken() );
                                             _listener.onAuthSuccess( this, true );
                                           }
                                           else
                                           {
                                             _listener.onAuthSuccess( this, false );
                                           }
                                         },
                                         error -> _listener.onAuthError( this, error ) );
  }

  private void completeLogin( final int userID, @Nonnull final String token )
  {
    _userID = userID;
    Cookies.setCookie( Constants.AUTH_COOKIE_NAME, token, null, null, "/", false );
  }

  @Nullable
  private String getAuthToken()
  {
    return Cookies.getCookie( Constants.AUTH_COOKIE_NAME );
  }

  private void resetAuthState()
  {
    Cookies.removeCookie( Constants.AUTH_COOKIE_NAME );
  }

  public void completeLogout()
  {
    _userID = null;
    performDisconnect();
  }

  private void performDisconnect()
  {
    final String authToken = getAuthToken();
    if ( null != authToken )
    {
      _authenticationService.logout( authToken );
    }
    resetAuthState();
    _listener.onAuthLogout( this );
  }

  private void onReAuthenticateResponse( @Nullable final Integer userID,
                                         @Nonnull final String token )
  {
    if ( null != userID )
    {
      authenticatedFlow( userID, token );
    }
    else
    {
      unauthenticatedFlow();
    }
  }

  private void authenticatedFlow( final int userID, final String token )
  {
    completeLogin( userID, token );
    _listener.onAuthSuccess( this, true );
  }

  private void unauthenticatedFlow()
  {
    resetAuthState();
    _listener.onAuthSuccess( this, false );
  }

  @Nullable
  public Integer getUserID()
  {
    return _userID;
  }

  public void setListener( final ScoutmgrApp listener )
  {
    _listener = listener;
  }
}
