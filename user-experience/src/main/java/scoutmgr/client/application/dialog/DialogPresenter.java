package scoutmgr.client.application.dialog;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class DialogPresenter
  extends PresenterWidget<DialogPresenter.View>
  implements UiHandlers
{
  private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger( DialogPresenter.class.getName() );

  interface View
    extends PopupView, HasUiHandlers<UiHandlers>
  {
  }

  @Inject
  DialogPresenter( final EventBus eventBus,
                   final View view )
  {
    super( eventBus, view );
    getView().setUiHandlers( this );
  }

  @Override
  public void okClicked()
  {
    removeFromPopupSlot( this );
  }

  @Override
  public void cancelClicked()
  {
    removeFromPopupSlot( this );
  }
}
