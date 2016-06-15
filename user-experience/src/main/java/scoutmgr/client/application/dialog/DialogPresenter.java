package scoutmgr.client.application.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import javax.inject.Inject;

public class DialogPresenter
  extends PresenterWidget<DialogPresenter.View>
  implements UiHandlers
{
  private static final java.util.logging.Logger LOG =
    java.util.logging.Logger.getLogger( DialogPresenter.class.getName() );

  interface View
    extends PopupView, HasUiHandlers<UiHandlers>
  {
    void addButton( Button button );

    void addAction( Widget actionWidget );

    void addHeaderWidget( Widget widget );

    void setCaption( String caption );

    void reset();

    void setContents( String message );
  }

  @Inject
  DialogPresenter( final EventBus eventBus,
                   final View view )
  {
    super( eventBus, view );
    getView().setUiHandlers( this );
  }

  @Override
  public void closeClicked()
  {
    removeFromPopupSlot( this );
  }

  public void configureConfirmation( final String caption,
                                     final String confirmMessage,
                                     final ClickHandler confirmationHandler )
  {
    getView().reset();
    getView().setCaption( caption );
    getView().setContents( confirmMessage );
    getView().addButton( new Button( "Yes", confirmationHandler ) );
    getView().addButton( new Button( "No", new ClickHandler()
    {
      @Override
      public void onClick( final ClickEvent event )
      {
        removeFromPopupSlot( DialogPresenter.this );
      }
    } ) );
  }
}
