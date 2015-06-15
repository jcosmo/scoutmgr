package scoutmgr.client.application;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.LockInteractionEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class ApplicationPresenter
  extends Presenter<ApplicationPresenter.View, ApplicationPresenter.Proxy>
{
  @ProxyStandard
  public interface Proxy extends com.gwtplatform.mvp.client.proxy.Proxy<ApplicationPresenter>
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

  /**
   * Use this in leaf presenters, inside their {@link #revealInParent} method.
   */
  @ContentSlot
  public static final Type<RevealContentHandler<?>> SLOT_SetMainContent = new Type<>();


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
}
