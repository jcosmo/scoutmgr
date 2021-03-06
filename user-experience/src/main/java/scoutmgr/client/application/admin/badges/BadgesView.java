package scoutmgr.client.application.admin.badges;

import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import java.util.List;
import javax.inject.Inject;
import scoutmgr.client.resource.ScoutmgrResourceBundle;
import scoutmgr.client.view.model.BadgeCategoryViewModel;

@SuppressWarnings( "Convert2Lambda" )
public class BadgesView
  extends ViewWithUiHandlers<BadgesUiHandlers>
  implements BadgesPresenter.View
{
  @UiField
  ScoutmgrResourceBundle _bundle;

  @UiField
  LayoutPanel _content;

  @UiField
  SimplePanel _leftNav;

  @UiField
  LayoutPanel _badgesPanel;

  interface Binder
    extends UiBinder<Widget, BadgesView>
  {
  }

  @Inject
  BadgesView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  public void setBadgeCategoryViewModels( final List<BadgeCategoryViewModel> badgeCategoryViewModels )
  {

  }

  @Override
  public void setInSlot( final Object slot, final IsWidget content )
  {
    if ( slot == BadgesPresenter.SLOT_LEFT_NAV )
    {
      _leftNav.setWidget( content );
    }
    else if ( slot == BadgesPresenter.SLOT_BADGES_CONTENT )
    {
      _content.clear();
      if ( null != content )
      {
        _content.add( content );
        _content.setWidgetLeftRight( content, 0, Style.Unit.PX, 0, Style.Unit.PX );
        _content.setWidgetTopBottom( content, 0, Style.Unit.PX, 0, Style.Unit.PX );
      }
    }
    else
    {
      super.setInSlot( slot, content );
    }
  }
}
