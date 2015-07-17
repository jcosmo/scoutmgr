package scoutmgr.client.application.admin.badges;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import java.util.logging.Logger;
import scoutmgr.client.data_type.BadgeScoutLevel;
import scoutmgr.client.place.NameTokens;

public class NavPresenter
  extends PresenterWidget<NavPresenter.View>
  implements NavigationHandler, NavUiHandlers
{
  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<NavUiHandlers>
  {
    void setBadgeLevelActive( BadgeScoutLevel level );
  }

  @Inject
  private PlaceManager _placeManager;

  @Inject
  NavPresenter( final EventBus eventBus,
                final View view )
  {
    super( eventBus, view );
    getView().setUiHandlers( this );
  }

  @Override
  protected void onBind()
  {
    super.onBind();
    addRegisteredHandler( NavigationEvent.getType(), this );
    updateViewLocation( _placeManager.getCurrentPlaceRequest() );
  }

  @Override
  public void onNavigation( final NavigationEvent navigationEvent )
  {
    final String nameToken = navigationEvent.getRequest().getNameToken();
    if ( NameTokens.ADMIN_BADGES.equals( nameToken ) || NameTokens.ADMIN_BADGE.equals( nameToken ) )
    {
      updateViewLocation( navigationEvent.getRequest() );
    }
  }

  private void updateViewLocation( final PlaceRequest request )
  {
    final String level = request.getParameter( "level", null );
    if ( null == level )
    {
      getView().setBadgeLevelActive( null );
    }
    else
    {
      getView().setBadgeLevelActive( BadgeScoutLevel.valueOf( level ) );
    }
  }

  @Override
  public void changeScoutLevel( final BadgeScoutLevel badgeScoutLevel )
  {
    final PlaceRequest currentPlace = _placeManager.getCurrentPlaceRequest();
    if ( null == badgeScoutLevel )
    {
      if ( !currentPlace.getNameToken().equals( NameTokens.ADMIN_BADGES ) )
      {
        _placeManager.revealPlace( new PlaceRequest.Builder().nameToken( NameTokens.ADMIN_BADGES ).build() );
      }
    }
    else
    {
      final PlaceRequest newPlace = new PlaceRequest.Builder().nameToken( NameTokens.ADMIN_BADGES_LEVEL )
        .with( "level", badgeScoutLevel.name() ).build();
      _placeManager.revealPlace( newPlace );
    }
  }
}
