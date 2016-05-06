package scoutmgr.client.application.scout.badgework;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import gwt.material.design.client.constants.CheckBoxType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import gwt.material.design.client.ui.MaterialRow;
import java.util.Date;
import java.util.List;
import scoutmgr.client.entity.Badge;
import scoutmgr.client.entity.BadgeTask;
import scoutmgr.client.entity.BadgeTaskGroup;
import scoutmgr.client.entity.Person;
import scoutmgr.client.resource.ScoutmgrResourceBundle;

public class BadgeworkProgressView
  extends ViewImpl
  implements BadgeworkProgressPresenter.View
{
  //private final MaterialModal _modal;
  @UiField
  MaterialButton _cancelButton;
  @UiField
  MaterialLabel _title;
  @UiField
  MaterialLabel _description;
  @UiField
  MaterialModalContent _content;
  @UiField
  ScoutmgrResourceBundle _bundle;
  @UiField
  MaterialButton _saveButton;
  @UiField
  HTMLPanel _extraRows;

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

  @UiHandler( "_cancelButton" )
  public void onCancel( final ClickEvent e )
  {
    materialModal().closeModal();
    materialModal().removeFromParent();
  }

  @UiHandler( "_saveButton" )
  public void onSavel( final ClickEvent e )
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
    _extraRows.clear();

    final List<BadgeTaskGroup> badgeTaskGroups = _badge.getBadgeTaskGroups();
    int x = 1;
    for ( final BadgeTaskGroup badgeTaskGroup : badgeTaskGroups )
    {

      if ( badgeTaskGroup.getBadgeTasks().isEmpty() )
      {
        final String description = "" + x + ": " + badgeTaskGroup.getDescription();
        final MaterialRow row = createTargetRow( description );
        row.addStyleName( _bundle.scoutmgr().badgeTaskCategoryRow() );
        _extraRows.add( row );
      }
      else
      {
        final MaterialRow row = createHeaderRow( "" + x + ": " + badgeTaskGroup.getDescription() );
        row.addStyleName( _bundle.scoutmgr().badgeTaskCategoryRow() );
        _extraRows.add( row );

        char y = 'a';
        for ( final BadgeTask badgeTask : badgeTaskGroup.getBadgeTasks() )
        {
          final String description = "" + y + ": " + badgeTask.getDescription();
          final MaterialRow badgeTaskRow = createTargetRow( description );
          badgeTaskRow.addStyleName( _bundle.scoutmgr().badgeTaskRow() );
          _extraRows.add( badgeTaskRow );
          y++;
        }
      }
      x++;
    }
  }

  private MaterialRow createTargetRow( final String description )
  {
    final MaterialRow row = new MaterialRow();
    final MaterialColumn titleColumn = new MaterialColumn();
    titleColumn.addStyleName( _bundle.scoutmgr().titleColumn() );
    titleColumn.setGrid( "s6" );
    titleColumn.add( new MaterialLabel( description ) );
    row.add( titleColumn );

    final MaterialColumn doneColumn = new MaterialColumn();
    doneColumn.setGrid( "s1" );
    final MaterialCheckBox checkBox = new MaterialCheckBox();
    checkBox.setType( CheckBoxType.INTERMEDIATE );
    checkBox.addStyleName( _bundle.scoutmgr().checkboxDone() );
    doneColumn.add( checkBox );
    row.add( doneColumn );

    final MaterialColumn whenColumn = new MaterialColumn();
    whenColumn.addStyleName( _bundle.scoutmgr().whenColumn() );
    whenColumn.setGrid( "s2" );
    final MaterialDatePicker when = new MaterialDatePicker();
    when.setEnabled( false );
    whenColumn.add( when );
    row.add( whenColumn );

    final MaterialColumn whoColumn = new MaterialColumn();
    whoColumn.setGrid( "s3" );
    final MaterialLabel who = new MaterialLabel( );
    who.setTruncate( true );
    whoColumn.add( who );
    row.add( whoColumn );

    checkBox.addClickHandler( ( e ) -> {
      when.setEnabled( checkBox.getValue() );
      if ( checkBox.getValue() )
      {
        when.setDate( new Date() );
        who.setText( "Leader Person" );
      }
      else
      {
        when.clear();
        who.setText( null );
      }
    } );

    return row;
  }

  private MaterialRow createHeaderRow( final String description )
  {
    final MaterialRow row = new MaterialRow();
    final MaterialColumn titleColumn = new MaterialColumn();
    titleColumn.setGrid( "s12" );
    titleColumn.add( new MaterialLabel( description ) );
    row.add( titleColumn );
    return row;
  }
}
