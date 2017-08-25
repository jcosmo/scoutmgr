package scoutmgr.client.application.admin.badges;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.GatekeeperParams;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.entity.BadgeCategory;
import scoutmgr.client.entity.comparator.BadgeCategoryComparator;
import scoutmgr.client.event.MetadataLoadedEvent;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.security.HasRolesGatekeeper;
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
  @UseGatekeeper( HasRolesGatekeeper.class )
  @GatekeeperParams( "SITE_ADMIN" )
  interface Proxy
    extends ProxyPlace<BadgesPresenter>
  {
  }

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<BadgesUiHandlers>
  {
    void setBadgeCategoryViewModels( List<BadgeCategoryViewModel> badgeCategoryViewModels );
  }

  @ContentSlot
  public static final GwtEvent.Type<RevealContentHandler<?>> SLOT_LEFT_NAV = new GwtEvent.Type<>();
  @ContentSlot
  public static final GwtEvent.Type<RevealContentHandler<?>> SLOT_BADGES_CONTENT = new GwtEvent.Type<>();

  @javax.inject.Inject
  private EntityRepository _repository;

  @Inject
  private NavPresenter _navPresenter;

  @Inject
  private BadgeSectionPresenter _badgeLevelPresenter;

  @Inject
  BadgesPresenter( final EventBus eventBus,
                   final View view,
                   final Proxy proxy )
  {
    super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT );

    getView().setUiHandlers( this );

    _badgeCategoryViewModels = new ArrayList<>();

    _badgeCategoryComparator = new ViewModelComparator<>( new BadgeCategoryComparator() );

    eventBus.addHandler( MetadataLoadedEvent.TYPE, event -> setBadgesToUI() );
  }

  protected void onBind()
  {
    super.onBind();
    setInSlot( SLOT_LEFT_NAV, _navPresenter );
    setInSlot( SLOT_BADGES_CONTENT, _badgeLevelPresenter );
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
    viewModel.setSection( category.getScoutSection() );
    viewModel.setRank( category.getRank() );
  }
}
