package scoutmgr.client.application.scout.badgework;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import org.realityforge.replicant.client.EntityChangeBroker;
import org.realityforge.replicant.client.EntityChangeEvent;
import org.realityforge.replicant.client.EntityChangeListener;
import org.realityforge.replicant.client.EntityChangeListenerAdapter;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.entity.Badge;
import scoutmgr.client.entity.BadgeCategory;
import scoutmgr.client.entity.BadgeTask;
import scoutmgr.client.entity.Person;
import scoutmgr.client.entity.TaskCompletion;
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

  final EntityChangeBroker _changeBroker;
  private EntityChangeListener _scoutChangeListener;

  private Person _scout;

  static final String POPUP_PROGRESS_PANEL_SLOT = "popupProgressPanel";

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<BadgeworkUiHandlers>
  {
    void setBadgeworkProgress( final ArrayList<BadgeCategory> scoutLevel, final ScoutViewModel scout );

    void updateBadgeworkProgress( final BadgeTask task, final ScoutViewModel scout );

    void reset();
  }

  @Inject
  BadgeworkPresenter( final EventBus eventBus,
                      final View view,
                      final EntityChangeBroker changeBroker )
  {
    super( eventBus, view );
    _changeBroker = changeBroker;
    _scoutChangeListener = new EntityChangeListenerAdapter()
    {
      @Override
      public void relatedAdded( @Nonnull final EntityChangeEvent event )
      {
        if ( event.getObject() instanceof TaskCompletion )
        {
          final TaskCompletion completion = (TaskCompletion) event.getObject();
          getView().updateBadgeworkProgress( completion.getBadgeTask(), new ScoutViewModel( _scout ) );
        }
      }

      @Override
      public void relatedRemoved( @Nonnull final EntityChangeEvent event )
      {
        if ( event.getObject() instanceof TaskCompletion )
        {
          final TaskCompletion completion = (TaskCompletion) event.getObject();
          getView().updateBadgeworkProgress( completion.getBadgeTask(), new ScoutViewModel( _scout ) );
        }
      }
    };

    getView().setUiHandlers( this );
    eventBus.addHandler( MetadataLoadedEvent.TYPE, event -> setBadgeworkToUI() );
  }

  private void setBadgeworkToUI()
  {
    getView().reset();
    if ( null == _scout )
    {
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
    if ( null != _scout )
    {
      _changeBroker.removeChangeListener( _scout, _scoutChangeListener );
    }
    _scout = scout;
    if ( null == scout )
    {
      getView().reset();
    }
    else
    {
      _changeBroker.addChangeListener( _scout, _scoutChangeListener );
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
