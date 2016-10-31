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
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import java.util.Collection;
import java.util.Comparator;
import javax.inject.Inject;
import scoutmgr.client.resource.ScoutmgrResourceBundle;
import scoutmgr.client.view.model.ScoutViewModel;

public class TroopView
  extends ViewWithUiHandlers<TroopUiHandlers>
  implements TroopPresenter.View
{
  private final ListDataProvider<ScoutViewModel> _provider;
  @UiField
  LayoutPanel _scoutsPanel;
  @UiField( provided = true )
  DataGrid<ScoutViewModel> _scoutsTable;
  @UiField
  SimplePanel _pagerPanel;
  @UiField
  ScoutmgrResourceBundle _bundle;

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

    _scoutsTable.addColumn( nameColumn, SafeHtmlUtils.fromString( "Username" ) );
    final NoSelectionModel<ScoutViewModel> selectionModel = new NoSelectionModel<>();
    _scoutsTable.setSelectionModel( selectionModel );
    _scoutsTable.setKeyboardSelectionPolicy( HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED );


    _provider.addDataDisplay( _scoutsTable );

    initWidget( uiBinder.createAndBindUi( this ) );

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
    sortHandler.setComparator( nameColumn,
                               ( o1, o2 ) ->
                                 getScoutName( o1  ).compareTo( getScoutName( o2 )) );
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
}
