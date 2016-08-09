package scoutmgr.client.application.navbar;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import javax.inject.Inject;

public class NavbarPresenter
  extends PresenterWidget<NavbarPresenter.View>
  implements NavigationHandler
{
  interface View
    extends com.gwtplatform.mvp.client.View
  {
    void setMenuItemActive( PlaceRequest nameToken );
  }

  @Inject
  private PlaceManager _placeManager;

  @Inject
  NavbarPresenter( final EventBus eventBus,
                   final View view )
  {
    super( eventBus, view );
  }

  @Override
  protected void onBind()
  {
    super.onBind();
    addRegisteredHandler( NavigationEvent.getType(), this );
    getView().setMenuItemActive( _placeManager.getCurrentPlaceRequest() );
  }

  @Override
  public void onNavigation( final NavigationEvent navigationEvent )
  {
    getView().setMenuItemActive( navigationEvent.getRequest() );
  }
}
