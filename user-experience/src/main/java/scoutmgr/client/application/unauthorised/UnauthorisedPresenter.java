package scoutmgr.client.application.unauthorised;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import javax.inject.Inject;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.place.NameTokens;

public class UnauthorisedPresenter
  extends Presenter<UnauthorisedPresenter.View, UnauthorisedPresenter.Proxy>
{
  @ProxyStandard
  @NameToken( NameTokens.UNAUTHORISED )
  interface Proxy
    extends ProxyPlace<UnauthorisedPresenter>
  {
  }

  interface View
    extends com.gwtplatform.mvp.client.View
  {
  }

  @Inject
  UnauthorisedPresenter( final EventBus eventBus,
                         final View view,
                         final Proxy proxy )
  {
    super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT );
  }
}
