package scoutmgr.client.application.navbar;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import gwt.material.design.client.ui.MaterialLink;
import javax.inject.Inject;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.resource.ScoutmgrResourceBundle;

public class NavbarView
  extends ViewImpl
  implements NavbarPresenter.View
{
  @UiField
  ScoutmgrResourceBundle _bundle;
  @UiField
  MaterialLink _adminScoutsLink;
  @UiField
  MaterialLink _adminBadgesLink;
  @UiField
  MaterialLink _adminEventsLink;

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
        newLink = _adminEventsLink;
        break;

      case NameTokens.ADMIN_SCOUTS:
      case NameTokens.ADMIN_SCOUT:
      case NameTokens.ADMIN_NEW_SCOUT:
      case NameTokens.SCOUT:
        newLink = _adminScoutsLink;
        break;

      case NameTokens.ADMIN_BADGES_LEVEL:
      case NameTokens.ADMIN_BADGE:
      case NameTokens.ADMIN_BADGES:
      case NameTokens.ADMIN_NEW_BADGE:
        newLink = _adminBadgesLink;
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
}
