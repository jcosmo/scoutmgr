package scoutmgr.client.application.login;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import javax.inject.Inject;
import scoutmgr.client.ScoutmgrApp;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.place.NameTokens;

public class LoginPresenter
  extends Presenter<LoginPresenter.View, LoginPresenter.Proxy>
  implements LoginUiHandlers
{
  @Inject
  private ScoutmgrApp _app;

  @Inject
  private PlaceManager _placeManager;

  @ProxyStandard
  @NameToken( NameTokens.LOGIN )
  @NoGatekeeper
  interface Proxy
    extends ProxyPlace<LoginPresenter>
  {
  }

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<LoginUiHandlers>
  {
    void onFailedLogin();

    void onSuccessfulLogin();
  }

  @Inject
  LoginPresenter( final EventBus eventBus,
                  final View view,
                  final Proxy proxy )
  {
    super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT );
    getView().setUiHandlers( this );
  }

  @Override
  public void prepareFromRequest( final PlaceRequest request )
  {
    if ( _app.isLoggedIn() )
    {
      _placeManager.revealPlace( new PlaceRequest.Builder().nameToken( NameTokens.UNAUTHORISED ).build(), false );
    }
    else
    {
      super.prepareFromRequest( request );
    }
  }
  public void onLogin( final String username, final String password )
  {
    _app.login( username, password);
    //() -> getView().onSuccessfulLogin(),
    //  () -> getView().onFailedLogin()
  }
}
