package scoutmgr.client.application.scout.badgework;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class BadgeworkPresenter
  extends PresenterWidget<BadgeworkPresenter.View>
  implements BadgeworkUiHandlers
{
  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<BadgeworkUiHandlers>
  {
  }

  @Inject
  BadgeworkPresenter( final EventBus eventBus,
                      final View view )
  {
    super( eventBus, view );
    getView().setUiHandlers( this );
  }
}
