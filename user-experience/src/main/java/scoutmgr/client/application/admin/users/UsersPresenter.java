package scoutmgr.client.application.admin.users;

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
import scoutmgr.client.entity.security.User;
import scoutmgr.client.net.ScoutmgrGwtDataLoaderService;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.security.HasRolesGatekeeper;
import scoutmgr.client.service.security.UserService;
import scoutmgr.client.view.model.UserViewModel;

public class UsersPresenter
  extends Presenter<UsersPresenter.View, UsersPresenter.Proxy>
  implements UsersUiHandlers
{

  @ProxyStandard
  @NameToken( { NameTokens.ADMIN_USERS } )
  @UseGatekeeper( HasRolesGatekeeper.class )
  @GatekeeperParams( { "SITE_ADMIN", "USER_ADMIN" } )
  interface Proxy
    extends ProxyPlace<UsersPresenter>
  {
  }

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<UsersUiHandlers>
  {
    void setUsers( Collection<UserViewModel> values );
  }

  @Inject
  private PlaceManager _placeManager;

  @Inject
  private DialogPresenter _dialogPresenter;

  private ScoutmgrGwtDataLoaderService _dataloader;

  @Inject
  private UserService _userService;

  @Inject
  private EntityRepository _entityRepository;

  private EntityChangeBroker _changeBroker;

  private final HashMap<User, UserViewModel> _model2ViewModel = new HashMap<>();
  private EntityChangeListener _entityChangeListener;

  @Inject
  UsersPresenter( final EventBus eventBus,
                  final View view,
                  final Proxy proxy,
                  final EntityChangeBroker changeBroker,
                  final ScoutmgrGwtDataLoaderService dataLoader )
  {
    super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT );
    _changeBroker = changeBroker;
    _dataloader = dataLoader;

    _entityChangeListener = new EntityChangeListenerAdapter()
    {
      @Override
      public void entityAdded( @Nonnull final EntityChangeEvent event )
      {
        final User user = (User) event.getObject();
        final UserViewModel viewModel = new UserViewModel( user );
        _model2ViewModel.put( user, viewModel );
        getView().setUsers( _model2ViewModel.values() );
      }

      @Override
      public void entityRemoved( @Nonnull final EntityChangeEvent event )
      {
        if ( null != _model2ViewModel.remove( event.getObject() ) )
        {
          getView().setUsers( _model2ViewModel.values() );
        }
      }

      @Override
      public void attributeChanged( @Nonnull final EntityChangeEvent event )
      {
        final User user = (User) event.getObject();
        final UserViewModel viewModel = new UserViewModel( user );
        _model2ViewModel.put( user, viewModel );
        getView().setUsers( _model2ViewModel.values() );
      }
    };

    getView().setUiHandlers( this );
  }

  @Override
  protected void onReveal()
  {
    _model2ViewModel.clear();
    getView().setUsers( _model2ViewModel.values() );
    _dataloader.getSession().subscribeToUsers( this::initialiseViewModel );

    super.onReveal();
  }

  private void initialiseViewModel()
  {
    _model2ViewModel.clear();
    final ArrayList<User> users = _entityRepository.findAll( User.class );
    for ( final User user : users )
    {
      _model2ViewModel.put( user, new UserViewModel( user ) );
    }

    getView().setUsers( _model2ViewModel.values() );
    _changeBroker.addChangeListener( User.class, _entityChangeListener );
  }

  @Override
  protected void onHide()
  {
    super.onHide();
    _changeBroker.removeChangeListener( User.class, _entityChangeListener );
    _dataloader.getSession().unsubscribeFromUsers( null );
  }

  @Override
  public void viewUser( final User user )
  {
    final PlaceRequest request = _placeManager.getCurrentPlaceRequest();
    final PlaceRequest newPlace = new PlaceRequest.Builder( request ).nameToken( NameTokens.getAdminUser() )
      .with( "id", user.getID().toString() ).build();
    _placeManager.revealPlace( newPlace );
  }

  @Override
  public void editUser( final User user )
  {
    final PlaceRequest request = _placeManager.getCurrentPlaceRequest();
    final PlaceRequest newPlace = new PlaceRequest.Builder( request ).nameToken( NameTokens.getAdminEditUser() )
      .with( "id", user.getID().toString() ).build();
    _placeManager.revealPlace( newPlace );
  }

  @Override
  public void requestDeleteUser( final User user )
  {
    _dialogPresenter.configureConfirmation( "Are you sure?",
                                            "You are about to delete '" + user.getUserName() + "'\n" +
                                            "If you continue then this user will be unable to log in any more.",
                                            event ->
                                            {
                                              deleteUser( user );
                                              _dialogPresenter.close();
                                            },
                                            "Cancel",
                                            "Delete" );
    _dialogPresenter.open();
  }

  void deleteUser( final User user )
  {
    _userService.deleteUser( user.getID() );
  }

  @Override
  public void addUser()
  {
    final PlaceRequest request = _placeManager.getCurrentPlaceRequest();
    final PlaceRequest newPlace = new PlaceRequest.Builder( request ).nameToken( NameTokens.getAdminNewUser() ).build();
    _placeManager.revealPlace( newPlace );
  }
}
