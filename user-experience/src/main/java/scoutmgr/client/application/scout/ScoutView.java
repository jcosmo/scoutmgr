package scoutmgr.client.application.scout;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class ScoutView
  extends ViewWithUiHandlers<ScoutUiHandlers>
  implements ScoutPresenter.View
{
  interface Binder
    extends UiBinder<Widget, ScoutView>
  {
  }

  @Inject
  ScoutView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }
}
