package scoutmgr.client.application.members;

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
import scoutmgr.client.entity.Person;
import scoutmgr.client.net.ScoutmgrDataLoaderService;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.application.scoutdetails.ScoutdetailsPresenter;
import scoutmgr.client.service.PersonnelService;
import scoutmgr.client.view.model.ScoutViewModel;

public class MembersPresenter
  extends Presenter<scoutmgr.client.application.members.MembersPresenter.View, scoutmgr.client.application.members.MembersPresenter.Proxy>
  implements MembersUiHandlers
{
  private static final java.util.logging.Logger LOG =
    java.util.logging.Logger.getLogger( MembersPresenter.class.getName() );

  @ProxyStandard
  @NameToken( { NameTokens.MEMBERS, NameTokens.MEMBER_EDIT } )
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
  private ScoutdetailsPresenter _scoutDetailsPresenter;

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
  public void prepareFromRequest( final PlaceRequest request )
  {
    super.prepareFromRequest( request );

    if ( NameTokens.getMemberEdit().equals( request.getNameToken() ) )
    {
      final String memberID = request.getParameter( "memberID", "" );
      addToPopupSlot( _scoutDetailsPresenter );
    }
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
    PlaceRequest request = _placeManager.getCurrentPlaceRequest();
    final PlaceRequest newPlace = new PlaceRequest.Builder( request ).nameToken( NameTokens.getMemberEdit() )
      .with( "memberID", "" ).build();
    LOG.warning( newPlace.toString() );
    _placeManager.revealPlace( newPlace );
  }

  @Override
  public void addScout()
  {
    PlaceRequest request = _placeManager.getCurrentPlaceRequest();
    final PlaceRequest newPlace = new PlaceRequest.Builder( request ).nameToken( NameTokens.getMemberEdit() )
      .with( "memberID", "" )
      .build();
    LOG.warning( newPlace.toString() );
    _placeManager.revealPlace( newPlace );
    //_personnelService.addScout( "a", "b", new Date(  ), "c");
  }
}
