package scoutmgr.client.application.scoutdetails;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class ScoutdetailsPresenter
  extends PresenterWidget<ScoutdetailsPresenter.View>
{
  interface View
    extends PopupView
  {
  }

  @Inject
  ScoutdetailsPresenter( final EventBus eventBus,
                         final View view )
  {
    super( eventBus, view );
  }
}
