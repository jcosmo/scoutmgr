package scoutmgr.client.application.navbar;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavSection;
import javax.inject.Inject;
import scoutmgr.client.ScoutmgrApp;
import scoutmgr.client.entity.Person;
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
  @UiField
  MaterialColumn _adminMenuContainer;
  @UiField
  MaterialColumn _myRecordMenuContainer;
  @UiField
  MaterialLink _myRecordLink;
  @UiField
  MaterialLink _adminScoutLink;
  @UiField
  MaterialLink _adminBadgesLink;
  @UiField
  MaterialLink _adminEventsLink;
  @UiField
  MaterialLink _adminUsersLink;
  @UiField
  MaterialColumn _myTroopMenuContainer;
  @UiField
  MaterialLink _myTroopLink;
  @UiField
  MaterialDropDown _adminDropdown;
  @UiField
  MaterialNavSection _accountSection;

  private MaterialLink _currentLink;

  @Inject
  private ScoutmgrApp _app;

  @Inject
  private FrontendContext _frontendContext;

  @Inject
  private PlaceManager _placeManager;

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
  public void setMenuItemActive( final PlaceRequest placeRequest )
  {
    final MaterialLink newLink;
    switch ( placeRequest.getNameToken() )
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

      case NameTokens.TROOP:
        newLink = _myTroopLink;
        break;

      case NameTokens.SCOUT:
        final String scoutID = placeRequest.getParameter( "id", "" );
        if ( _app.isLoggedIn() && scoutID.equals( _app.getLoggedInUserID().toString() ) )
        {
          newLink = _myRecordLink;
        }
        else
        {
          newLink = _adminLink;
        }
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

  @Override
  public void disableAllAccess( final boolean disableLogout )
  {
    _accountSection.setVisible( !disableLogout );
    enableSiteAdminFunctionality( false);
    _myRecordMenuContainer.setVisible( false );
    _myTroopMenuContainer.setVisible( false );
  }

  @Override
  public void enablePersonalRecordAccess( final boolean b )
  {
    _myRecordMenuContainer.setVisible( b );
    _myTroopMenuContainer.setVisible( b );
  }

  @Override
  public void enableSiteAdminFunctionality( final boolean b )
  {
    _adminMenuContainer.setVisible( b );
    if ( b )
    {
      ensureAdminDropdown();
      addIfNotPresent( _adminDropdown, _adminScoutLink );
      addIfNotPresent( _adminDropdown, _adminEventsLink );
      addIfNotPresent( _adminDropdown, _adminUsersLink );
      addIfNotPresent( _adminDropdown, _adminBadgesLink );
    }
    else if ( _adminDropdown != null )
    {
      _adminMenuContainer.remove( _adminDropdown );
      _adminDropdown = null;
    }
  }

  private void ensureAdminDropdown()
  {
    if ( null == _adminDropdown)
    {
      _adminDropdown = new MaterialDropDown( "menu-admin" );
      _adminDropdown.setBelowOrigin( true );
      _adminMenuContainer.add( _adminDropdown );
    }
  }

  private void addIfNotPresent( final MaterialDropDown dropdown, final MaterialLink link )
  {
    if ( !dropdown.getItems().contains( link ) )
    {
      dropdown.add( link );
    }
  }

  @Override
  public void enableUserManagement( final boolean b )
  {
    if ( b )
    {
      ensureAdminDropdown();
      addIfNotPresent( _adminDropdown, _adminUsersLink );
      _adminMenuContainer.setVisible( true );
    }
  }

  @UiHandler( "_logoutLink" )
  public void onLogout( final ClickEvent e )
  {
    _app.logout();
  }

  @UiHandler( "_myRecordLink" )
  public void onMyRecord( final ClickEvent e )
  {
    final Person person = _frontendContext.getPerson();
    if ( null != person )
    {
      final PlaceRequest request = _placeManager.getCurrentPlaceRequest();
      final PlaceRequest newPlace = new PlaceRequest.Builder( request ).nameToken( NameTokens.getScout() )
        .with( "id", person.getID().toString() ).build();
      _placeManager.revealPlace( newPlace );
    }
  }
}
