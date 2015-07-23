package scoutmgr.client.application.admin.badges;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.entity.ScoutLevel;
import scoutmgr.client.place.NameTokens;

public class NavPresenter
  extends PresenterWidget<NavPresenter.View>
  implements NavigationHandler, NavUiHandlers
{
  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<NavUiHandlers>
  {
    void setBadgeLevelActive( ScoutLevel level );
  }

  @Inject
  private PlaceManager _placeManager;

  @Inject
  private EntityRepository _entityRepository;

  ScoutLevel _lastBadgeLevel;

  @Inject
  NavPresenter( final EventBus eventBus,
                final View view )
  {
    super( eventBus, view );
    _lastBadgeLevel = null;
    getView().setUiHandlers( this );
  }

  @Override
  protected void onBind()
  {
    super.onBind();
    addRegisteredHandler( NavigationEvent.getType(), this );
  }

  @Override
  public void onNavigation( final NavigationEvent navigationEvent )
  {
    final PlaceRequest request = navigationEvent.getRequest();
    final String nameToken = request.getNameToken();
    if ( NameTokens.ADMIN_BADGES_LEVEL.equals( nameToken ) )
    {
      final String level = request.getParameter( "level", null );
      final ScoutLevel scoutLevel = _entityRepository.findByID( ScoutLevel.class, level );
      if ( null != scoutLevel )
      {
        _lastBadgeLevel = scoutLevel;
      }
      getView().setBadgeLevelActive( _lastBadgeLevel );
    }
    else if ( NameTokens.ADMIN_BADGES.equals( nameToken ) )
    {
      changeScoutLevel( _lastBadgeLevel );
    }
  }

  @Override
  public void changeScoutLevel( final ScoutLevel badgeScoutLevel )
  {
    _lastBadgeLevel = badgeScoutLevel;
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
        .with( "level", badgeScoutLevel.getCode() ).build();
      _placeManager.revealPlace( newPlace );
    }
  }
}
