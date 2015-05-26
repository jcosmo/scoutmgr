package scoutmgr.client.navbar;

import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.resource.ScoutmgrResourceBundle;

public class NavbarView
  extends ViewWithUiHandlers<NavbarUiHandlers>
  implements NavbarPresenter.View
{
  @UiField
  Anchor _eventsLink;

  @UiField
  Anchor _membersLink;

  @UiField
  LIElement _eventsLinkContainer;

  @UiField
  LIElement _membersLinkContainer;

  @UiField
  HTML _navToggle;

  @UiField
  DivElement _navCollapse;

  @UiField
  ScoutmgrResourceBundle _bundle;

  interface Binder
    extends UiBinder<Widget, NavbarView>
  {
  }

  @Inject
  NavbarView( final Binder uiBinder)
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  public void setMenuItemActive( final String nameToken )
  {
    switch (nameToken )
    {
      case NameTokens.EVENTS:
        _eventsLinkContainer.addClassName( _bundle.bootstrap().active() );
        _membersLinkContainer.removeClassName( _bundle.bootstrap().active() );
        break;

      case NameTokens.MEMBERS:
        _eventsLinkContainer.removeClassName( _bundle.bootstrap().active() );
        _membersLinkContainer.addClassName( _bundle.bootstrap().active() );
        break;
    }
  }

  @UiHandler( "_membersLink" )
  @SuppressWarnings( "unused" )
  void onMembersClicked( final ClickEvent event )
  {
    getUiHandlers().gotoMembers();
  }

  @UiHandler( "_eventsLink" )
  @SuppressWarnings( "unused" )
  void onEventsClicked( final ClickEvent event )
  {
    getUiHandlers().gotoEvents();
  }

  @UiHandler( "_navToggle" )
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
