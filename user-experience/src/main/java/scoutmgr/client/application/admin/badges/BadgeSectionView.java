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
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import java.util.ArrayList;
import javax.inject.Inject;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.entity.BadgeCategory;
import scoutmgr.client.entity.ScoutSection;
import scoutmgr.client.view.model.BadgeCategoryViewModel;

public class BadgeSectionView
  extends ViewWithUiHandlers<BadgeSectionUiHandlers>
  implements BadgeSectionPresenter.View
{
  private final ListDataProvider<BadgeCategoryViewModel> _provider;

  @UiField( provided = true )
  DataGrid<BadgeCategoryViewModel> _grid;

  ScoutSection _badgeSection;

  @Inject
  EntityRepository _entityRepository;

  interface Binder
    extends UiBinder<Widget, BadgeSectionView>
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
  BadgeSectionView( final Binder uiBinder )
  {
    _grid = new DataGrid<>( 20, GWT.<DataGridResources>create( DataGridResources.class ) );
    _grid.setWidth( "100%" );
    _grid.setHeight( "620px" );
    _grid.setAutoHeaderRefreshDisabled( false );
    _grid.setEmptyTableWidget( new Label( "Select a Scout Section from the left" ) );

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
  public void setBadgeSection( final ScoutSection level )
  {
    if ( level != _badgeSection )
    {
      changeBadgeSection( level );
    }
  }

  private void changeBadgeSection( final ScoutSection badgeSection )
  {
    _badgeSection = badgeSection;
    _provider.getList().clear();
    if ( null != badgeSection )
    {
      final ArrayList<BadgeCategory> categories = _entityRepository.findAll( BadgeCategory.class );
      for ( final BadgeCategory category : categories )
      {
        if ( badgeSection == category.getScoutSection() )
        {
          _provider.getList().add( buildViewModel( category ) );
        }
      }
    }
    ColumnSortEvent.fire( _grid, _grid.getColumnSortList() );
  }

  private BadgeCategoryViewModel buildViewModel( final BadgeCategory category )
  {
    final BadgeCategoryViewModel viewModel = new BadgeCategoryViewModel( category.getName(), category );
    viewModel.setSection( category.getScoutSection() );
    viewModel.setRank( category.getRank() );
    viewModel.setName( category.getName() );
    return viewModel;
  }
}
