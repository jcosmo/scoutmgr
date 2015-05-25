package scoutmgr.client.navbar;

import com.google.gwt.dom.client.LIElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
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

  ScoutmgrResourceBundle _resources;

  interface Binder
    extends UiBinder<Widget, NavbarView>
  {
  }

  @Inject
  NavbarView( final Binder uiBinder,
              final ScoutmgrResourceBundle resources )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
    _resources = resources;
  }

  @Override
  public void setMenuItemActive( final String nameToken )
  {
    switch (nameToken )
    {
      case NameTokens.EVENTS:
        _eventsLinkContainer.addClassName( _resources.bootstrap().active() );
        _membersLinkContainer.removeClassName( _resources.bootstrap().active() );
        break;

      case NameTokens.MEMBERS:
        _eventsLinkContainer.removeClassName( _resources.bootstrap().active() );
        _membersLinkContainer.addClassName( _resources.bootstrap().active() );
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
}
