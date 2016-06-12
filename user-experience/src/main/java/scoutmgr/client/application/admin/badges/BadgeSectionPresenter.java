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
import scoutmgr.client.entity.ScoutSection;
import scoutmgr.client.place.NameTokens;

public class BadgeSectionPresenter
  extends PresenterWidget<BadgeSectionPresenter.View>
  implements NavigationHandler, BadgeSectionUiHandlers
{
  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<BadgeSectionUiHandlers>
  {
    void setBadgeSection( ScoutSection level );
  }

  @Inject
  private PlaceManager _placeManager;

  @Inject
  private EntityRepository _entityRepository;

  @Inject
  BadgeSectionPresenter( final EventBus eventBus,
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
      navigateToBadgeSection( level );
    }
  }

  private void navigateToBadgeSection( final String level )
  {
    final ArrayList<ScoutSection> scoutSections = _entityRepository.findAll( ScoutSection.class );
    for ( final ScoutSection scoutSection : scoutSections )
    {
      if ( scoutSection.getCode().equals( level ) )
      {
        getView().setBadgeSection( scoutSection );
        return;
      }
    }
    getView().setBadgeSection( null );
  }

}
