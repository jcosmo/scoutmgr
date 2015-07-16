package scoutmgr.client.application.admin.badges;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.entity.BadgeCategory;
import scoutmgr.client.entity.comparator.BadgeCategoryComparator;
import scoutmgr.client.event.MetadataLoadedEvent;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.view.model.BadgeCategoryViewModel;
import scoutmgr.client.view.model.comparator.ViewModelComparator;

public class BadgesPresenter
  extends Presenter<BadgesPresenter.View, BadgesPresenter.Proxy>
  implements BadgesUiHandlers
{
  private final ViewModelComparator<BadgeCategoryViewModel> _badgeCategoryComparator;
  private List<BadgeCategoryViewModel> _badgeCategoryViewModels;

  @ProxyStandard
  @NameToken( { NameTokens.ADMIN_BADGES, NameTokens.ADMIN_BADGES_LEVEL } )
  public interface Proxy
    extends ProxyPlace<BadgesPresenter>
  {
  }

  public interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<BadgesUiHandlers>
  {
    void setBadgeCategoryViewModels( List<BadgeCategoryViewModel> badgeCategoryViewModels );
  }

  @Inject
  private PlaceManager _placeManager;

  @javax.inject.Inject
  private EntityRepository _repository;

  @Inject
  BadgesPresenter( final EventBus eventBus,
                   final View view,
                   final Proxy proxy )
  {
    super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT );

    getView().setUiHandlers( this );

    _badgeCategoryViewModels = new ArrayList<>(  );

    _badgeCategoryComparator = new ViewModelComparator<>( new BadgeCategoryComparator() );

    eventBus.addHandler( MetadataLoadedEvent.TYPE, new MetadataLoadedEvent.Handler()
    {
      @Override
      public void onMetadataLoaded( @Nonnull final MetadataLoadedEvent event )
      {
        setBadgesToUI();
      }
    } );
  }

  @Override
  protected void onReveal()
  {
    super.onReveal();
    setBadgesToUI();
  }

  private void setBadgesToUI()
  {
    final ArrayList<BadgeCategory> categories = _repository.findAll( BadgeCategory.class );
    _badgeCategoryViewModels.clear();
    for ( final BadgeCategory category : categories )
    {
      _badgeCategoryViewModels.add( buildBadgeCategoryViewModel( category ) );
    }
    Collections.sort( _badgeCategoryViewModels, _badgeCategoryComparator );
    getView().setBadgeCategoryViewModels( _badgeCategoryViewModels );
  }

  private BadgeCategoryViewModel buildBadgeCategoryViewModel( final BadgeCategory category )
  {
    final BadgeCategoryViewModel viewModel =
      new BadgeCategoryViewModel( category.getName(), category );
    populateBadgeCategoryViewModel( category, viewModel );
    return viewModel;
  }

  private void populateBadgeCategoryViewModel( final BadgeCategory category, final BadgeCategoryViewModel viewModel )
  {
    viewModel.setName( category.getName() );
    viewModel.setLevel( category.getScoutLevel() );
    viewModel.setRank( category.getRank() );
  }
}
