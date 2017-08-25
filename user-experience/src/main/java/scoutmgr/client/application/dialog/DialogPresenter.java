package scoutmgr.client.application.dialog;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import java.util.Arrays;
import javax.inject.Inject;
import scoutmgr.client.application.ApplicationPresenter;

public class DialogPresenter
  extends PresenterWidget<DialogPresenter.View>
{
  interface View
    extends com.gwtplatform.mvp.client.View
  {
    void setTitle( String caption );

    void reset();

    void addContent( String message );

    void addButton( String text, ClickHandler handler );

    void close();
  }

  @Inject
  private ApplicationPresenter _applicationPresenter;

  @Inject
  DialogPresenter( final EventBus eventBus,
                   final View view )
  {
    super( eventBus, view );
  }

  public void configureConfirmation( final String caption,
                                     final String confirmMessage,
                                     final ClickHandler confirmationHandler,
                                     final String cancelText,
                                     final String confirmText )
  {
    getView().reset();
    getView().setTitle( caption );
    Arrays.stream( confirmMessage.split( "\n" ) ).forEach( x -> getView().addContent( x ) );
    getView().addButton( confirmText, confirmationHandler );
    getView().addButton( cancelText, event -> getView().close( ) );
  }

  public void open()
  {
    _applicationPresenter.showDialog( this );
  }

  public void close()
  {
    getView().close();
  }
}
