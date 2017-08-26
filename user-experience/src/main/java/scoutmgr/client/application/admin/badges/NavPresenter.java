package scoutmgr.client.application.admin.badges;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import java.util.ArrayList;
import javax.inject.Inject;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.entity.ScoutSection;
import scoutmgr.client.event.MetadataLoadedEvent;
import scoutmgr.client.place.NameTokens;

public class NavPresenter
  extends PresenterWidget<NavPresenter.View>
  implements NavigationHandler, NavUiHandlers
{
  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<NavUiHandlers>
  {
    void setBadgeSections( ArrayList<ScoutSection> levels );

    void setBadgeSectionActive( ScoutSection level );
  }

  @Inject
  private PlaceManager _placeManager;

  @Inject
  private EntityRepository _entityRepository;

  // Utilised if we return to this place without a selected level
  private ScoutSection _lastBadgeSection;
  // Utilised to store a desired level before the metadata is loaded
  private String _startupBadgeSection;

  @Inject
  NavPresenter( final EventBus eventBus,
                final View view )
  {
    super( eventBus, view );
    _lastBadgeSection = null;
    _startupBadgeSection = null;
    getView().setUiHandlers( this );

    eventBus.addHandler( MetadataLoadedEvent.TYPE, event ->
    {
      // Push data to UI, and handle any pending navigation request
      getView().setBadgeSections( _entityRepository.findAll( ScoutSection.class ) );
      if ( null != _startupBadgeSection )
      {
        navigateToBadgeSection( _startupBadgeSection );
      }
    } );
  }

  @Override
  protected void onBind()
  {
    super.onBind();
    addRegisteredHandler( NavigationEvent.getType(), this );
    getView().setBadgeSections( _entityRepository.findAll( ScoutSection.class ) );
  }

  @Override
  public void onNavigation( final NavigationEvent navigationEvent )
  {
    final PlaceRequest request = navigationEvent.getRequest();
    final String nameToken = request.getNameToken();
    if ( NameTokens.ADMIN_BADGES_LEVEL.equals( nameToken ) )
    {
      final String level = request.getParameter( "level", null );
      _startupBadgeSection = level;
      navigateToBadgeSection( level );
    }
    else if ( NameTokens.ADMIN_BADGES.equals( nameToken ) )
    {
      changeScoutSection( _lastBadgeSection );
    }
  }

  private void navigateToBadgeSection( final String level )
  {
    final ArrayList<ScoutSection> scoutSections = _entityRepository.findAll( ScoutSection.class );
    for ( final ScoutSection scoutSection : scoutSections )
    {
      if ( scoutSection.getCode().equals( level ) )
      {
        _lastBadgeSection = scoutSection;
        getView().setBadgeSectionActive( _lastBadgeSection );
        _startupBadgeSection = null;
        break;
      }
    }
  }

  @Override
  public void changeScoutSection( final ScoutSection badgeScoutSection )
  {
    _lastBadgeSection = badgeScoutSection;
    final PlaceRequest currentPlace = _placeManager.getCurrentPlaceRequest();
    if ( null == badgeScoutSection )
    {
      if ( !currentPlace.getNameToken().equals( NameTokens.ADMIN_BADGES ) )
      {
        _placeManager.revealPlace( new PlaceRequest.Builder().nameToken( NameTokens.ADMIN_BADGES ).build() );
      }
    }
    else
    {
      final PlaceRequest newPlace = new PlaceRequest.Builder().nameToken( NameTokens.ADMIN_BADGES_LEVEL )
        .with( "level", badgeScoutSection.getCode() ).build();
      _placeManager.revealPlace( newPlace );
    }
  }
}
