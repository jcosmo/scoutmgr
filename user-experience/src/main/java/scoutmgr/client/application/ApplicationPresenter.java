package scoutmgr.client.application;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.realityforge.replicant.client.EntityChangeBroker;
import org.realityforge.replicant.client.EntityChangeEvent;
import org.realityforge.replicant.client.EntityChangeListener;
import scoutmgr.client.entity.Person;
import scoutmgr.client.net.ScoutmgrDataLoaderService;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.view.model.ListItemViewModel;

public class ApplicationPresenter
  extends Presenter<ApplicationPresenter.View, ApplicationPresenter.Proxy>
  implements ApplicationUiHandlers
{
  private ScoutmgrDataLoaderService _dataloader;

  private EntityChangeBroker _changeBroker;

  private final HashMap<Person, ListItemViewModel> _model2ViewModel = new HashMap<>();
  private final HashMap<ListItemViewModel, ArrayList<Person>> _viewModel2Model = new HashMap<>();
  private EntityChangeListener _entityChangeListener;

  @ProxyStandard
  @NameToken( NameTokens.HOME )
  interface Proxy
    extends ProxyPlace<ApplicationPresenter>
  {
  }

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<ApplicationUiHandlers>
  {
    void resetAndFocus();

    void setItems( final Collection<ListItemViewModel> items );
  }

  @Inject
  ApplicationPresenter( final EventBus eventBus,
                        final View view,
                        final Proxy proxy,
                        final EntityChangeBroker changeBroker,
                        final ScoutmgrDataLoaderService dataLoader )
  {
    super( eventBus, view, proxy, RevealType.Root );

    _changeBroker = changeBroker;
    _dataloader = dataLoader;

    _entityChangeListener = new EntityChangeListener()
    {
      @Override
      public void entityAdded( final EntityChangeEvent event )
      {
        final Person person = (Person) event.getObject();
        final ListItemViewModel viewModel = new ListItemViewModel( person );
        _model2ViewModel.put( person, viewModel );
        getView().setItems( _model2ViewModel.values() );
      }

      @Override
      public void entityRemoved( final EntityChangeEvent event )
      {

      }

      @Override
      public void attributeChanged( final EntityChangeEvent event )
      {

      }

      @Override
      public void relatedAdded( final EntityChangeEvent event )
      {

      }

      @Override
      public void relatedRemoved( final EntityChangeEvent event )
      {

      }
    };

    getView().setUiHandlers( this );
  }

  @Override
  protected void onReveal()
  {
    super.onReveal();

    _changeBroker.addChangeListener( Person.class, _entityChangeListener );
    _dataloader.getSession().subscribeToPeople( null );
  }

  @Override
  protected void onHide()
  {
    super.onReveal();

    _changeBroker.removeChangeListener( Person.class, _entityChangeListener );
    _dataloader.getSession().unsubscribeFromPeople( null );
  }

  @Override
  protected void onReset()
  {
    super.onReset();

    // clean up view prior to show

    getView().resetAndFocus();
  }
}
