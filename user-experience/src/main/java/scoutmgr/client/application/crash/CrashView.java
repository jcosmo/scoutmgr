package scoutmgr.client.application.crash;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import scoutmgr.client.application.events.EventsUiHandlers;

public class CrashView
  extends ViewWithUiHandlers<EventsUiHandlers>
  implements CrashPresenter.View
{
  interface Binder
    extends UiBinder<Widget, CrashView>
  {
  }

  @Inject
  CrashView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }
}
