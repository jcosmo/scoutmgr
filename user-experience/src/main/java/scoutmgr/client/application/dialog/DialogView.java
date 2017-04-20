package scoutmgr.client.application.dialog;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.HeadingSize;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import gwt.material.design.client.ui.MaterialModalFooter;
import gwt.material.design.client.ui.html.Heading;
import gwt.material.design.client.ui.html.Paragraph;
import javax.inject.Inject;
import scoutmgr.client.resource.ScoutmgrResourceBundle;

public class DialogView
  extends ViewImpl
  implements DialogPresenter.View
{
  interface Binder
    extends UiBinder<Widget, DialogView>
  {
  }

  @UiField
  MaterialModalFooter _buttonPanel;
  @UiField
  ScoutmgrResourceBundle _bundle;
  @UiField
  MaterialModalContent _contentPanel;

  private final Heading _title;

  @Inject
  DialogView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
    _title = new Heading( HeadingSize.H4 );
    _title.setFontWeight( 300 );
    _contentPanel.add( _title );
  }

  private MaterialModal materialModal()
  {
    return (MaterialModal) asWidget();
  }

  @Override
  protected void onAttach()
  {
    super.onAttach();
    materialModal().openModal();
  }

  @Override
  public void close()
  {
    materialModal().closeModal();
    materialModal().removeFromParent();
  }

  @Override
  public void setTitle( final String title )
  {
    _title.getElement().setInnerHTML( title );
  }

  @Override
  public void reset()
  {
    _buttonPanel.clear();
    _contentPanel.clear();
    _contentPanel.add( _title );
  }

  public void addContent( final String message )
  {
    _contentPanel.add( new Paragraph( message ) );
  }

  @Override
  public void addButton( final String text, final ClickHandler handler )
  {
    final MaterialButton button = new MaterialButton();
    button.setType( ButtonType.FLAT );
    button.setText( text );
    button.addClickHandler( handler );
    _buttonPanel.add( button );
  }
}
