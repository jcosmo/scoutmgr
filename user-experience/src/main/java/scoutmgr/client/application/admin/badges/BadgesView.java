package scoutmgr.client.application.admin.badges;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import java.util.List;
import java.util.logging.Logger;
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
  SimplePanel _content;
  @UiField
  SimplePanel _leftNav;

  interface Binder
    extends UiBinder<Widget, BadgesView>
  {
  }

  @Inject
  BadgesView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ));
  }

  @Override
  public void setBadgeCategoryViewModels( final List<BadgeCategoryViewModel> badgeCategoryViewModels )
  {

  }

  private static final Logger LOG = Logger.getLogger( BadgesView.class.getName() );

  @Override
  public void setInSlot( final Object slot, final IsWidget content )
  {
    if ( slot == BadgesPresenter.SLOT_LEFT_NAV )
    {
      _leftNav.setWidget( content );
    }
    else if ( slot == BadgesPresenter.SLOT_BADGES_CONTENT )
    {
      _content.setWidget( content );
    }
    else
    {
      super.setInSlot( slot, content );
    }
  }
}
