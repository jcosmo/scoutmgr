package scoutmgr.client.application;

import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class ApplicationView
  extends ViewImpl
  implements ApplicationPresenter.View
{
  @UiField
  Label _loading;
  @UiField
  LayoutPanel _content;
  @UiField
  SimplePanel _navbarPanel;
  @UiField
  SimplePanel _footerPanel;
  @UiField
  LayoutPanel _mainLayout;

  interface Binder
    extends UiBinder<Widget, ApplicationView>
  {
  }

  @Inject
  ApplicationView( Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  public void setInSlot( final Object slot, final IsWidget content )
  {
    if ( slot == ApplicationPresenter.SLOT_MAIN_CONTENT )
    {
      _content.clear();
      _content.add( content );
      _content.setWidgetLeftRight( content, 0, Style.Unit.PX, 0, Style.Unit.PX );
      _content.setWidgetTopBottom( content, 0, Style.Unit.PX, 0, Style.Unit.PX );
    }
    else if ( slot == ApplicationPresenter.SLOT_MAIN_NAVBAR )
    {
      _navbarPanel.setWidget( content );
    }
    else if ( slot == ApplicationPresenter.SLOT_MAIN_FOOTER )
    {
      _footerPanel.setWidget( content );
    }
    else
    {
      super.setInSlot( slot, content );
    }
  }

  @Override
  public void showLoading( boolean visible )
  {
    _loading.setVisible( visible );
  }
}
