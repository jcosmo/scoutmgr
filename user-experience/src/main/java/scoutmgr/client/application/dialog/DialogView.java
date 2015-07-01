package scoutmgr.client.application.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class DialogView
  extends PopupViewWithUiHandlers<UiHandlers>
  implements DialogPresenter.View
{
  private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger( DialogView.class.getName() );

  @UiField
  Button _okButton;
  @UiField
  Button _cancelButton;

  interface Binder
    extends UiBinder<Widget, DialogView>
  {
  }

  @Inject
  DialogView( final EventBus eventBus, final Binder uiBinder )
  {
    super( eventBus );
    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @UiHandler( value = "_cancelButton" )
  void cancelClicked( final ClickEvent event )
  {
    getUiHandlers().cancelClicked();
  }

  @UiHandler( value = "_okButton" )
  void okClicked( final ClickEvent event )
  {
    getUiHandlers().okClicked();
  }
}
