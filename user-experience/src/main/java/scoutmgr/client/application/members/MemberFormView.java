package scoutmgr.client.application.members;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import scoutmgr.client.view.model.ScoutViewModel;

@SuppressWarnings( "Convert2Lambda" )
public class MemberFormView
  extends ViewWithUiHandlers<MemberFormUiHandlers>
  implements MemberFormPresenter.View
{
  @UiField
  TextBox _familyName;
  @UiField
  TextBox _givenName;
  @UiField
  Anchor _saveButton;
  @UiField
  Anchor _cancelButton;

  interface Binder
    extends UiBinder<Widget, MemberFormView>
  {
  }

  @Inject
  MemberFormView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  public void reset()
  {
    _givenName.setValue( "" );
    _familyName.setValue( "" );
  }

  @Override
  public void setMember( final ScoutViewModel member )
  {
    _givenName.setValue( member.getFirstName() );
    _familyName.setValue( member.getLastName() );
  }

  @UiHandler( "_saveButton" )
  public void onSave( final ClickEvent event )
  {
    event.preventDefault();
    event.stopPropagation();
    getUiHandlers().saveMember( _givenName.getText(), _familyName.getText() );
  }
}
