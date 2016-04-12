package scoutmgr.client.application.scout;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import java.util.logging.Logger;
import org.realityforge.replicant.client.EntityChangeBroker;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.net.ScoutmgrDataLoaderService;
import scoutmgr.client.place.NameTokens;

public class ScoutPresenter
  extends Presenter<ScoutPresenter.View, ScoutPresenter.Proxy>
  implements ScoutUiHandlers
{
  @ProxyStandard
  @NameToken( { NameTokens.SCOUT } )
  interface Proxy
    extends ProxyPlace<ScoutPresenter>
  {
  }

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<ScoutUiHandlers>
  {
  }

  @Inject
  ScoutPresenter(  final EventBus eventBus,
                   final View view,
                   final Proxy proxy )
  {
    super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT );

    getView().setUiHandlers( this );
  }
}
