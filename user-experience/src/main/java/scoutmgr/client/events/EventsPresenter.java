package scoutmgr.client.events;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import scoutmgr.client.footer.FooterPresenter;
import scoutmgr.client.navbar.NavbarPresenter;
import scoutmgr.client.place.NameTokens;

public class EventsPresenter
  extends Presenter<EventsPresenter.View, EventsPresenter.Proxy>
  implements EventsUiHandlers
{
  static final Object SLOT_MAIN_CONTENT = new Object();
  static final Object SLOT_NAVBAR_CONTENT = new Object();
  static final Object SLOT_FOOTER_CONTENT = new Object();

  @Inject
  private NavbarPresenter _navbarPresenter;

  @Inject
  private FooterPresenter _footerPresenter;

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
    super( eventBus, view, proxy, RevealType.Root );

    getView().setUiHandlers( this );
  }

  @Override
  protected void onBind()
  {
    super.onBind();
    setInSlot( SLOT_NAVBAR_CONTENT, _navbarPresenter );
    setInSlot( SLOT_FOOTER_CONTENT, _footerPresenter );
  }

  @Override
  protected void onReveal()
  {
    super.onReveal();
  }

  @Override
  protected void onHide()
  {
    super.onReveal();
  }

  @Override
  protected void onReset()
  {
    super.onReset();
  }
}
