package scoutmgr.client.application.scout.badgework;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
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
import java.util.List;
import scoutmgr.client.entity.Badge;
import scoutmgr.client.entity.BadgeTask;
import scoutmgr.client.entity.BadgeTaskGroup;
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
  @UiField
  MaterialModalContent _content;

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

    final List<BadgeTaskGroup> badgeTaskGroups = _badge.getBadgeTaskGroups();
    int x = 1;
    for ( final BadgeTaskGroup badgeTaskGroup : badgeTaskGroups )
    {

      if ( badgeTaskGroup.getBadgeTasks().isEmpty() )
      {
        final String description = "" + x + ": " + badgeTaskGroup.getDescription();
        _content.add( createTargetRow( description ) );
      }
      else
      {
        _content.add( createHeaderRow( "" + x + ": " + badgeTaskGroup.getDescription() ) );
        char y = 'a';
        for ( final BadgeTask badgeTask : badgeTaskGroup.getBadgeTasks() )
        {
          final String description = "" + y + ": " + badgeTask.getDescription();
          _content.add( createTargetRow( description ) );
          y++;
        }
      }
      x++;
    }
  }

  private MaterialRow createTargetRow( final String description )
  {
    final MaterialRow row = new MaterialRow();
    final MaterialColumn titleColumn = new MaterialColumn(  );
    titleColumn.setGrid( "s7" );
    titleColumn.add( new MaterialLabel( description ) );
    row.add( titleColumn );

    final MaterialColumn doneColumn = new MaterialColumn(  );
    doneColumn.setGrid( "s1" );
    final MaterialCheckBox checkBox = new MaterialCheckBox(null, CheckBoxType.FILLED);
    doneColumn.add( checkBox );
    row.add( doneColumn );

    final MaterialColumn whenColumn = new MaterialColumn(  );
    whenColumn.setGrid( "s2" );
    final MaterialDatePicker when = new MaterialDatePicker();
    whenColumn.add( when );
    row.add( whenColumn );

    final MaterialColumn whoColumn = new MaterialColumn(  );
    whoColumn.setGrid( "s2" );
    final MaterialLabel who = new MaterialLabel( "The Leader" );
    whoColumn.add( who );
    row.add( whoColumn );
    return row;
  }

  private MaterialRow createHeaderRow( final String description )
  {
    final MaterialRow row = new MaterialRow();
    final MaterialColumn titleColumn = new MaterialColumn(  );
    titleColumn.setGrid( "s12" );
    titleColumn.add( new MaterialLabel( description ) );
    row.add( titleColumn );
    return row;
  }
}
