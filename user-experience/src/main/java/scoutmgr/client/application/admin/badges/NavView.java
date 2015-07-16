package scoutmgr.client.application.admin.badges;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import java.util.ArrayList;
import java.util.List;
import scoutmgr.client.data_type.BadgeScoutLevel;
import scoutmgr.client.resource.ScoutmgrResourceBundle;

public class NavView
  extends ViewImpl
  implements NavPresenter.View
{
  private final List<String> _provider;

  @UiField( provided = true )
  CellList<String> _categoryList;

  @UiField
  ScoutmgrResourceBundle _bundle;

  private LIElement _currentLink;

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
    _categoryList.setRowCount( _provider.size(), true );
    _categoryList.setRowData( 0, _provider );

    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  public void setMenuItemActive( final String nameToken )
  {
    final LIElement newLink;
    switch ( nameToken )
    {
      default:
        newLink = null;
    }

    if ( newLink != _currentLink )
    {
      if ( null != _currentLink )
      {
        _currentLink.removeClassName( _bundle.bootstrap().active() );
      }

      if ( null != newLink )
      {
        newLink.addClassName( _bundle.bootstrap().active() );
      }

      _currentLink = newLink;
    }
  }
}
