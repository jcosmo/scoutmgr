package scoutmgr.client.members;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import scoutmgr.client.entity.Person;
import scoutmgr.client.view.model.ScoutViewModel;

public class MembersView
  extends ViewWithUiHandlers<MembersUiHandlers>
  implements scoutmgr.client.members.MembersPresenter.View
{
  private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger( MembersView.class.getName() );

  private final ListDataProvider<ScoutViewModel> _provider;

  @UiField
  SimplePanel _navbarPanel;
  @UiField
  SimplePanel _membersPanel;
  @UiField
  SimplePanel _footerPanel;

  @UiField(provided = true)
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

    _memberTable = new DataGrid<>( 20, resources );
    _memberTable.setEmptyTableWidget( new Label( "No resources" ) );
    _memberTable.setLoadingIndicator( new Label( "No resources" ) );

    final Column<ScoutViewModel, String> firstNameColumn =
      new Column<ScoutViewModel, String>( new TextCell() )
      {
        @Override
        public String getValue( final ScoutViewModel viewModel )
        {
          return viewModel.getFirstName();
        }
      };
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
    _memberTable.addColumn( surnameColumn, SafeHtmlUtils.fromString( "Surname" ) );

    _provider = new ListDataProvider<>();
    _provider.addDataDisplay( _memberTable );

    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  public void setInSlot( final Object slot, final IsWidget content )
  {
    if ( slot == MembersPresenter.SLOT_MAIN_CONTENT )
    {
      _membersPanel.setWidget( content );
    }
    else if ( slot == scoutmgr.client.members.MembersPresenter.SLOT_NAVBAR_CONTENT )
    {
      _navbarPanel.setWidget( content );
    }
    else if ( slot == MembersPresenter.SLOT_FOOTER_CONTENT )
    {
      _footerPanel.setWidget( content );
    }
  }

  @Override
  public void setMembers( final Collection<ScoutViewModel> list )
  {
    LOG.warning( "Setting members to : " + list.size() );
    _provider.getList().clear();
    if ( null != list )
    {
      final List<ScoutViewModel> unfilteredList = new ArrayList<>(list);
      _provider.getList().addAll( unfilteredList );
    }
    ColumnSortEvent.fire( _memberTable, _memberTable.getColumnSortList() );
  }

  @Override
  public void resetAndFocus()
  {
  }
}
