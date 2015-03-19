package scoutmgr.client.application;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import scoutmgr.client.place.NameTokens;

public class ApplicationPresenter
  extends Presenter<ApplicationPresenter.View, ApplicationPresenter.Proxy>
  implements ApplicationUiHandlers
{
  @ProxyStandard
  @NameToken( NameTokens.home )
  interface Proxy
    extends ProxyPlace<ApplicationPresenter>
  {
  }

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<ApplicationUiHandlers>
  {
    void resetAndFocus();
  }

  private final PlaceManager _placeManager;

  @Inject
  ApplicationPresenter( final EventBus eventBus,
                        final View view,
                        final Proxy proxy,
                        final PlaceManager placeManager )
  {
    super( eventBus, view, proxy, RevealType.Root );

    _placeManager = placeManager;

    getView().setUiHandlers( this );
  }

  @Override
  protected void onReset()
  {
    super.onReset();

    getView().resetAndFocus();
  }
}
