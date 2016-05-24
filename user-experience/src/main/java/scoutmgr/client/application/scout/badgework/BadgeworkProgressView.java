package scoutmgr.client.application.scout.badgework;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
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
import java.util.HashMap;
import org.realityforge.gwt.datatypes.client.date.RDate;
import scoutmgr.client.entity.Badge;
import scoutmgr.client.entity.BadgeTask;
import scoutmgr.client.resource.ScoutmgrResourceBundle;
import scoutmgr.client.view.model.ScoutViewModel;
import scoutmgr.client.view.model.TaskCompletionViewModel;

public class BadgeworkProgressView
  extends ViewWithUiHandlers<BadgeworkProgressUiHandlers>
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

  private HashMap<BadgeTask, RowViewModel> _rowCache = new HashMap<>();

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
    getUiHandlers().onCancel();
  }

  @UiHandler( "_saveButton" )
  @SuppressWarnings( "UnusedParameters" )
  public void onSave( final ClickEvent e )
  {
    getUiHandlers().onSave( _scout );
  }

  @Override
  public void close()
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
    _rowCache.clear();
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
        final TaskCompletionViewModel completionRecord = _scout.getCompletionRecord( badgeTask );
        final RowViewModel row = createTargetRow( description, completionRecord,
                                                  new BadgeTaskCompleter( badgeTask ) );
        row.getRow().addStyleName( _bundle.scoutmgr().badgeTaskCategoryRow() );
        _rowCache.put( badgeTask, row );
        _extraRows.add( row.getRow() );
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
          final TaskCompletionViewModel completionRecord = _scout.getCompletionRecord( childTask );
          final RowViewModel childRow = createTargetRow( description, completionRecord,
                                                         new BadgeTaskCompleter( childTask ) );
          childRow.getRow().addStyleName( _bundle.scoutmgr().badgeTaskRow() );
          _rowCache.put( childTask, childRow );
          _extraRows.add( childRow.getRow() );
          y++;
        }
      }
      x++;
    }
  }

  private RowViewModel createTargetRow( final String description,
                                       final TaskCompletionViewModel completionRecord,
                                       final BadgeCompleter completer )
  {
    final boolean isCompleted = null != completionRecord;
    final RDate dateCompleted = isCompleted ? completionRecord.getDateCompleted() : null;
    final String signedBy = "Some Leader";

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
    when.addValueChangeHandler( e -> completer.updateWhen( when.getDate()) );
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
      completer.changeState( checkBox.getValue() );
    } );

    return new RowViewModel(row, checkBox, when, who );
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

    void updateWhen( Date date );
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
      final TaskCompletionViewModel completion;
      if ( toState )
      {
        completion = _scout.addCompletionRecord( _badgeTask );
        updateRow( _rowCache.get( _badgeTask ), completion );
      }
      else
      {
        _scout.removeCompletionRecord( _badgeTask );
        updateRow( _rowCache.get( _badgeTask ), null );
      }
      return true;
    }

    @Override
    public void updateWhen( final Date date )
    {
      _scout.getCompletionRecord( _badgeTask ).setDateCompleted( date );
    }
  }

  private void updateRow( final RowViewModel rowVm, final TaskCompletionViewModel completion )
  {
    final boolean isCompleted = null != completion;
    final RDate dateCompleted = isCompleted ? completion.getDateCompleted() : null;
    final String signedBy = "Some Leader";

    rowVm.getCheckBox().setValue( isCompleted );
    if ( isCompleted )
    {
      rowVm.getWhen().setValue( RDate.toDate( dateCompleted ) );
      rowVm.getWhen().setEnabled( true );
      rowVm.getWho().setText( signedBy );
    }
    else
    {
      rowVm.getWhen().clear();
      rowVm.getWhen().setEnabled( false );
      rowVm.getWho().setText( null );
    }
  }

  private class RowViewModel
  {
    private MaterialRow _row;
    private final MaterialCheckBox _checkBox;
    private final MaterialDatePicker _when;
    private final MaterialLabel _who;

    public RowViewModel( final MaterialRow row,
                         final MaterialCheckBox checkBox,
                         final MaterialDatePicker when,
                         final MaterialLabel who )
    {

      _row = row;
      _checkBox = checkBox;
      _when = when;
      _who = who;
    }

    public MaterialRow getRow()
    {
      return _row;
    }

    public MaterialCheckBox getCheckBox()
    {
      return _checkBox;
    }

    public MaterialDatePicker getWhen()
    {
      return _when;
    }

    public MaterialLabel getWho()
    {
      return _who;
    }
  }
}
