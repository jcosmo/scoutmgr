package scoutmgr.client.scoutdetails;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewImpl;

public class ScoutdetailsView
  extends PopupViewImpl
  implements ScoutdetailsPresenter.View
{
  interface Binder
    extends UiBinder<Widget, ScoutdetailsView>
  {
  }

  @Inject
  ScoutdetailsView( final EventBus eventBus, final Binder uiBinder )
  {
    super( eventBus );
    initWidget( uiBinder.createAndBindUi( this ) );
  }
}
