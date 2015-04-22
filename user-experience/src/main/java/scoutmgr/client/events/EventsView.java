package scoutmgr.client.events;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class EventsView
  extends ViewWithUiHandlers<EventsUiHandlers>
  implements EventsPresenter.View
{
  @UiField
  SimplePanel _navbarPanel;
  @UiField
  SimplePanel _eventsPanel;
  @UiField
  SimplePanel _footerPanel;

  interface Binder
    extends UiBinder<Widget, EventsView>
  {
  }

  @Inject
  EventsView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  public void setInSlot( final Object slot, final IsWidget content )
  {
    if ( slot == EventsPresenter.SLOT_MAIN_CONTENT )
    {
      _eventsPanel.setWidget( content );
    }
    else if ( slot == EventsPresenter.SLOT_NAVBAR_CONTENT )
    {
      _navbarPanel.setWidget( content );
    }
    else if ( slot == EventsPresenter.SLOT_FOOTER_CONTENT )
    {
      _footerPanel.setWidget( content );
    }
  }
}
