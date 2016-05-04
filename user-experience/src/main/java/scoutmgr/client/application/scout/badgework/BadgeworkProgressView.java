package scoutmgr.client.application.scout.badgework;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialModal;
import scoutmgr.client.entity.Badge;
import scoutmgr.client.entity.Person;

public class BadgeworkProgressView
  extends ViewImpl
  implements BadgeworkProgressPresenter.View
{
  //private final MaterialModal _modal;
  @UiField
  MaterialButton _closeButton;
  @UiField
  MaterialLabel _title;
  @UiField
  MaterialLabel _description;

  private Person _scout;
  private Badge _badge;

  interface Binder
    extends UiBinder<Widget, BadgeworkProgressView>
  {
  }

  @Inject
  BadgeworkProgressView( Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  protected void onAttach()
  {
    super.onAttach();
    materialModal().openModal();
  }

  private MaterialModal materialModal()
  {
    return (MaterialModal) asWidget();
  }

  @UiHandler( "_closeButton" )
  public void onClose( final ClickEvent e )
  {
    materialModal().closeModal();
    materialModal().removeFromParent();
  }

  @Override
  public void configure( final Person scout, final Badge badge )
  {
    _scout = scout;
    _badge = badge;
    resetUI();
  }

  private void resetUI()
  {
    _title.setText( _badge.getName() );
    _description.setText( _badge.getDescription().replaceAll( "\n", "<br />" ) );
  }
}
