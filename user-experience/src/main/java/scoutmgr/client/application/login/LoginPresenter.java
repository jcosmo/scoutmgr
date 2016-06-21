package scoutmgr.client.application.login;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import javax.inject.Inject;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.ioc.FrontendContext;
import scoutmgr.client.place.NameTokens;

public class LoginPresenter
  extends Presenter<LoginPresenter.View, LoginPresenter.Proxy>
  implements LoginUiHandlers
{
  @Inject
  FrontendContext _frontendContext;

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
    super( eventBus, view, proxy, RevealType.RootLayout );
    getView().setUiHandlers( this );
  }

  public void onLogin( final String username, final String password )
  {
    _frontendContext.login( username, password,
                            () -> getView().onSuccessfulLogin(),
                            () -> getView().onFailedLogin() );
  }
}
