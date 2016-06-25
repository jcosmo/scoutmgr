package scoutmgr.client.application.admin.users;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import javax.inject.Inject;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.entity.security.User;
import scoutmgr.client.net.ScoutmgrDataLoaderService;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.service.security.UserService;
import scoutmgr.client.view.model.UserViewModel;

public class UserFormPresenter
  extends Presenter<UserFormPresenter.View, UserFormPresenter.Proxy>
  implements UserFormUiHandlers
{
  private Integer _idForEdit;

  @ProxyStandard
  @NameToken( { NameTokens.ADMIN_USER, NameTokens.ADMIN_EDIT_USER, NameTokens.ADMIN_NEW_USER } )
  interface Proxy
    extends ProxyPlace<UserFormPresenter>
  {
  }

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<UserFormUiHandlers>
  {
    void reset();

    void setUser( UserViewModel viewModel );
  }

  @Inject
  private PlaceManager _placeManager;

  @Inject
  private ScoutmgrDataLoaderService _dataloader;

  @Inject
  private EntityRepository _entityRepository;

  @Inject
  private UserService _userService;

  @Inject
  UserFormPresenter( final EventBus eventBus,
                     final View view,
                     final Proxy proxy )
  {
    super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT );
    getView().setUiHandlers( this );
  }

  @Override
  public void prepareFromRequest( final PlaceRequest request )
  {
    super.prepareFromRequest( request );
    final String idStr = request.getParameter( "id", null );
    if ( null != idStr )
    {
      _idForEdit = Integer.valueOf( idStr );
      setUserForEdit( _idForEdit );
    }
    else
    {
      _idForEdit = null;
      getView().reset();
    }
  }

  private void setUserForEdit( final Integer id )
  {
    final User user = _entityRepository.findByID( User.class, id );
    if ( null != user )
    {
      final UserViewModel viewModel = new UserViewModel( user );
      getView().setUser( viewModel );
    }
  }

  public void saveUser( final String userName, final String email, final String password )
  {
    if ( null == _idForEdit )
    {
      _userService.addUser( userName, email, password );
    }
    else
    {
      _userService.updateUser( _idForEdit, email, password );
    }
    final PlaceRequest newRequest = new PlaceRequest.Builder().nameToken( NameTokens.ADMIN_USERS ).build();
    _placeManager.revealPlace( newRequest );
  }
}
