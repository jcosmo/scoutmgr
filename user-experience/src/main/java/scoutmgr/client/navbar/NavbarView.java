package scoutmgr.client.navbar;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import gwt.material.design.client.ui.MaterialLink;

public class NavbarView
  extends ViewWithUiHandlers<NavbarUiHandlers>
  implements NavbarPresenter.View
{
  @UiField
  MaterialLink _eventsLink;

  @UiField
  MaterialLink _membersLink;

  interface Binder
    extends UiBinder<Widget, NavbarView>
  {
  }

  @Inject
  NavbarView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
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
