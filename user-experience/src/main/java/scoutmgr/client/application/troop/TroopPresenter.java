package scoutmgr.client.application.troop;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.entity.BadgeCategory;
import scoutmgr.client.entity.Person;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.view.model.ScoutViewModel;

public class TroopPresenter
  extends Presenter<TroopPresenter.View, TroopPresenter.Proxy>
  implements TroopUiHandlers
{
  private final HashMap<Person, ScoutViewModel> _model2ViewModel = new HashMap<>();

  @Inject
  private EntityRepository _entityRepository;

  @ProxyStandard
  @NameToken( { NameTokens.TROOP } )
  interface Proxy
    extends ProxyPlace<TroopPresenter>
  {
    //TODO: Sort out security
  }

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<TroopUiHandlers>
  {
    void setScouts( Collection<ScoutViewModel> values );

    void setBadgeCategories( final List<BadgeCategory> badgeCategories );
  }

  @Inject
  TroopPresenter( final EventBus eventBus,
                  final View view,
                  final Proxy proxy )
  {
    super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT );

    getView().setUiHandlers( this );
  }

  @Override
  protected void onReveal()
  {
    initialiseViewModel();
    super.onReveal();
  }

  private void initialiseViewModel()
  {

    _model2ViewModel.clear();
    final ArrayList<Person> scouts = _entityRepository.findAll( Person.class );
    for ( final Person scout : scouts )
    {
      _model2ViewModel.put( scout, new ScoutViewModel( scout ) );
    }
    final ArrayList<BadgeCategory> allBadgeCategories = _entityRepository.findAll( BadgeCategory.class );
    final ArrayList<BadgeCategory> badgeCategories = new ArrayList<>();
    for ( final BadgeCategory category : allBadgeCategories )
    {
      //if ( category.getScoutSection().equals( _scout.getScoutSection() ) )
      {
        badgeCategories.add( category );
      }
    }

    getView().setBadgeCategories( badgeCategories );
    getView().setScouts( _model2ViewModel.values() );
  }
}
