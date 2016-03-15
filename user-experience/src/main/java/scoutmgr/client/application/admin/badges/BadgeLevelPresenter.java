package scoutmgr.client.application.admin.badges;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
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
    if ( NameTokens.ADMIN_BADGES_LEVEL.equals( navigationEvent.getRequest().getNameToken()))
    {
      final String level = navigationEvent.getRequest().getParameter( "level", null );
      getView().setBadgeLevel( _entityRepository.findByID( ScoutLevel.class, level ) );
    }
  }
}