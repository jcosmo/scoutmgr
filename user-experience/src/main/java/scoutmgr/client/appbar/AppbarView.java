package scoutmgr.client.appbar;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class AppbarView
  extends ViewWithUiHandlers<AppbarUiHandlers>
  implements AppbarPresenter.View
{
  interface Binder
    extends UiBinder<Widget, AppbarView>
  {
  }

  @Inject
  AppbarView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }
}
