package scoutmgr.client.application.dialog;

import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import javax.inject.Inject;
import scoutmgr.client.resource.ScoutmgrResourceBundle;

public class DialogView
  extends PopupViewWithUiHandlers<UiHandlers>
  implements DialogPresenter.View
{
  interface Binder
    extends UiBinder<Widget, DialogView>
  {
  }

  @UiField
  SpanElement _caption;
  @UiField
  HTMLPanel _buttonPanel;
  @UiField
  ScoutmgrResourceBundle _bundle;
  @UiField
  Button _closeButton;
  @UiField
  HTMLPanel _contentPanel;
  @UiField
  HTMLPanel _headerWidgetPanel;
  @UiField
  HTMLPanel _actionsPanel;

  @Inject
  DialogView( final EventBus eventBus, final Binder uiBinder )
  {
    super( eventBus );
    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  public void addButton( final Button button )
  {
    _buttonPanel.setVisible( true );
    button.addStyleName( _bundle.bootstrap().btn() );
    _buttonPanel.add( button );
  }

  @Override
  public void addAction( final Widget actionWidget )
  {
    _actionsPanel.add( actionWidget );
  }

  @Override
  public void addHeaderWidget( final Widget widget )
  {
    _headerWidgetPanel.add( widget );
  }

  @Override
  public void setCaption( final String caption )
  {
    _caption.setInnerText( caption );
  }

  @Override
  public void reset()
  {
    _buttonPanel.clear();
    _buttonPanel.setVisible( false );
    _headerWidgetPanel.clear();
    _actionsPanel.clear();
    _contentPanel.clear();
    _caption.setInnerText( "" );
  }

  @UiHandler( "_closeButton" )
  public void handleClick( final ClickEvent event )
  {
    getUiHandlers().closeClicked();
  }

  public void setContents( final Widget widget )
  {
    _contentPanel.clear();
    _contentPanel.add( widget );
  }

  public void setContents( final String message )
  {
    _contentPanel.clear();
    _contentPanel.add( new HTMLPanel( SafeHtmlUtils.htmlEscape( message ) ) );
  }
}
