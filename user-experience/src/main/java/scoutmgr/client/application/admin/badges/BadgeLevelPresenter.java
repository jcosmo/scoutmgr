package scoutmgr.client.application.admin.badges;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import java.util.ArrayList;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.entity.ScoutLevel;
import scoutmgr.client.place.NameTokens;

public class BadgeLevelPresenter
  extends PresenterWidget<BadgeLevelPresenter.View>
  implements NavigationHandler, BadgeLevelUiHandlers
{
  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<BadgeLevelUiHandlers>
  {
    void setBadgeLevel( ScoutLevel level );
  }

  @Inject
  private PlaceManager _placeManager;

  @Inject
  private EntityRepository _entityRepository;

  @Inject
  BadgeLevelPresenter( final EventBus eventBus,
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
  }

  @Override
  public void onNavigation( final NavigationEvent navigationEvent )
  {
    if ( NameTokens.ADMIN_BADGES_LEVEL.equals( navigationEvent.getRequest().getNameToken() ) )
    {
      final String level = navigationEvent.getRequest().getParameter( "level", null );
      navigateToBadgeLevel( level );
    }
  }

  private void navigateToBadgeLevel( final String level )
  {
    final ArrayList<ScoutLevel> scoutLevels = _entityRepository.findAll( ScoutLevel.class );
    for ( final ScoutLevel scoutLevel : scoutLevels )
    {
      if ( scoutLevel.getCode().equals( level ) )
      {
        getView().setBadgeLevel( scoutLevel );
        return;
      }
    }
    getView().setBadgeLevel( null );
  }

}
