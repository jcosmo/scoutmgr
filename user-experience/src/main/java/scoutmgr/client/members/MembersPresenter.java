package scoutmgr.client.members;

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
import javax.annotation.Nonnull;
import org.realityforge.replicant.client.EntityChangeBroker;
import org.realityforge.replicant.client.EntityChangeEvent;
import org.realityforge.replicant.client.EntityChangeListener;
import scoutmgr.client.entity.Person;
import scoutmgr.client.footer.FooterPresenter;
import scoutmgr.client.navbar.NavbarPresenter;
import scoutmgr.client.net.ScoutmgrDataLoaderService;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.view.model.ListItemViewModel;
import scoutmgr.client.view.model.ScoutViewModel;

public class MembersPresenter
  extends Presenter<scoutmgr.client.members.MembersPresenter.View, scoutmgr.client.members.MembersPresenter.Proxy>
  implements MembersUiHandlers
{

  static final Object SLOT_MAIN_CONTENT = new Object();
  static final Object SLOT_NAVBAR_CONTENT = new Object();
  static final Object SLOT_FOOTER_CONTENT = new Object();

  @Inject
  private NavbarPresenter _navbarPresenter;

  @Inject
  private FooterPresenter _footerPresenter;

  @ProxyStandard
  @NameToken( NameTokens.MEMBERS )
  interface Proxy
    extends ProxyPlace<scoutmgr.client.members.MembersPresenter>
  {
  }

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<MembersUiHandlers>
  {
    void setMembers( Collection<ScoutViewModel> values );

    void resetAndFocus();
  }

  private ScoutmgrDataLoaderService _dataloader;

  private EntityChangeBroker _changeBroker;

  private final HashMap<Person, ScoutViewModel> _model2ViewModel = new HashMap<>();
  private final HashMap<ListItemViewModel, ArrayList<Person>> _viewModel2Model = new HashMap<>();
  private EntityChangeListener _entityChangeListener;

  @Inject
  MembersPresenter( final EventBus eventBus,
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
      public void entityAdded( @Nonnull final EntityChangeEvent event )
      {
        final Person person = (Person) event.getObject();
        final ScoutViewModel viewModel = new ScoutViewModel( person );
        _model2ViewModel.put( person, viewModel );
        getView().setMembers( _model2ViewModel.values() );
      }

      @Override
      public void entityRemoved( @Nonnull final EntityChangeEvent event )
      {

      }

      @Override
      public void attributeChanged( @Nonnull final EntityChangeEvent event )
      {

      }

      @Override
      public void relatedAdded( @Nonnull final EntityChangeEvent event )
      {

      }

      @Override
      public void relatedRemoved( @Nonnull final EntityChangeEvent event )
      {

      }
    };

    getView().setUiHandlers( this );
  }

  @Override
  protected void onBind()
  {
    super.onBind();
  }

  @Override
  protected void onReveal()
  {
    super.onReveal();
    _viewModel2Model.clear();
    _changeBroker.addChangeListener( Person.class, _entityChangeListener );
    _dataloader.getSession().subscribeToPeople( null );

    setInSlot( SLOT_NAVBAR_CONTENT, _navbarPresenter );
    setInSlot( SLOT_FOOTER_CONTENT, _footerPresenter );
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
    _viewModel2Model.clear();
    getView().resetAndFocus();
  }
}
