package scoutmgr.client.application.admin.members;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.GatekeeperParams;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import org.realityforge.replicant.client.EntityChangeBroker;
import org.realityforge.replicant.client.EntityChangeEvent;
import org.realityforge.replicant.client.EntityChangeListener;
import org.realityforge.replicant.client.EntityChangeListenerAdapter;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.application.dialog.DialogPresenter;
import scoutmgr.client.entity.Person;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.security.HasRolesGatekeeper;
import scoutmgr.client.service.PersonnelService;
import scoutmgr.client.view.model.ScoutViewModel;

public class MembersPresenter
  extends Presenter<MembersPresenter.View, MembersPresenter.Proxy>
  implements MembersUiHandlers
{
  @ProxyStandard
  @NameToken( { NameTokens.ADMIN_SCOUTS } )
  @UseGatekeeper( HasRolesGatekeeper.class )
  @GatekeeperParams( { "SITE_ADMIN", "MEMBER_ADMIN" } )
  interface Proxy
    extends ProxyPlace<MembersPresenter>
  {
  }

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<MembersUiHandlers>
  {
    void setMembers( Collection<ScoutViewModel> values );
  }

  @Inject
  private PlaceManager _placeManager;

  @Inject
  private DialogPresenter _dialogPresenter;

  @Inject
  private PersonnelService _personnelService;

  @Inject
  private EntityRepository _entityRepository;

  private EntityChangeBroker _changeBroker;

  private final HashMap<Person, ScoutViewModel> _model2ViewModel = new HashMap<>();
  private EntityChangeListener _entityChangeListener;

  @Inject
  MembersPresenter( final EventBus eventBus,
                    final View view,
                    final Proxy proxy,
                    final EntityChangeBroker changeBroker )
  {
    super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT );
    _changeBroker = changeBroker;

    _entityChangeListener = new EntityChangeListenerAdapter()
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
        if ( null != _model2ViewModel.remove( event.getObject() ) )
        {
          getView().setMembers( _model2ViewModel.values() );
        }
      }

      @Override
      public void attributeChanged( @Nonnull final EntityChangeEvent event )
      {
        final Person person = (Person) event.getObject();
        final ScoutViewModel viewModel = new ScoutViewModel( person );
        _model2ViewModel.put( person, viewModel );
        getView().setMembers( _model2ViewModel.values() );
      }
    };

    getView().setUiHandlers( this );
  }

  @Override
  protected void onReveal()
  {
    _model2ViewModel.clear();
    getView().setMembers( _model2ViewModel.values() );
    initialiseViewModel();

    super.onReveal();
  }

  private void initialiseViewModel()
  {
    _model2ViewModel.clear();
    final ArrayList<Person> people = _entityRepository.findAll( Person.class );
    for ( final Person person : people )
    {
      _model2ViewModel.put( person, new ScoutViewModel( person ) );
    }
    getView().setMembers( _model2ViewModel.values() );
    _changeBroker.addChangeListener( Person.class, _entityChangeListener );
  }

  @Override
  protected void onHide()
  {
    super.onHide();
    _changeBroker.removeChangeListener( Person.class, _entityChangeListener );
    //_dataloader.getSession().unsubscribeFromPeople( null );
  }

  @Override
  public void viewScout( final Person person )
  {
    final PlaceRequest request = _placeManager.getCurrentPlaceRequest();
    final PlaceRequest newPlace = new PlaceRequest.Builder( request ).nameToken( NameTokens.getScout() )
      .with( "id", person.getID().toString() ).build();
    _placeManager.revealPlace( newPlace );
  }

  @Override
  public void editScout( final Person person )
  {
    final PlaceRequest request = _placeManager.getCurrentPlaceRequest();
    final PlaceRequest newPlace = new PlaceRequest.Builder( request ).nameToken( NameTokens.getAdminScout() )
      .with( "id", person.getID().toString() ).build();
    _placeManager.revealPlace( newPlace );
  }

  @Override
  public void requestDeleteScout( final Person person )
  {
    _dialogPresenter.configureConfirmation( "Are you sure?",
                                            "You are about to delete '" + person.getFirstName() + " " + person.getLastName() + "'\n" +
                                            "If you continue then all history for this Scout's badgework, event attendance, etc will be permanently lost.",
                                            event ->
                                            {
                                              deleteScout( person );
                                              _dialogPresenter.close();
                                            },
                                            "Cancel",
                                            "Delete" );
    _dialogPresenter.open();
  }

  private void deleteScout( final Person person )
  {
    _personnelService.deletePerson( person.getID() );
  }

  @Override
  public void addScout()
  {
    final PlaceRequest request = _placeManager.getCurrentPlaceRequest();
    final PlaceRequest newPlace = new PlaceRequest.Builder( request ).nameToken( NameTokens.getAdminNewScout() ).build();
    _placeManager.revealPlace( newPlace );
  }
}
