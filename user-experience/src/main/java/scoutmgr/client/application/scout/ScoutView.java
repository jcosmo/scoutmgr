package scoutmgr.client.application.scout;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class ScoutView
  extends ViewWithUiHandlers<ScoutUiHandlers>
  implements ScoutPresenter.View
{
  @UiField
  SimplePanel _scoutDetailsPanel;
  @UiField
  SimplePanel _otherStuff;

  interface Binder
    extends UiBinder<Widget, ScoutView>
  {
  }

  @Inject
  ScoutView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
    _scoutDetailsPanel.add( new HTMLPanel("scout details!") );
    _otherStuff.add( new HTMLPanel("other stuff") );
  }
}
