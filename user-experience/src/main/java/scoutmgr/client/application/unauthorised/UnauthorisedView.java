package scoutmgr.client.application.unauthorised;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import javax.inject.Inject;

public class UnauthorisedView
  extends ViewImpl
  implements UnauthorisedPresenter.View
{
  interface Binder
    extends UiBinder<Widget, UnauthorisedView>
  {
  }

  @Inject
  UnauthorisedView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }
}
