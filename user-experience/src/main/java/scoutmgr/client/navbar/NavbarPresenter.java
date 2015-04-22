package scoutmgr.client.navbar;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest.Builder;
import scoutmgr.client.place.NameTokens;

public class NavbarPresenter
  extends PresenterWidget<NavbarPresenter.View>
  implements NavbarUiHandlers, NavigationHandler
{
  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<NavbarUiHandlers>
  {
    void setMenuItemActive( String nameToken );
  }

  @Inject
  private PlaceManager _placeManager;

  @Override
  public void gotoEvents()
  {
    final PlaceRequest placeRequest = new Builder().nameToken( NameTokens.getEvents() ).build();
    _placeManager.revealPlace( placeRequest );
  }

  @Override
  public void gotoMembers()
  {
    final PlaceRequest placeRequest = new Builder().nameToken( NameTokens.getMembers() ).build();
    _placeManager.revealPlace( placeRequest );
  }

  @Inject
  NavbarPresenter( final EventBus eventBus,
                   final View view )
  {
    super( eventBus, view );

    getView().setUiHandlers( this );
  }


  @Override
  protected void onBind()
  {
    addRegisteredHandler( NavigationEvent.getType(), this );
    getView().setMenuItemActive( _placeManager.getCurrentPlaceRequest().getNameToken() );
  }

  @Override
  public void onNavigation( final NavigationEvent navigationEvent )
  {
    getView().setMenuItemActive( navigationEvent.getRequest().getNameToken() );
  }
}
