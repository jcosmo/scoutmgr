package scoutmgr.client.members;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import java.util.ArrayList;
import java.util.Collection;
import scoutmgr.client.view.model.ListItemViewModel;

public class MembersView
  extends ViewWithUiHandlers<MembersUiHandlers>
  implements scoutmgr.client.members.MembersPresenter.View
{
  @UiField
  SimplePanel _navbarPanel;
  @UiField
  SimplePanel _membersPanel;
  @UiField
  SimplePanel _footerPanel;

  @UiField(provided = true)
  CellList<ListItemViewModel> _list;

  interface Binder
    extends UiBinder<Widget, MembersView>
  {
  }

  @Inject
  MembersView( final Binder uiBinder )
  {
    ScoutCell renderer = new ScoutCell( );

    _list = new CellList<>( renderer );

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
  public void setMembers( final Collection<ListItemViewModel> members )
  {
    _list.setRowCount( members.size() );
    _list.setRowData( 0, new ArrayList<>(members) );
  }

  @Override
  public void resetAndFocus()
  {
  }
}
