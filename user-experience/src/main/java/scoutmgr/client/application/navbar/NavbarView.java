package scoutmgr.client.application.navbar;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.resource.ScoutmgrResourceBundle;

public class NavbarView
  extends ViewImpl
  implements NavbarPresenter.View
{
  LIElement _eventsLinkContainer;

  LIElement _membersLinkContainer;

  LIElement _badgesLinkContainer;

  LIElement _currentLink;

  HTML _navToggle;

  DivElement _navCollapse;

  ScoutmgrResourceBundle _bundle;

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
    final LIElement newLink;
    switch ( nameToken )
    {
      case NameTokens.EVENTS:
        newLink = _eventsLinkContainer;
        break;

      case NameTokens.ADMIN_SCOUTS:
      case NameTokens.ADMIN_SCOUT:
      case NameTokens.ADMIN_NEW_SCOUT:
        newLink = _membersLinkContainer;
        break;

      case NameTokens.ADMIN_BADGES_LEVEL:
      case NameTokens.ADMIN_BADGE:
      case NameTokens.ADMIN_BADGES:
      case NameTokens.ADMIN_NEW_BADGE:
        newLink = _badgesLinkContainer;
        break;

      default:
        newLink = null;
    }

    if ( newLink != _currentLink )
    {
      if ( null != _currentLink )
      {
        _currentLink.removeClassName( _bundle.bootstrap().active() );
      }

      if ( null != newLink )
      {
        newLink.addClassName( _bundle.bootstrap().active() );
      }

      _currentLink = newLink;
    }
  }

  @SuppressWarnings( "UnusedParameters" )
  public void handleClick( final ClickEvent event )
  {
    if ( _navCollapse.hasClassName( _bundle.bootstrap().collapse() ) )
    {
      _navCollapse.removeClassName( _bundle.bootstrap().collapse() );
    }
    else
    {
      _navCollapse.addClassName( _bundle.bootstrap().collapse() );
    }
  }
}
