package scoutmgr.client.ioc;

import com.google.gwt.user.client.Cookies;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import scoutmgr.Constants;
import scoutmgr.client.data_type.security.TokenDTO;
import scoutmgr.client.service.ScoutmgrAsyncCallback;
import scoutmgr.client.service.ScoutmgrAsyncErrorCallback;
import scoutmgr.client.service.security.AuthenticationService;

public class LoginManager
{
  @Inject
  private AuthenticationService _authenticationService;

  /**
   * The user object identifying the logged in user.
   */
  @Nullable
  private Integer _userID;

  public final boolean isLoggedOn()
  {
    return null != _userID;
  }

  public void login( @Nonnull final String username,
                     @Nonnull final String password,
                     @Nullable final Runnable onLoginAction,
                     @Nullable final Runnable onUnsuccessfulLoginAction,
                     @Nullable final ScoutmgrAsyncErrorCallback onError )
  {
    _authenticationService.authenticate( username,
                                         password,
                                         new ScoutmgrAsyncCallback<TokenDTO>()
                                         {
                                           @Override
                                           public void onSuccess( final TokenDTO token )
                                           {
                                             if ( null != token )
                                             {
                                               completeLogin( token.getUserID(), token.getToken() );
                                               runIfPresent( onLoginAction );
                                             }
                                             else
                                             {
                                               runIfPresent( onUnsuccessfulLoginAction );
                                             }
                                           }
                                         },
                                         onError );
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

  public void completeLogout( @Nullable final Runnable runnable )
  {
    _userID = null;
    performDisconnect( runnable );
  }

  private void performDisconnect( final Runnable runnable )
  {
    final String authToken = getAuthToken();
    if ( null != authToken )
    {
      _authenticationService.logout( authToken );
    }
    resetAuthState();
    runIfPresent( runnable );
  }

  public void initialArrival( @Nullable final Runnable onAuthenticatedAction,
                              @Nullable final Runnable onUnauthenticatedAction,
                              @Nullable final ScoutmgrAsyncErrorCallback onErrorAction )
  {
    final String token = getAuthToken();
    if ( null == token )
    {
      resetAuthState();
      unauthenticatedFlow( onUnauthenticatedAction );
    }
    else
    {
      _authenticationService.reAuthenticate( token,
                                             new ScoutmgrAsyncCallback<Integer>()
                                             {
                                               @Override
                                               public void onSuccess( final Integer userID )
                                               {
                                                 onReAuthenticateResponse( userID,
                                                                           token,
                                                                           onAuthenticatedAction,
                                                                           onUnauthenticatedAction );
                                               }
                                             },
                                             onErrorAction );
    }
  }

  private void onReAuthenticateResponse( @Nullable final Integer userID,
                                         @Nonnull final String token,
                                         @Nullable final Runnable onAuthenticatedAction,
                                         @Nullable final Runnable onUnauthenticatedAction )
  {
    if ( null != userID )
    {
      authenticatedFlow( userID, token, onAuthenticatedAction );
    }
    else
    {
      unauthenticatedFlow( onUnauthenticatedAction );
    }
  }

  private void authenticatedFlow( final int userID, final String token, @Nullable final Runnable action )
  {
    completeLogin( userID, token );
    runIfPresent( action );
  }

  private void unauthenticatedFlow( @Nullable final Runnable action )
  {
    resetAuthState();
    runIfPresent( action );
  }

  @Nullable
  protected final Integer getUserID()
  {
    return _userID;
  }

  private void runIfPresent( @Nullable final Runnable runnable )
  {
    if ( null != runnable )
    {
      runnable.run();
    }
  }
}
