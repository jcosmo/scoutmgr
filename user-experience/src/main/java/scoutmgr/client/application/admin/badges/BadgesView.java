package scoutmgr.client.application.admin.badges;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import java.util.List;
import scoutmgr.client.resource.ScoutmgrResourceBundle;
import scoutmgr.client.view.model.BadgeCategoryViewModel;

@SuppressWarnings( "Convert2Lambda" )
public class BadgesView
  extends ViewWithUiHandlers<BadgesUiHandlers>
  implements BadgesPresenter.View
{
  //private final ListDataProvider<ScoutViewModel> _provider;

  /*
  @UiField
  LayoutPanel _membersPanel;
  */

  /*
  @UiField( provided = true )
  SimplePager _pager;
  */

  @UiField
  ScoutmgrResourceBundle _bundle;

  interface Binder
    extends UiBinder<Widget, BadgesView>
  {
  }

  @Inject
  BadgesView( final Binder uiBinder )
  {
/*    _memberTable = new DataGrid<>( 20, GWT.<MembersDataGridResources>create( MembersDataGridResources.class ) );
    _memberTable.setWidth( "100%" );
    _memberTable.setHeight( "620px" );
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

    final ActionCell<ScoutViewModel> actionCell = new ActionCell<>( this, true, true, false );
    final Column<ScoutViewModel, ScoutViewModel> actionsColumn =
      new Column<ScoutViewModel, ScoutViewModel>( actionCell )
      {
        @Override
        public ScoutViewModel getValue( final ScoutViewModel object )
        {
          return object;
        }
      };

    createColumnSorters( givenNameColumn, familyNameColumn );

    _memberTable.addColumn( givenNameColumn, SafeHtmlUtils.fromString( "Given Name" ) );
    _memberTable.addColumn( familyNameColumn, SafeHtmlUtils.fromString( "Family Name" ) );
    _memberTable.addColumn( dobColumn, SafeHtmlUtils.fromString( "Birthday" ) );
    _memberTable.addColumn( regNumColumn, SafeHtmlUtils.fromString( "Reg. Num" ) );
    _memberTable.addColumn( actionsColumn );
    _memberTable.setColumnWidth( actionsColumn, 60, Style.Unit.PX );

    setupPager();
    _provider.addDataDisplay( _memberTable );
*/
    initWidget( uiBinder.createAndBindUi( this ) );
  }

  /*
  private void setupPager()
  {
    final SimplePager.Resources pagerResources = GWT.create( SimplePager.Resources.class );
    _pager = new SimplePager( SimplePager.TextLocation.CENTER, pagerResources, false, 0, true );
    _pager.setDisplay( _memberTable );
  }

  private void createColumnSorters( final Column<ScoutViewModel, String> givenNameColumn,
                                    final Column<ScoutViewModel, String> familyNameColumn )
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
  }
  */

  @Override
  public void setBadgeCategoryViewModels( final List<BadgeCategoryViewModel> badgeCategoryViewModels )
  {

  }
}
