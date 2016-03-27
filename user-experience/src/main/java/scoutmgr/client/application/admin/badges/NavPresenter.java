package scoutmgr.client.application.admin.badges;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import java.util.ArrayList;
import javax.annotation.Nonnull;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.entity.ScoutLevel;
import scoutmgr.client.event.MetadataLoadedEvent;
import scoutmgr.client.place.NameTokens;

public class NavPresenter
  extends PresenterWidget<NavPresenter.View>
  implements NavigationHandler, NavUiHandlers
{
  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<NavUiHandlers>
  {
    void setBadgeLevels( ArrayList<ScoutLevel> levels );

    void setBadgeLevelActive( ScoutLevel level );
  }

  @Inject
  private PlaceManager _placeManager;

  @Inject
  private EntityRepository _entityRepository;

  // Utilised if we return to this place without a selected level
  private ScoutLevel _lastBadgeLevel;
  // Utilised to store a desired level before the metadata is loaded
  private String _startupBadgeLevel;

  @Inject
  NavPresenter( final EventBus eventBus,
                final View view )
  {
    super( eventBus, view );
    _lastBadgeLevel = null;
    _startupBadgeLevel = null;
    getView().setUiHandlers( this );

    eventBus.addHandler( MetadataLoadedEvent.TYPE, new MetadataLoadedEvent.Handler()
    {
      @Override
      public void onMetadataLoaded( @Nonnull final MetadataLoadedEvent event )
      {
        // Push data to UI, and handle any pending navigation request
        getView().setBadgeLevels( _entityRepository.findAll( ScoutLevel.class ) );
        if ( null != _startupBadgeLevel )
        {
          navigateToBadgeLevel( _startupBadgeLevel );
        }
      }
    } );
  }

  @Override
  protected void onBind()
  {
    super.onBind();
    addRegisteredHandler( NavigationEvent.getType(), this );
    getView().setBadgeLevels( _entityRepository.findAll( ScoutLevel.class ) );
  }

  @Override
  public void onNavigation( final NavigationEvent navigationEvent )
  {
    final PlaceRequest request = navigationEvent.getRequest();
    final String nameToken = request.getNameToken();
    if ( NameTokens.ADMIN_BADGES_LEVEL.equals( nameToken ) )
    {
      final String level = request.getParameter( "level", null );
      _startupBadgeLevel = level;
      navigateToBadgeLevel( level );
    }
    else if ( NameTokens.ADMIN_BADGES.equals( nameToken ) )
    {
      changeScoutLevel( _lastBadgeLevel );
    }
  }

  private void navigateToBadgeLevel( final String level )
  {
    final ArrayList<ScoutLevel> scoutLevels = _entityRepository.findAll( ScoutLevel.class );
    for ( final ScoutLevel scoutLevel : scoutLevels )
    {
      if ( scoutLevel.getCode().equals( level ) )
      {
        _lastBadgeLevel = scoutLevel;
        getView().setBadgeLevelActive( _lastBadgeLevel );
        _startupBadgeLevel = null;
        break;
      }
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
