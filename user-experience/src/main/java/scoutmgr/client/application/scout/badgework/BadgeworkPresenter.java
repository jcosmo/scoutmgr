package scoutmgr.client.application.scout.badgework;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import java.util.ArrayList;
import javax.inject.Inject;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.entity.BadgeCategory;
import scoutmgr.client.entity.Person;
import scoutmgr.client.event.MetadataLoadedEvent;

@SuppressWarnings( "Convert2streamapi" )
public class BadgeworkPresenter
  extends PresenterWidget<BadgeworkPresenter.View>
  implements BadgeworkUiHandlers
{
  @Inject
  private EntityRepository _entityRepository;

  private Person _scout;

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<BadgeworkUiHandlers>
  {
    void setBadgeworkProgress( ArrayList<BadgeCategory> scoutLevel, final Person scout );

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
      if ( category.getScoutLevel().equals(_scout.getScoutLevel() ) )
      {
        badgeCategories.add( category );
      }
    }
    getView().setBadgeworkProgress( badgeCategories, _scout );
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
}
