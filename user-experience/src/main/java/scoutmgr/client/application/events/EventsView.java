package scoutmgr.client.application.events;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import javax.inject.Inject;

public class EventsView
  extends ViewWithUiHandlers<EventsUiHandlers>
  implements EventsPresenter.View
{
  @UiField
  SimplePanel _eventsPanel;

  interface Binder
    extends UiBinder<Widget, EventsView>
  {
  }

  @Inject
  EventsView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }
}
