package scoutmgr.client.application.footer;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class FooterView
  extends ViewImpl
  implements FooterPresenter.View
{
  interface Binder
    extends UiBinder<Widget, FooterView>
  {
  }

  @Inject
  FooterView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }
}
