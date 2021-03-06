package scoutmgr.client.application.admin.members;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
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
import gwt.material.design.client.ui.MaterialAnchorButton;
import java.util.Collection;
import java.util.Comparator;
import javax.inject.Inject;
import scoutmgr.client.resource.ScoutmgrResourceBundle;
import scoutmgr.client.view.RDateUtil;
import scoutmgr.client.view.cell.ActionCell;
import scoutmgr.client.view.model.ScoutViewModel;

@SuppressWarnings( "Convert2Lambda" )
public class MembersView
  extends ViewWithUiHandlers<MembersUiHandlers>
  implements MembersPresenter.View, ActionCell.UiActionHandler<ScoutViewModel>
{
  private final ListDataProvider<ScoutViewModel> _provider;

  @UiField( provided = true )
  DataGrid<ScoutViewModel> _memberTable;
  @UiField
  MaterialAnchorButton _addScoutButton;
  @UiField
  ScoutmgrResourceBundle _bundle;
  @UiField
  SimplePanel _pagerPanel;

  interface Binder
    extends UiBinder<Widget, MembersView>
  {
  }

  interface MembersDataGridStyle
    extends DataGrid.Style
  {
    String left();

    String headerHighlight();
  }

  interface MembersDataGridResources
    extends DataGrid.Resources
  {
    @Override
    @Source( "MembersDataGrid.gss" )
    MembersDataGridStyle dataGridStyle();
  }

  @Inject
  MembersView( final Binder uiBinder )
  {
    _memberTable = new DataGrid<>( 20 );
    _memberTable.setAutoHeaderRefreshDisabled( false );
    _memberTable.setEmptyTableWidget( new Label( "No resources" ) );

    _provider = new ListDataProvider<>();

    final Column<ScoutViewModel, String> givenNameColumn =
      new Column<ScoutViewModel, String>( new TextCell() )
      {
        @Override
        public String getValue( final ScoutViewModel viewModel )
        {
          return viewModel.getFirstName();
        }
      };
    final Column<ScoutViewModel, String> familyNameColumn =
      new Column<ScoutViewModel, String>( new TextCell() )
      {
        @Override
        public String getValue( final ScoutViewModel viewModel )
        {
          return viewModel.getLastName();
        }
      };
    final Column<ScoutViewModel, String> scoutSectionColumn =
      new Column<ScoutViewModel, String>( new TextCell() )
      {
        @Override
        public String getValue( final ScoutViewModel viewModel )
        {
          return viewModel.getScoutSection().getCode();
        }
      };
    final Column<ScoutViewModel, String> dobColumn =
      new Column<ScoutViewModel, String>( new TextCell() )
      {
        @Override
        public String getValue( final ScoutViewModel viewModel )
        {
          return RDateUtil.formatRDate( viewModel.getDob() );
        }
      };
    final Column<ScoutViewModel, String> regNumColumn =
      new Column<ScoutViewModel, String>( new TextCell() )
      {
        @Override
        public String getValue( final ScoutViewModel viewModel )
        {
          return viewModel.getRegistrationNumber();
        }
      };

    final ActionCell<ScoutViewModel> actionCell = new ActionCell<>( this, true, true, true, false );
    final Column<ScoutViewModel, ScoutViewModel> actionsColumn =
      new Column<ScoutViewModel, ScoutViewModel>( actionCell )
      {
        @Override
        public ScoutViewModel getValue( final ScoutViewModel object )
        {
          return object;
        }
      };

    createColumnSorters( givenNameColumn, familyNameColumn, scoutSectionColumn );

    _memberTable.addColumn( scoutSectionColumn, SafeHtmlUtils.fromString( "Section" ) );
    _memberTable.addColumn( givenNameColumn, SafeHtmlUtils.fromString( "Given Name" ) );
    _memberTable.addColumn( familyNameColumn, SafeHtmlUtils.fromString( "Family Name" ) );
    _memberTable.addColumn( dobColumn, SafeHtmlUtils.fromString( "Birthday" ) );
    _memberTable.addColumn( regNumColumn, SafeHtmlUtils.fromString( "Reg. Num" ) );
    _memberTable.addColumn( actionsColumn );
    _memberTable.setColumnWidth( actionsColumn, 120, Style.Unit.PX );
    final NoSelectionModel<ScoutViewModel> selectionModel = new NoSelectionModel<>();
    _memberTable.setSelectionModel( selectionModel );
    _memberTable.setKeyboardSelectionPolicy( HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED );


    _provider.addDataDisplay( _memberTable );

    initWidget( uiBinder.createAndBindUi( this ) );

    setupPager();
  }

  private void setupPager()
  {
    final SimplePager.Resources pagerResources = GWT.create( SimplePager.Resources.class );
    final SimplePager pager = new SimplePager( SimplePager.TextLocation.CENTER, pagerResources, false, 0, true );
    pager.setDisplay( _memberTable );
    _pagerPanel.setWidget( pager );
  }

  private void createColumnSorters( final Column<ScoutViewModel, String> givenNameColumn,
                                    final Column<ScoutViewModel, String> familyNameColumn,
                                    final Column<ScoutViewModel, String> scoutSectionColumn)
  {
    final ColumnSortEvent.ListHandler<ScoutViewModel> sortHandler =
      new ColumnSortEvent.ListHandler<>( _provider.getList() );
    _memberTable.addColumnSortHandler( sortHandler );

    givenNameColumn.setSortable( true );
    sortHandler.setComparator( givenNameColumn, new Comparator<ScoutViewModel>()
    {
      @Override
      public int compare( final ScoutViewModel o1, final ScoutViewModel o2 )
      {
        return o1.getFirstName().compareTo( o2.getFirstName() );
      }
    } );

    familyNameColumn.setSortable( true );
    sortHandler.setComparator( familyNameColumn, new Comparator<ScoutViewModel>()
    {
      @Override
      public int compare( final ScoutViewModel o1, final ScoutViewModel o2 )
      {
        return o1.getLastName().compareTo( o2.getLastName() );
      }
    } );

    scoutSectionColumn.setSortable( true );
    sortHandler.setComparator( scoutSectionColumn, new Comparator<ScoutViewModel>()
    {
      @Override
      public int compare( final ScoutViewModel o1, final ScoutViewModel o2 )
      {
        return Integer.compare( o1.getScoutSection().getRank(), o2.getScoutSection().getRank() );
      }
    } );
  }

  @Override
  public void setMembers( final Collection<ScoutViewModel> list )
  {
    _provider.getList().clear();
    if ( null != list )
    {
      _provider.getList().addAll( list );
    }
    ColumnSortEvent.fire( _memberTable, _memberTable.getColumnSortList() );
  }

  @UiHandler( value = "_addScoutButton" )
  public void addScoutClicked( final ClickEvent e )
  {
    getUiHandlers().addScout();
  }

  @Override
  public void onView( final ScoutViewModel viewModel )
  {
    // IMPORTANT: DO NOT REMOVE THE FOLLOWING CAST, OR GWT WILL NOT COMPILE
    getUiHandlers().viewScout( viewModel.asModelObject() );
  }

  @Override
  public void onEdit( final ScoutViewModel viewModel )
  {
    // IMPORTANT: DO NOT REMOVE THE FOLLOWING CAST, OR GWT WILL NOT COMPILE
    getUiHandlers().editScout( viewModel.asModelObject() );
  }

  @Override
  public void onDelete( final ScoutViewModel viewModel )
  {
    // IMPORTANT: DO NOT REMOVE THE FOLLOWING CAST, OR GWT WILL NOT COMPILE
    getUiHandlers().requestDeleteScout( viewModel.asModelObject() );
  }
}
