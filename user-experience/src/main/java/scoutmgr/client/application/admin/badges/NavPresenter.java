package scoutmgr.client.application.admin.badges;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class NavPresenter
  extends PresenterWidget<NavPresenter.View>
  implements NavigationHandler
{
  interface View
    extends com.gwtplatform.mvp.client.View
  {
    void setMenuItemActive( String nameToken );
  }

  @Inject
  private PlaceManager _placeManager;

  @Inject
  NavPresenter( final EventBus eventBus,
                final View view )
  {
    super( eventBus, view );
  }

  @Override
  protected void onBind()
  {
    super.onBind();
    addRegisteredHandler( NavigationEvent.getType(), this );
    getView().setMenuItemActive( _placeManager.getCurrentPlaceRequest().getNameToken() );
  }

  @Override
  public void onNavigation( final NavigationEvent navigationEvent )
  {
    getView().setMenuItemActive( navigationEvent.getRequest().getNameToken() );
  }
}
