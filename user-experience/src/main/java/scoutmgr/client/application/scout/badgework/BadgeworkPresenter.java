package scoutmgr.client.application.scout.badgework;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import java.util.ArrayList;
import javax.inject.Inject;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.entity.Badge;
import scoutmgr.client.entity.BadgeCategory;
import scoutmgr.client.entity.Person;
import scoutmgr.client.event.MetadataLoadedEvent;
import scoutmgr.client.view.model.ScoutViewModel;

@SuppressWarnings( "Convert2streamapi" )
public class BadgeworkPresenter
  extends PresenterWidget<BadgeworkPresenter.View>
  implements BadgeworkUiHandlers
{
  @Inject
  private EntityRepository _entityRepository;

  @Inject
  BadgeworkProgressPresenter _badgeworkProgressPresenter;

  private Person _scout;

  static final String POPUP_PROGRESS_PANEL_SLOT = "popupProgressPanel";

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<BadgeworkUiHandlers>
  {
    void setBadgeworkProgress( ArrayList<BadgeCategory> scoutLevel, final ScoutViewModel scout );

    void reset();
  }

  @Inject
  BadgeworkPresenter( final EventBus eventBus,
                      final View view )
  {
    super( eventBus, view );
    getView().setUiHandlers( this );
    eventBus.addHandler( MetadataLoadedEvent.TYPE, event -> setBadgeworkToUI() );
  }

  private void setBadgeworkToUI()
  {
    if ( null == _scout )
    {
      getView().reset();
      return;
    }

    final ArrayList<BadgeCategory> allBadgeCategories = _entityRepository.findAll( BadgeCategory.class );
    final ArrayList<BadgeCategory> badgeCategories = new ArrayList<>();
    for ( final BadgeCategory category : allBadgeCategories )
    {
      if ( category.getScoutLevel().equals( _scout.getScoutLevel() ) )
      {
        badgeCategories.add( category );
      }
    }
    getView().setBadgeworkProgress( badgeCategories, new ScoutViewModel( _scout ) );
  }

  public void configureForScout( final Person scout )
  {
    _scout = scout;
    if ( null == scout )
    {
      getView().reset();
    }
    else
    {
      setBadgeworkToUI();
    }
  }

  @Override
  public void requestRecordProgress( final int badgeID )
  {
    final Badge badge = _entityRepository.findByID( Badge.class, badgeID );
    _badgeworkProgressPresenter.configure( _scout, badge );
    setInSlot( POPUP_PROGRESS_PANEL_SLOT, _badgeworkProgressPresenter );
  }
}
