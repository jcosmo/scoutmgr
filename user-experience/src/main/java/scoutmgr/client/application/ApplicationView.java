package scoutmgr.client.application;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class ApplicationView
  extends ViewWithUiHandlers<ApplicationUiHandlers>
  implements ApplicationPresenter.View
{
  interface Binder
    extends UiBinder<Widget, ApplicationView>
  {
  }

  @Inject
  ApplicationView( Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  public void resetAndFocus()
  {
  }
}
