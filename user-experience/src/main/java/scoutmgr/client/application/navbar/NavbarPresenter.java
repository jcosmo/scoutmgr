package scoutmgr.client.application.navbar;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import javax.inject.Inject;
import scoutmgr.client.entity.security.User;
import scoutmgr.client.event.security.UserLoadedEvent;
import scoutmgr.client.event.security.UserLoggedOutEvent;
import scoutmgr.client.ioc.FrontendContext;
import scoutmgr.client.security.PermissionUtil;

public class NavbarPresenter
  extends PresenterWidget<NavbarPresenter.View>
  implements NavigationHandler
{
  interface View
    extends com.gwtplatform.mvp.client.View
  {
    void setMenuItemActive( PlaceRequest nameToken );

    void disableAllAccess( final boolean disableLogout );

    void enablePersonalRecordAccess( boolean b );

    void enableSiteAdminFunctionality( boolean b );

    void enableUserManagement( boolean b );
  }

  @Inject
  private PlaceManager _placeManager;

  @Inject
  private FrontendContext _frontendContext;

  @Inject
  NavbarPresenter( final EventBus eventBus,
                   final View view )
  {
    super( eventBus, view );
    eventBus.addHandler( UserLoadedEvent.TYPE, event -> configureMenus() );
    eventBus.addHandler( UserLoggedOutEvent.TYPE, event -> configureMenus() );
  }

  private void configureMenus()
  {
    if ( !_frontendContext.isLoggedIn() )
    {
      getView().disableAllAccess( true );
    }
    else
    {
      final User user = _frontendContext.getUser();
      getView().disableAllAccess( false );
      getView().enablePersonalRecordAccess( null != user.getPerson() );
      getView().enableSiteAdminFunctionality( PermissionUtil.isSiteAdmin( user ) );
      getView().enableUserManagement( PermissionUtil.isUserAdmin( user ) );
    }
  }

  @Override
  protected void onBind()
  {
    super.onBind();
    addRegisteredHandler( NavigationEvent.getType(), this );
    configureMenus();
    getView().setMenuItemActive( _placeManager.getCurrentPlaceRequest() );
  }

  @Override
  public void onNavigation( final NavigationEvent navigationEvent )
  {
    getView().setMenuItemActive( navigationEvent.getRequest() );
  }
}
