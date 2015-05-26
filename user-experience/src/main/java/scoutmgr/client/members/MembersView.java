package scoutmgr.client.members;

import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ProvidesKey;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
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

  @UiField( provided = true )
  ValueListBox<ListItemViewModel> _list;

  interface Binder
    extends UiBinder<Widget, MembersView>
  {
  }

  @Inject
  MembersView( final Binder uiBinder )
  {
    _list = new ValueListBox<>(
      new AbstractRenderer<ListItemViewModel>()
      {
        @Override
        public String render( final ListItemViewModel item )
        {
          if ( null != item )
          {
            return item.getDisplayString();
          }
          else
          {
            return "";
          }
        }
      },
      new ProvidesKey<ListItemViewModel>()
      {
        @Override
        public Object getKey( final ListItemViewModel item )
        {
          if (null != item)
          {
            return item.getID();
          }
          else
          {
            return "";
          }
        }
      } );

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
    final ListItemViewModel currentValue = _list.getValue();
    if ( !members.contains( currentValue ))
    {
      _list.setValue( members.iterator().next() );
    }
    _list.setAcceptableValues( members );
  }

  @Override
  public void resetAndFocus()
  {
  }
}
