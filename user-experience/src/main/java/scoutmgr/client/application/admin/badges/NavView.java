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
import scoutmgr.client.entity.ScoutSection;
import scoutmgr.client.resource.ScoutmgrResourceBundle;
import scoutmgr.client.view.cell.ScoutSectionCell;

public class NavView
  extends ViewWithUiHandlers<NavUiHandlers>
  implements NavPresenter.View
{
  private final List<ScoutSection> _provider;

  @UiField( provided = true )
  CellList<ScoutSection> _categoryList;

  @UiField
  ScoutmgrResourceBundle _bundle;

  private final SingleSelectionModel<ScoutSection> _selectionModel;
  private ScoutSection _selectedSection;

  interface Binder
    extends UiBinder<Widget, NavView>
  {
  }

  @Inject
  NavView( final Binder uiBinder )
  {
    _selectedSection = null;
    _provider = new ArrayList<>();

    ScoutSectionCell scoutSectionCell = new ScoutSectionCell();
    _categoryList = new CellList<>( scoutSectionCell );
    _categoryList.setKeyboardSelectionPolicy( HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED );
    _selectionModel = new SingleSelectionModel<>();
    _categoryList.setSelectionModel( _selectionModel );
    _selectionModel.addSelectionChangeHandler( new SelectionChangeEvent.Handler()
    {
      @Override
      public void onSelectionChange( final SelectionChangeEvent event )
      {
        final ScoutSection selected = _selectionModel.getSelectedObject();
        getUiHandlers().changeScoutSection( selected );
      }
    } );

    _categoryList.setRowCount( _provider.size(), true );
    _categoryList.setRowData( 0, _provider );

    initWidget( uiBinder.createAndBindUi( this ) );
  }

  public void setBadgeSections( final ArrayList<ScoutSection> levels )
  {
    _selectionModel.clear();
    _provider.clear();
    _provider.addAll( levels );
    _categoryList.setRowCount( _provider.size(), true );
    _categoryList.setRowData( 0, _provider );
    if ( null != _selectedSection )
    {
      _selectionModel.setSelected( _selectedSection, true );
    }
  }

  @Override
  public void setBadgeSectionActive( final ScoutSection level )
  {
    _selectionModel.clear();
    if ( null != level )
    {
      _selectionModel.setSelected( level, true );
    }
    _selectedSection = level;
  }
}
