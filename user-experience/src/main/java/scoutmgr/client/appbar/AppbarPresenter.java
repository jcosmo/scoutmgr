package scoutmgr.client.appbar;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class AppbarPresenter
  extends PresenterWidget<AppbarPresenter.View>
  implements AppbarUiHandlers
{
  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<AppbarUiHandlers>
  {
  }

  @Inject
  AppbarPresenter( final EventBus eventBus,
                   final View view )
  {
    super( eventBus, view );

    getView().setUiHandlers( this );
  }
}
