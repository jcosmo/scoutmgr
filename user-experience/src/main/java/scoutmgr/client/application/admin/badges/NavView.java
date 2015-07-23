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

  @Inject
  EntityRepository _entityRepository;

  private final SingleSelectionModel<ScoutLevel> _selectionModel;

  interface Binder
    extends UiBinder<Widget, NavView>
  {
  }

  @Inject
  NavView( final Binder uiBinder )
  {
    _provider = new ArrayList<>(  );
    final ArrayList<ScoutLevel> scoutLevels = _entityRepository.findAll( ScoutLevel.class );
    _provider.addAll( scoutLevels );

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

  @Override
  public void setBadgeLevelActive( final ScoutLevel level )
  {
    _selectionModel.clear();
    if ( null != level )
    {
      _selectionModel.setSelected( level, true );
    }
  }
}
