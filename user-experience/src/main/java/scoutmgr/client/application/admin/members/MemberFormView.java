package scoutmgr.client.application.admin.members;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import org.realityforge.gwt.datatypes.client.date.RDate;
import org.realityforge.replicant.client.EntityRepository;
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
  @UiField
  DateBox _dob;
  @UiField
  TextBox _regNum;
  @UiField
  ListBox _scoutLevel;

  @Inject
  EntityRepository _entityRepository;

  interface Binder
    extends UiBinder<Widget, MemberFormView>
  {
  }

  @Inject
  MemberFormView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
    final DateTimeFormat format = DateTimeFormat.getFormat( "dd/MM/yyyy" );
    _dob.setFormat( new DateBox.DefaultFormat( format ) );
  }

  @Override
  public void reset()
  {
    //final ArrayList<ScoutLevel> scoutLevels = _entityRepository.findAll( ScoutLevel.class );
    //Arrays.sort( scoutLevels, ScoutLevelComparator.BY_RANK );
    _scoutLevel.clear();
    /*
    for ( ScoutLevel scoutLevel : scoutLevels )
    {
      _scoutLevel.addItem( scoutLevel.getCode() );
    }
    */
    _givenName.setValue( "" );
    _familyName.setValue( "" );
    _regNum.setValue( "" );
    _dob.setValue( RDate.toDate( new RDate( 2000, 1, 1 ) ) );
  }

  @Override
  public void setMember( final ScoutViewModel member )
  {
    _scoutLevel.setSelectedIndex( member.getScoutLevel().getRank() - 1 );
    _regNum.setValue( member.getRegistrationNumber() );
    _givenName.setValue( member.getFirstName() );
    _familyName.setValue( member.getLastName() );
    _dob.setValue( RDate.toDate( member.getDob() ) );
  }

  @UiHandler( "_saveButton" )
  public void onSave( final ClickEvent event )
  {
    event.preventDefault();
    event.stopPropagation();
    getUiHandlers().saveMember( _scoutLevel.getSelectedValue(), _regNum.getText(), _givenName.getText(),
                                _familyName.getText(), RDate.fromDate( _dob.getValue() ) );
  }
}