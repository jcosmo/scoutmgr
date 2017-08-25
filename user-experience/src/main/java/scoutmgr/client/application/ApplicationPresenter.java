package scoutmgr.client.application;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.LockInteractionEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import javax.inject.Inject;
import scoutmgr.client.application.dialog.DialogPresenter;
import scoutmgr.client.application.footer.FooterPresenter;
import scoutmgr.client.application.navbar.NavbarPresenter;

public class ApplicationPresenter
  extends Presenter<ApplicationPresenter.View, ApplicationPresenter.Proxy>
{
  @Inject
  private NavbarPresenter _navbarPresenter;

  @Inject
  private FooterPresenter _footerPresenter;

  @ProxyStandard
  public interface Proxy
    extends com.gwtplatform.mvp.client.proxy.Proxy<ApplicationPresenter>
  {
  }

  interface View
    extends com.gwtplatform.mvp.client.View
  {
    void showLoading( boolean visible );
  }

  @Inject
  ApplicationPresenter( final EventBus eventBus,
                        final View view,
                        final Proxy proxy )
  {
    super( eventBus, view, proxy, RevealType.RootLayout );
  }

  @ContentSlot
  public static final Type<RevealContentHandler<?>> SLOT_MAIN_CONTENT = new Type<>();
  @ContentSlot
  public static final Type<RevealContentHandler<?>> SLOT_MAIN_NAVBAR = new Type<>();
  @ContentSlot
  public static final Type<RevealContentHandler<?>> SLOT_MAIN_FOOTER = new Type<>();
  @ContentSlot
  public static final Type<RevealContentHandler<?>> SLOT_DIALOG = new Type<>();

  /**
   * Display a short lock message whenever navigation is in progress.
   *
   * @param event The {@link LockInteractionEvent}.
   */
  @ProxyEvent
  public void onLockInteraction( final LockInteractionEvent event )
  {
    getView().showLoading( event.shouldLock() );
  }

  @Override
  protected void onBind()
  {
    super.onBind();
    setInSlot( SLOT_MAIN_NAVBAR, _navbarPresenter );
    setInSlot( SLOT_MAIN_FOOTER, _footerPresenter );
  }

  public void showDialog( final DialogPresenter dialogPresenter )
  {
    setInSlot( SLOT_DIALOG, dialogPresenter );
  }
}
