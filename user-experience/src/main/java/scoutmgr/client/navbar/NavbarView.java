package scoutmgr.client.navbar;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import gwt.material.design.client.ui.MaterialLink;
import scoutmgr.client.place.NameTokens;

public class NavbarView
  extends ViewWithUiHandlers<NavbarUiHandlers>
  implements NavbarPresenter.View
{
  @UiField
  Anchor _eventsLink;

  @UiField
  Anchor _membersLink;

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
    switch (nameToken )
    {
      case NameTokens.EVENTS:
        break;

      case NameTokens.MEMBERS:
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
