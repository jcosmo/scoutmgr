package scoutmgr.client.application.troop;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import javax.inject.Inject;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.place.NameTokens;

public class TroopPresenter
  extends Presenter<TroopPresenter.View, TroopPresenter.Proxy>
  implements TroopUiHandlers
{
  @ProxyStandard
  @NameToken( { NameTokens.TROOP } )
  interface Proxy
    extends ProxyPlace<TroopPresenter>
  {
    //TODO: Sort out security
  }

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<TroopUiHandlers>
  {
  }

  @Inject
  TroopPresenter( final EventBus eventBus,
                  final View view,
                  final Proxy proxy )
  {
    super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT );

    getView().setUiHandlers( this );
  }
}
