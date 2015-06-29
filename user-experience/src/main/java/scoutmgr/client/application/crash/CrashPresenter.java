package scoutmgr.client.application.crash;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.application.events.EventsUiHandlers;
import scoutmgr.client.place.NameTokens;

public class CrashPresenter
  extends Presenter<CrashPresenter.View, CrashPresenter.Proxy>
  implements EventsUiHandlers
{
  @ProxyStandard
  @NameToken( NameTokens.CRASH )
  interface Proxy
    extends ProxyPlace<CrashPresenter>
  {
  }

  interface View
    extends com.gwtplatform.mvp.client.View
  {
  }

  @Inject
  CrashPresenter( final EventBus eventBus,
                  final View view,
                  final Proxy proxy )
  {
    super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT );
  }
}
