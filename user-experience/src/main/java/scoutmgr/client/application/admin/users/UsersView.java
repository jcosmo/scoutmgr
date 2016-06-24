package scoutmgr.client.application.admin.users;

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
import scoutmgr.client.view.cell.ActionCell;
import scoutmgr.client.view.model.UserViewModel;

@SuppressWarnings( "Convert2Lambda" )
public class UsersView
  extends ViewWithUiHandlers<UsersUiHandlers>
  implements UsersPresenter.View, ActionCell.UiActionHandler<UserViewModel>
{
  private final ListDataProvider<UserViewModel> _provider;

  @UiField( provided = true )
  DataGrid<UserViewModel> _usersTable;
  @UiField
  MaterialAnchorButton _addUserButton;
  @UiField
  ScoutmgrResourceBundle _bundle;
  @UiField
  SimplePanel _pagerPanel;

  interface Binder
    extends UiBinder<Widget, UsersView>
  {
  }

  interface UsersDataGridStyle
    extends DataGrid.Style
  {
    String left();

    String headerHighlight();
  }

  interface UsersDataGridResources
    extends DataGrid.Resources
  {
    @Override
    @Source( "UsersDataGrid.gss" )
    UsersDataGridStyle dataGridStyle();
  }

  @Inject
  UsersView( final Binder uiBinder )
  {
    _usersTable = new DataGrid<>( 20 );
    _usersTable.setAutoHeaderRefreshDisabled( false );
    _usersTable.setEmptyTableWidget( new Label( "No users" ) );

    _provider = new ListDataProvider<>();

    final Column<UserViewModel, String> usernameColumn =
      new Column<UserViewModel, String>( new TextCell() )
      {
        @Override
        public String getValue( final UserViewModel viewModel )
        {
          return viewModel.getUserName();
        }
      };

    final ActionCell<UserViewModel> actionCell = new ActionCell<>( this, true, true, true, false );
    final Column<UserViewModel, UserViewModel> actionsColumn =
      new Column<UserViewModel, UserViewModel>( actionCell )
      {
        @Override
        public UserViewModel getValue( final UserViewModel object )
        {
          return object;
        }
      };

    createColumnSorters( usernameColumn );

    _usersTable.addColumn( usernameColumn, SafeHtmlUtils.fromString( "Username" ) );
    _usersTable.addColumn( actionsColumn );
    _usersTable.setColumnWidth( actionsColumn, 120, Style.Unit.PX );
    final NoSelectionModel<UserViewModel> selectionModel = new NoSelectionModel<>();
    _usersTable.setSelectionModel( selectionModel );
    _usersTable.setKeyboardSelectionPolicy( HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED );


    _provider.addDataDisplay( _usersTable );

    initWidget( uiBinder.createAndBindUi( this ) );

    setupPager();
  }

  private void setupPager()
  {
    final SimplePager.Resources pagerResources = GWT.create( SimplePager.Resources.class );
    final SimplePager pager = new SimplePager( SimplePager.TextLocation.CENTER, pagerResources, false, 0, true );
    pager.setDisplay( _usersTable );
    _pagerPanel.setWidget( pager );
  }

  private void createColumnSorters( final Column<UserViewModel, String> usernameColumn )
  {
    final ColumnSortEvent.ListHandler<UserViewModel> sortHandler =
      new ColumnSortEvent.ListHandler<>( _provider.getList() );
    _usersTable.addColumnSortHandler( sortHandler );

    usernameColumn.setSortable( true );
    sortHandler.setComparator( usernameColumn, new Comparator<UserViewModel>()
    {
      @Override
      public int compare( UserViewModel o1, UserViewModel o2 )
      {
        return o1.getUserName().compareTo( o2.getUserName() );
      }
    } );
  }

  @Override
  public void setUsers( final Collection<UserViewModel> list )
  {
    _provider.getList().clear();
    if ( null != list )
    {
      _provider.getList().addAll( list );
    }
    ColumnSortEvent.fire( _usersTable, _usersTable.getColumnSortList() );
  }

  @UiHandler( value = "_addUserButton" )
  public void addScoutClicked( final ClickEvent e )
  {
    getUiHandlers().addUser();
  }

  @Override
  public void onView( final UserViewModel viewModel )
  {
    // IMPORTANT: DO NOT REMOVE THE FOLLOWING CAST, OR GWT WILL NOT COMPILE
    getUiHandlers().viewUser( viewModel.asModelObject() );
  }

  @Override
  public void onEdit( final UserViewModel viewModel )
  {
    // IMPORTANT: DO NOT REMOVE THE FOLLOWING CAST, OR GWT WILL NOT COMPILE
    getUiHandlers().editUser( viewModel.asModelObject() );
  }

  @Override
  public void onDelete( final UserViewModel viewModel )
  {
    // IMPORTANT: DO NOT REMOVE THE FOLLOWING CAST, OR GWT WILL NOT COMPILE
    getUiHandlers().requestDeleteUser( viewModel.asModelObject() );
  }
}
