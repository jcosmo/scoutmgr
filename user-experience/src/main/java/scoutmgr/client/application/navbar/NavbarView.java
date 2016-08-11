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
import gwt.material.design.client.ui.MaterialLink;
import javax.inject.Inject;
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

  private MaterialLink _currentLink;

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

      case NameTokens.SCOUT:
        final String scoutID = placeRequest.getParameter( "id", "" );
        if ( _frontendContext.isLoggedIn() && scoutID.equals( _frontendContext.getLoggedInUserID().toString() ) )
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
  public void disableAllAccess()
  {
    _adminMenuContainer.setVisible( false );
    _myRecordMenuContainer.setVisible( false );
  }

  @Override
  public void enablePersonalRecordAccess( final boolean b )
  {
    _myRecordMenuContainer.setVisible( b );
  }

  @Override
  public void enableSiteAdminFunctionality( final boolean b )
  {
    _adminMenuContainer.setVisible( b );
  }

  @UiHandler( "_logoutLink" )
  public void onLogout( final ClickEvent e )
  {
    _frontendContext.logout();
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
