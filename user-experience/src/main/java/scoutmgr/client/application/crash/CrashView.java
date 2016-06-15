package scoutmgr.client.application.crash;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import javax.inject.Inject;
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
