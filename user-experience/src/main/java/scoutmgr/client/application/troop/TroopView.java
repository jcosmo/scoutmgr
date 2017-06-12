package scoutmgr.client.application.troop;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLink;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;
import scoutmgr.client.entity.Badge;
import scoutmgr.client.entity.BadgeCategory;
import scoutmgr.client.entity.BadgeTask;
import scoutmgr.client.entity.ScoutSection;
import scoutmgr.client.resource.ScoutmgrResourceBundle;
import scoutmgr.client.view.model.ScoutViewModel;
import scoutmgr.client.view.model.TaskCompletionViewModel;

public class TroopView
  extends ViewWithUiHandlers<TroopUiHandlers>
  implements TroopPresenter.View
{
  private final ListDataProvider<ScoutViewModel> _provider;
  @UiField( provided = true )
  DataGrid<ScoutViewModel> _scoutsTable;
  @UiField
  SimplePanel _pagerPanel;
  @UiField
  ScoutmgrResourceBundle _bundle;
  @UiField
  MaterialDropDown _groupSelector;

  private ScoutSection _scoutSection;
  private List<BadgeCategory> _badgeCategories;

  /*
    interface DataGridStyle
      extends DataGrid.Style
    {
      String left();

      String headerHighlight();
    }

    interface DataGridResources
      extends DataGrid.Resources
    {
      @Override
      @Source( "TroopDataGrid.gss" )
      TroopView.DataGridStyle dataGridStyle();
    }
  */
  interface Binder
    extends UiBinder<Widget, TroopView>
  {
  }

  @Inject
  TroopView( final Binder uiBinder )
  {
    _scoutsTable = new DataGrid<>( 20 );
    _scoutsTable.setAutoHeaderRefreshDisabled( false );
    _scoutsTable.setEmptyTableWidget( new Label( "No scouts" ) );

    _provider = new ListDataProvider<>();

    final Column<ScoutViewModel, String> nameColumn =
      new Column<ScoutViewModel, String>( new TextCell() )
      {
        @Override
        public String getValue( final ScoutViewModel viewModel )
        {
          return getScoutName( viewModel );
        }
      };
    createColumnSorters( nameColumn );

    _scoutsTable.addColumn( nameColumn, SafeHtmlUtils.fromString( "Name" ) );
    _scoutsTable.setColumnWidth( nameColumn, "200px" );
    final NoSelectionModel<ScoutViewModel> selectionModel = new NoSelectionModel<>();
    _scoutsTable.setSelectionModel( selectionModel );
    _scoutsTable.setKeyboardSelectionPolicy( HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED );

    _provider.addDataDisplay( _scoutsTable );

    initWidget( uiBinder.createAndBindUi( this ) );

    _groupSelector.add( new MaterialLink( "All" ) );
    setupPager();
  }

  private String getScoutName( final ScoutViewModel viewModel )
  {
    return viewModel.getLastName() + ", " + viewModel.getFirstName();
  }

  private void setupPager()
  {
    final SimplePager.Resources pagerResources = GWT.create( SimplePager.Resources.class );
    final SimplePager pager = new SimplePager( SimplePager.TextLocation.CENTER, pagerResources, false, 0, true );
    pager.setDisplay( _scoutsTable );
    _pagerPanel.setWidget( pager );
  }

  private void createColumnSorters( final Column<ScoutViewModel, String> nameColumn )
  {
    final ColumnSortEvent.ListHandler<ScoutViewModel> sortHandler =
      new ColumnSortEvent.ListHandler<>( _provider.getList() );
    _scoutsTable.addColumnSortHandler( sortHandler );

    nameColumn.setSortable( true );
    sortHandler.setComparator( nameColumn, Comparator.comparing( this::getScoutName ) );
  }

  public void setBadgeCategories( final List<BadgeCategory> badgeCategories )
  {
    _badgeCategories = badgeCategories;
    rebuildColumns();
  }

  private void rebuildColumns()
  {
    final int columnCount = _scoutsTable.getColumnCount();
    for ( int c = 1; c < columnCount; c++ )
    {
      _scoutsTable.removeColumn( 1 );
    }
    _badgeCategories.forEach( this::addColumnForBadgeCategory );
  }

  private void addColumnsForBadgesInCategory( final BadgeCategory badgeCategory )
  {
    badgeCategory.getBadges().forEach( this::addColumnForBadge );
  }

  private void addColumnForBadge( final Badge badge )
  {
    final Column<ScoutViewModel, String> column =
      new Column<ScoutViewModel, String>( new TextCell() )
      {
        @Override
        public String getValue( final ScoutViewModel viewModel )
        {
          return percentageAsString( getProgressPercentage( viewModel, badge ) );
        }
      };
    _scoutsTable.addColumn( column, SafeHtmlUtils.fromString( badge.getName() ) );
  }

  private String percentageAsString( final int progress )
  {
    if ( 0 == progress )
    {
      return "";
    }
    if ( 100 == progress )
    {
      return "Done";
    }
    return "" + progress + "%";
  }

  private void addColumnForBadgeCategory( final BadgeCategory category )
  {
    final Column<ScoutViewModel, String> column =
      new Column<ScoutViewModel, String>( new TextCell() )
      {
        @Override
        public String getValue( final ScoutViewModel viewModel )
        {
          return percentageAsString( getProgressPercentage( viewModel, category ) );
        }
      };
    _scoutsTable.addColumn( column, SafeHtmlUtils.fromString( category.getName() ) );
  }

  public void setScouts( final Collection<ScoutViewModel> list )
  {
    _provider.getList().clear();
    if ( null != list )
    {
      _provider.getList().addAll( list );
    }
    ColumnSortEvent.fire( _scoutsTable, _scoutsTable.getColumnSortList() );
  }

  private int getProgressPercentage( final ScoutViewModel scout, final BadgeCategory category )
  {
    int sum = category.getBadges().stream().mapToInt( badge -> getProgressPercentage( scout, badge ) ).sum();
    return (int) ( sum / (float) category.getBadges().size() );
  }

  private int getProgressPercentage( final ScoutViewModel scout, final Badge badge )
  {
    final List<BadgeTask> badgeTasks = badge.getBadgeTasks();
    int taskCount = 0;
    int tasksComplete = 0;
    for ( final BadgeTask badgeTask : badgeTasks )
    {
      if ( badgeTask.getParent() != null )
      {
        continue;
      }

      if ( badgeTask.getBadgeTasks().isEmpty() )
      {
        taskCount++;
        final TaskCompletionViewModel completionRecord = scout.getCompletionRecord( badgeTask );
        if ( null != completionRecord )
        {
          tasksComplete++;
        }
      }
      else
      {
        for ( final BadgeTask childTask : badgeTask.getBadgeTasks() )
        {
          taskCount++;
          final TaskCompletionViewModel completionRecord = scout.getCompletionRecord( childTask );
          if ( null != completionRecord )
          {
            tasksComplete++;
          }
        }
      }
    }
    return (int) ( tasksComplete / (float) taskCount * 100.0 );
  }
}
