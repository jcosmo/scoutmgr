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
import org.realityforge.gwt.datatypes.client.date.RDate;
import scoutmgr.client.entity.Badge;
import scoutmgr.client.entity.BadgeTask;
import scoutmgr.client.resource.ScoutmgrResourceBundle;
import scoutmgr.client.view.model.ScoutViewModel;
import scoutmgr.client.view.model.TaskCompletionViewModel;

public class BadgeworkProgressView
  extends ViewImpl
  implements BadgeworkProgressPresenter.View
{
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

  private ScoutViewModel _scout;
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
  @SuppressWarnings( "UnusedParameters" )
  public void onCancel( final ClickEvent e )
  {
    materialModal().closeModal();
    materialModal().removeFromParent();
  }

  @UiHandler( "_saveButton" )
  @SuppressWarnings( "UnusedParameters" )
  public void onSave( final ClickEvent e )
  {
    materialModal().closeModal();
    materialModal().removeFromParent();
  }

  @Override
  public void configure( final ScoutViewModel scout, final Badge badge )
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

    int x = 1;
    for ( final BadgeTask badgeTask : _badge.getBadgeTasks() )
    {
      if ( badgeTask.getParent() != null )
      {
        continue;
      }
      if ( badgeTask.getBadgeTasks().isEmpty() )
      {
        final String description = "" + x + ": " + badgeTask.getDescription();
        final MaterialRow row = createTargetRow( badgeTask,
                                                 description,
                                                 _scout.getCompletionRecord( badgeTask ) );
        row.addStyleName( _bundle.scoutmgr().badgeTaskCategoryRow() );
        _extraRows.add( row );
      }
      else
      {
        final MaterialRow row = createHeaderRow( "" + x + ": " + badgeTask.getDescription() );
        row.addStyleName( _bundle.scoutmgr().badgeTaskCategoryRow() );
        _extraRows.add( row );

        char y = 'a';
        for ( final BadgeTask childTask : badgeTask.getBadgeTasks() )
        {
          final String description = "" + y + ": " + childTask.getDescription();
          final MaterialRow badgeTaskRow = createTargetRow( childTask,
                                                            description,
                                                            _scout.getCompletionRecord( childTask ) );
          badgeTaskRow.addStyleName( _bundle.scoutmgr().badgeTaskRow() );
          _extraRows.add( badgeTaskRow );
          y++;
        }
      }
      x++;
    }
  }

  private MaterialRow createTargetRow( final BadgeTask badgeTask,
                                       final String description,
                                       final TaskCompletionViewModel completionRecord )
  {
    final boolean isCompleted = null != completionRecord;
    final RDate dateCompleted = isCompleted ? completionRecord.getDateCompleted() : null;
    return createTargetRow( description, isCompleted, dateCompleted, "Leader Person",
                            new BadgeTaskCompleter( badgeTask ) );
  }

  private MaterialRow createTargetRow( final String description,
                                       final boolean isCompleted,
                                       final RDate dateCompleted,
                                       final String signedBy,
                                       final BadgeCompleter completer )
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
    checkBox.setValue( isCompleted );
    checkBox.addStyleName( _bundle.scoutmgr().checkboxDone() );
    doneColumn.add( checkBox );
    row.add( doneColumn );

    final MaterialColumn whenColumn = new MaterialColumn();
    whenColumn.addStyleName( _bundle.scoutmgr().whenColumn() );
    whenColumn.setGrid( "s2" );
    final MaterialDatePicker when = new MaterialDatePicker();
    when.setEnabled( isCompleted );
    if ( isCompleted )
    {
      when.setValue( RDate.toDate( dateCompleted ) );
    }
    whenColumn.add( when );
    row.add( whenColumn );

    final MaterialColumn whoColumn = new MaterialColumn();
    whoColumn.setGrid( "s3" );
    final MaterialLabel who = new MaterialLabel();
    who.setTruncate( true );
    if ( isCompleted )
    {
      who.setText( signedBy );
    }
    whoColumn.add( who );
    row.add( whoColumn );

    checkBox.addClickHandler( ( e ) -> {
      if ( !completer.changeState( checkBox.getValue() ) )
      {
        checkBox.setValue( !checkBox.getValue() );
        return;
      }
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

  private interface BadgeCompleter
  {
    boolean changeState( boolean toState );
  }

  private class BadgeTaskCompleter
    implements BadgeCompleter
  {
    final private BadgeTask _badgeTask;

    private BadgeTaskCompleter( final BadgeTask badgeTask )
    {
      _badgeTask = badgeTask;
    }

    @Override
    public boolean changeState( final boolean toState )
    {
      if ( toState )
      {
        _scout.addCompletionRecord( _badgeTask );
      }
      //else
      //{
      //  _scout.removeCompletionRecord( _badgeTask );
      //}
      return true;
    }
  }
}
