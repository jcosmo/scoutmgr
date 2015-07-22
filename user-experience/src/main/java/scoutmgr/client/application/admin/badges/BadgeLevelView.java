package scoutmgr.client.application.admin.badges;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.data_type.BadgeCategoryScoutLevel;
import scoutmgr.client.data_type.BadgeScoutLevel;
import scoutmgr.client.entity.BadgeCategory;
import scoutmgr.client.resource.ScoutmgrResourceBundle;
import scoutmgr.client.view.model.BadgeCategoryViewModel;

public class BadgeLevelView
  extends ViewWithUiHandlers<BadgeLevelUiHandlers>
  implements BadgeLevelPresenter.View
{
  private final ListDataProvider<BadgeCategoryViewModel> _provider;
  @UiField
  ScoutmgrResourceBundle _bundle;

  @UiField( provided = true )
  DataGrid<BadgeCategoryViewModel> _grid;

  BadgeScoutLevel _badgeLevel;

  @Inject
  EntityRepository _entityRepository;

  interface Binder
    extends UiBinder<Widget, BadgeLevelView>
  {
  }

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
    @Source( "BadgesDataGrid.gss" )
    DataGridStyle dataGridStyle();
  }
  @Inject
  BadgeLevelView( final Binder uiBinder )
  {
    _grid = new DataGrid( 20,  GWT.<DataGridResources>create( DataGridResources.class ) );
    _grid.setWidth( "100%" );
    _grid.setHeight( "620px" );
    _grid.setAutoHeaderRefreshDisabled( false );
    _grid.setEmptyTableWidget( new Label( "No Badge Categories" ) );

    _provider = new ListDataProvider<>();

    final Column<BadgeCategoryViewModel, String> col =
      new Column<BadgeCategoryViewModel, String>( new TextCell() )
      {
        @Override
        public String getValue( final BadgeCategoryViewModel viewModel )
        {
          return viewModel.getDisplayString();
        }
      };

    _grid.addColumn( col, SafeHtmlUtils.fromString( "Category" ) );

    _provider.addDataDisplay( _grid );

    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  public void setBadgeLevel( final BadgeScoutLevel level )
  {
    if ( level != _badgeLevel )
    {
      changeBadgeLevel( level );
    }
  }

  private static final Logger LOG = Logger.getLogger( BadgeLevelView.class.getName() );

  private void changeBadgeLevel( final BadgeScoutLevel badgeLevel )
  {
    LOG.warning("changing to " + badgeLevel );
    _badgeLevel = badgeLevel;
    _provider.getList().clear();
    if ( null != badgeLevel )
    {
      final BadgeCategoryScoutLevel level = BadgeCategoryScoutLevel.valueOf( badgeLevel.name() );
      final ArrayList<BadgeCategory> categories = _entityRepository.findAll( BadgeCategory.class );
      LOG.warning("got " + categories.size() );
      for ( final BadgeCategory category : categories )
      {
        if ( level == category.getScoutLevel() )
        {
          LOG.warning("matched " + category );
          _provider.getList().add( buildViewModel( category) );
        }
      }
    }
    ColumnSortEvent.fire( _grid, _grid.getColumnSortList() );
  }

  private BadgeCategoryViewModel buildViewModel( final BadgeCategory category )
  {
    final BadgeCategoryViewModel viewModel = new BadgeCategoryViewModel( category.getName(), category);
    viewModel.setLevel( category.getScoutLevel() );
    viewModel.setRank( category.getRank() );
    viewModel.setName( category.getName() );
    return viewModel;
  }
}
