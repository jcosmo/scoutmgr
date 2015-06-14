package scoutmgr.client.members;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import java.util.Collection;
import java.util.Comparator;
import scoutmgr.client.view.model.ScoutViewModel;

@SuppressWarnings( "Convert2Lambda" )
public class MembersView
  extends ViewWithUiHandlers<MembersUiHandlers>
  implements scoutmgr.client.members.MembersPresenter.View
{
  // private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger( MembersPresenter.class.getName() );

  private final ListDataProvider<ScoutViewModel> _provider;

  @UiField
  SimplePanel _navbarPanel;
  @UiField
  LayoutPanel _membersPanel;
  @UiField
  SimplePanel _footerPanel;

  @UiField( provided = true )
  SimplePager _pager;
  @UiField( provided = true )
  DataGrid<ScoutViewModel> _memberTable;

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
    final MembersDataGridResources resources = GWT.create( MembersDataGridResources.class );

    _provider = new ListDataProvider<>();

    _memberTable = new DataGrid<>( 20, resources );
    _memberTable.setEmptyTableWidget( new Label( "No resources" ) );
    _memberTable.setLoadingIndicator( new Label( "No resources" ) );

    final ColumnSortEvent.ListHandler<ScoutViewModel> sortHandler =
      new ColumnSortEvent.ListHandler<>( _provider.getList() );
    _memberTable.addColumnSortHandler( sortHandler );

    final SimplePager.Resources pagerResources = GWT.create( SimplePager.Resources.class );
    _pager = new SimplePager( SimplePager.TextLocation.CENTER, pagerResources, false, 0, true );
    _pager.setDisplay( _memberTable );

    final Column<ScoutViewModel, String> firstNameColumn =
      new Column<ScoutViewModel, String>( new TextCell() )
      {
        @Override
        public String getValue( final ScoutViewModel viewModel )
        {
          return viewModel.getFirstName();
        }
      };
    firstNameColumn.setSortable( true );
    sortHandler.setComparator( firstNameColumn, new Comparator<ScoutViewModel>()
    {
      @Override
      public int compare( final ScoutViewModel o1, final ScoutViewModel o2 )
      {
        return o1.getFirstName().compareTo( o2.getFirstName() );
      }
    } );
    _memberTable.addColumn( firstNameColumn, SafeHtmlUtils.fromString( "Name" ) );

    final Column<ScoutViewModel, String> surnameColumn =
      new Column<ScoutViewModel, String>( new TextCell() )
      {
        @Override
        public String getValue( final ScoutViewModel viewModel )
        {
          return viewModel.getLastName();
        }
      };
    surnameColumn.setSortable( true );
    sortHandler.setComparator( surnameColumn, new Comparator<ScoutViewModel>()
    {
      @Override
      public int compare( final ScoutViewModel o1, final ScoutViewModel o2 )
      {
        return o1.getLastName().compareTo( o2.getLastName() );
      }
    } );
    _memberTable.addColumn( surnameColumn, SafeHtmlUtils.fromString( "Surname" ) );

    _provider.addDataDisplay( _memberTable );
    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  public void setInSlot( final Object slot, final IsWidget content )
  {
    if ( slot == scoutmgr.client.members.MembersPresenter.SLOT_NAVBAR_CONTENT )
    {
      _navbarPanel.setWidget( content );
    }
    else if ( slot == MembersPresenter.SLOT_FOOTER_CONTENT )
    {
      _footerPanel.setWidget( content );
    }
    else
    {
      throw new UnsupportedOperationException( "Can't insert into slot: " + slot );
    }
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
}
