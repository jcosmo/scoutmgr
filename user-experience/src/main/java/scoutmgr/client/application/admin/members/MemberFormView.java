package scoutmgr.client.application.admin.members;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialTextBox;
import java.util.ArrayList;
import javax.inject.Inject;
import org.realityforge.gwt.datatypes.client.date.RDate;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.entity.ScoutSection;
import scoutmgr.client.view.model.ScoutViewModel;

@SuppressWarnings( "Convert2Lambda" )
public class MemberFormView
  extends ViewWithUiHandlers<MemberFormUiHandlers>
  implements MemberFormPresenter.View
{
  @UiField
  MaterialTextBox _familyName;
  @UiField
  MaterialTextBox _givenName;
  @UiField
  MaterialButton _saveButton;
  @UiField
  MaterialButton _cancelButton;
  @UiField
  MaterialDatePicker _dob;
  @UiField
  MaterialTextBox _regNum;
  @UiField
  MaterialListBox _scoutSection;

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
    _dob.setFormat( "dd/mm/yyyy" );
  }

  @Override
  public void reset()
  {
    setupScoutSections();
    _givenName.setValue( "" );
    _familyName.setValue( "" );
    _regNum.setValue( "" );
    _dob.setValue( RDate.toDate( new RDate( 2000, 1, 1 ) ) );
  }

  private void setupScoutSections()
  {
    final ArrayList<ScoutSection> scoutSections = _entityRepository.findAll( ScoutSection.class );

    //Arrays.sort( scoutSections, ScoutSectionComparator.BY_RANK );
    _scoutSection.clear();
    for ( ScoutSection scoutSection : scoutSections )
    {
      _scoutSection.addItem( scoutSection.getCode() );
    }
  }

  @Override
  public void setMember( final ScoutViewModel member )
  {
    setupScoutSections();
    _scoutSection.setSelectedIndex( member.getScoutSection().getRank() - 1 );
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
    getUiHandlers().saveMember( _scoutSection.getSelectedValue(), _regNum.getText(), _givenName.getText(),
                                _familyName.getText(), RDate.fromDate( _dob.getValue() ) );
  }
}
