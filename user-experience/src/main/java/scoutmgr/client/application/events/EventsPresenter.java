package scoutmgr.client.application.events;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import javax.inject.Inject;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.place.NameTokens;

public class EventsPresenter
  extends Presenter<EventsPresenter.View, EventsPresenter.Proxy>
  implements EventsUiHandlers
{
  @ProxyStandard
  @NameToken( NameTokens.EVENTS )
  interface Proxy
    extends ProxyPlace<EventsPresenter>
  {
  }

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<EventsUiHandlers>
  {
  }

  @Inject
  EventsPresenter( final EventBus eventBus,
                   final View view,
                   final Proxy proxy )
  {
    super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT );

    getView().setUiHandlers( this );
  }
}
