package scoutmgr.client.application.members;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import java.util.Collection;
import java.util.HashMap;
import javax.annotation.Nonnull;
import org.realityforge.replicant.client.EntityChangeBroker;
import org.realityforge.replicant.client.EntityChangeEvent;
import org.realityforge.replicant.client.EntityChangeListener;
import org.realityforge.replicant.client.EntityChangeListenerAdapter;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.application.dialog.DialogPresenter;
import scoutmgr.client.entity.Person;
import scoutmgr.client.net.ScoutmgrDataLoaderService;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.service.PersonnelService;
import scoutmgr.client.view.model.ScoutViewModel;

public class MembersPresenter
  extends Presenter<scoutmgr.client.application.members.MembersPresenter.View, scoutmgr.client.application.members.MembersPresenter.Proxy>
  implements MembersUiHandlers
{
  @ProxyStandard
  @NameToken( { NameTokens.MEMBERS } )
  interface Proxy
    extends ProxyPlace<scoutmgr.client.application.members.MembersPresenter>
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

  private ScoutmgrDataLoaderService _dataloader;

  @Inject
  private PersonnelService _personnelService;

  private EntityChangeBroker _changeBroker;

  private final HashMap<Person, ScoutViewModel> _model2ViewModel = new HashMap<>();
  private EntityChangeListener _entityChangeListener;

  @Inject
  MembersPresenter( final EventBus eventBus,
                    final View view,
                    final Proxy proxy,
                    final EntityChangeBroker changeBroker,
                    final ScoutmgrDataLoaderService dataLoader )
  {
    super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT );
    _changeBroker = changeBroker;
    _dataloader = dataLoader;

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
        if ( null != _model2ViewModel.remove( (Person) event.getObject() ) )
        {
          getView().setMembers( _model2ViewModel.values() );
        }
      }
    };

    getView().setUiHandlers( this );
  }

  @Override
  protected void onReveal()
  {
    _model2ViewModel.clear();
    getView().setMembers( _model2ViewModel.values() );
    _changeBroker.addChangeListener( Person.class, _entityChangeListener );
    _dataloader.getSession().subscribeToPeople( null );

    super.onReveal();
  }

  @Override
  protected void onHide()
  {
    super.onHide();
    _changeBroker.removeChangeListener( Person.class, _entityChangeListener );
    _dataloader.getSession().unsubscribeFromPeople( null );
  }

  @Override
  public void editScout( final Person person )
  {
    final PlaceRequest request = _placeManager.getCurrentPlaceRequest();
    final PlaceRequest newPlace = new PlaceRequest.Builder( request ).nameToken( NameTokens.getMember() )
      .with( "id", person.getID().toString() ).build();
    _placeManager.revealPlace( newPlace );
  }

  @Override
  public void requestDeleteScout( final Person person )
  {
    _dialogPresenter.configureConfirmation( "Are you sure?",
                                            "Delete " + person.getFirstName() + " " + person.getLastName() + "?",
                                            new ClickHandler()
                                            {
                                              @Override
                                              public void onClick( final ClickEvent event )
                                              {
                                                deleteScout( person );
                                                removeFromPopupSlot( _dialogPresenter );
                                              }
                                            } );
    addToPopupSlot( _dialogPresenter, true );
  }

  void deleteScout( final Person person )
  {
    _personnelService.deleteScout( person.getID() );
  }

  @Override
  public void addScout()
  {
    final PlaceRequest request = _placeManager.getCurrentPlaceRequest();
    final PlaceRequest newPlace = new PlaceRequest.Builder( request ).nameToken( NameTokens.getNewMember() ).build();
    _placeManager.revealPlace( newPlace );
  }
}
