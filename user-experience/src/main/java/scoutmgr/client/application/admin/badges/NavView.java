package scoutmgr.client.application.admin.badges;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import java.util.ArrayList;
import java.util.List;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.entity.ScoutLevel;
import scoutmgr.client.resource.ScoutmgrResourceBundle;
import scoutmgr.client.view.cell.ScoutLevelCell;

public class NavView
  extends ViewWithUiHandlers<NavUiHandlers>
  implements NavPresenter.View
{
  private final List<ScoutLevel> _provider;

  @UiField( provided = true )
  CellList<ScoutLevel> _categoryList;

  @UiField
  ScoutmgrResourceBundle _bundle;

  private final SingleSelectionModel<ScoutLevel> _selectionModel;
  private ScoutLevel _selectedLevel;

  interface Binder
    extends UiBinder<Widget, NavView>
  {
  }

  @Inject
  NavView( final Binder uiBinder )
  {
    _selectedLevel = null;
    _provider = new ArrayList<>(  );

    ScoutLevelCell scoutLevelCell = new ScoutLevelCell(  );
    _categoryList = new CellList<>( scoutLevelCell );
    _categoryList.setKeyboardSelectionPolicy( HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED );
    _selectionModel = new SingleSelectionModel<>(  );
    _categoryList.setSelectionModel( _selectionModel );
    _selectionModel.addSelectionChangeHandler( new SelectionChangeEvent.Handler()
    {
      @Override
      public void onSelectionChange( final SelectionChangeEvent event )
      {
        final ScoutLevel selected = _selectionModel.getSelectedObject();
        getUiHandlers().changeScoutLevel( selected );
      }
    } );

    _categoryList.setRowCount( _provider.size(), true );
    _categoryList.setRowData( 0, _provider );

    initWidget( uiBinder.createAndBindUi( this ) );
  }

  public void setBadgeLevels(final ArrayList<ScoutLevel> levels)
  {
    _selectionModel.clear();
    _provider.clear();
    _provider.addAll( levels );
    _categoryList.setRowCount( _provider.size(), true );
    _categoryList.setRowData( 0, _provider );
    if ( null != _selectedLevel )
    {
      _selectionModel.setSelected( _selectedLevel, true );
    }
  }

  @Override
  public void setBadgeLevelActive( final ScoutLevel level )
  {
    _selectionModel.clear();
    if ( null != level )
    {
      _selectionModel.setSelected( level, true );
    }
    _selectedLevel = level;
  }
}
