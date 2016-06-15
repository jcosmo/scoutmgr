package scoutmgr.client.application.login;

import javax.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.application.events.EventsUiHandlers;
import scoutmgr.client.place.NameTokens;

public class LoginPresenter
  extends Presenter<LoginPresenter.View, LoginPresenter.Proxy>
  implements EventsUiHandlers
{
  @ProxyStandard
  @NameToken( NameTokens.LOGIN )
  @NoGatekeeper
  interface Proxy
    extends ProxyPlace<LoginPresenter>
  {
  }

  interface View
    extends com.gwtplatform.mvp.client.View
  {
  }

  @Inject
  LoginPresenter( final EventBus eventBus,
                  final View view,
                  final Proxy proxy )
  {
    super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT );
  }
}
