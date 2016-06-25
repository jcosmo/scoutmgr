package scoutmgr.client.application.navbar;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import gwt.material.design.client.ui.MaterialLink;
import javax.inject.Inject;
import scoutmgr.client.ioc.FrontendContext;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.resource.ScoutmgrResourceBundle;

public class NavbarView
  extends ViewImpl
  implements NavbarPresenter.View
{
  @UiField
  ScoutmgrResourceBundle _bundle;
  @UiField
  MaterialLink _adminLink;
  @UiField
  MaterialLink _logoutLink;

  @Inject
  FrontendContext _frontendContext;

  private MaterialLink _currentLink;

  interface Binder
    extends UiBinder<Widget, NavbarView>
  {
  }

  @Inject
  NavbarView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  public void setMenuItemActive( final String nameToken )
  {
    final MaterialLink newLink;
    switch ( nameToken )
    {
      case NameTokens.EVENTS:
      case NameTokens.ADMIN:
      case NameTokens.ADMIN_SCOUTS:
      case NameTokens.ADMIN_SCOUT:
      case NameTokens.ADMIN_NEW_SCOUT:
      case NameTokens.ADMIN_BADGES_LEVEL:
      case NameTokens.ADMIN_BADGE:
      case NameTokens.ADMIN_BADGES:
      case NameTokens.ADMIN_NEW_BADGE:
      case NameTokens.ADMIN_USERS:
      case NameTokens.ADMIN_USER:
      case NameTokens.ADMIN_EDIT_USER:
      case NameTokens.ADMIN_NEW_USER:
        newLink = _adminLink;
        break;

      default:
        newLink = null;
    }

    if ( newLink != _currentLink )
    {
      if ( null != _currentLink )
      {
        _currentLink.removeStyleName( _bundle.scoutmgr().activeNav() );
      }

      if ( null != newLink )
      {
        newLink.addStyleName( _bundle.scoutmgr().activeNav() );
      }

      _currentLink = newLink;
    }
  }

  @UiHandler( "_logoutLink" )
  public void onLogout( final ClickEvent e )
  {
    _frontendContext.logout();
  }
}
