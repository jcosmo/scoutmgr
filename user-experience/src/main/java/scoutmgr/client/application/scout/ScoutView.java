package scoutmgr.client.application.scout;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialToast;
import java.util.logging.Logger;
import scoutmgr.client.view.model.ScoutViewModel;

public class ScoutView
  extends ViewWithUiHandlers<ScoutUiHandlers>
  implements ScoutPresenter.View
{
  @UiField
  SimplePanel _scoutDetailsPanel;

  @UiField
  MaterialTab _tabs;

  @UiField
  SimplePanel _scoutBadgesPanel;

  interface Binder
    extends UiBinder<Widget, ScoutView>
  {
  }

  @Inject
  ScoutView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
    _scoutDetailsPanel.add( new HTMLPanel("scout details!") );
    _tabs.selectTab( "tab_badgework" );
  }

  @Override
  public void setScout( final ScoutViewModel viewModel )
  {
    _scoutDetailsPanel.clear();
    _scoutDetailsPanel.add( new HTMLPanel( "Scout: " + viewModel.getFirstName() + " " + viewModel.getLastName() ) );
    _tabs.selectTab( "tab_badgework" );
  }

  @Override
  public void showLoadingMessage()
  {
    _scoutDetailsPanel.clear();
    _scoutDetailsPanel.add( new HTMLPanel("Loading....") );
  }

  @Override
  public void setInSlot( final Object slot, final IsWidget content )
  {
    if ( ScoutPresenter.SLOT_BADGEWORK.equals( slot ) )
    {
      _scoutBadgesPanel.setWidget( content );
    }
    super.setInSlot( slot, content );
  }
}
