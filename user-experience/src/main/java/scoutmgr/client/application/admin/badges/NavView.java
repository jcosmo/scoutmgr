package scoutmgr.client.application.admin.badges;

import com.google.gwt.cell.client.TextCell;
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
import java.util.logging.Logger;
import scoutmgr.client.data_type.BadgeScoutLevel;
import scoutmgr.client.resource.ScoutmgrResourceBundle;

public class NavView
  extends ViewWithUiHandlers<NavUiHandlers>
  implements NavPresenter.View
{
  private final List<String> _provider;

  @UiField( provided = true )
  CellList<String> _categoryList;

  @UiField
  ScoutmgrResourceBundle _bundle;

  private final SingleSelectionModel<String> _selectionModel;

  interface Binder
    extends UiBinder<Widget, NavView>
  {
  }

  @Inject
  NavView( final Binder uiBinder )
  {
    _provider = new ArrayList<>(  );
    _provider.add( BadgeScoutLevel.JOEY.name() );
    _provider.add( BadgeScoutLevel.CUB.name() );
    _provider.add( BadgeScoutLevel.SCOUT.name() );
    _provider.add( BadgeScoutLevel.VENTURER.name() );

    TextCell textCell = new TextCell(  );
    _categoryList = new CellList<>( textCell );
    _categoryList.setKeyboardSelectionPolicy( HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED );
    _selectionModel = new SingleSelectionModel<>(  );
    _categoryList.setSelectionModel( _selectionModel );
    _selectionModel.addSelectionChangeHandler( new SelectionChangeEvent.Handler()
    {
      @Override
      public void onSelectionChange( final SelectionChangeEvent event )
      {
        final String selected = _selectionModel.getSelectedObject();
        getUiHandlers().changeScoutLevel( null == selected ? null : BadgeScoutLevel.valueOf( selected ) );
      }
    } );

    _categoryList.setRowCount( _provider.size(), true );
    _categoryList.setRowData( 0, _provider );

    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  public void setBadgeLevelActive( final BadgeScoutLevel level )
  {
    _selectionModel.clear();
    if ( null != level )
    {
      _selectionModel.setSelected( level.name(), true );
    }
  }
}
