package scoutmgr.client.application.troop;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import gwt.material.design.client.ui.MaterialTab;
import javax.inject.Inject;

public class TroopView
  extends ViewWithUiHandlers<TroopUiHandlers>
  implements TroopPresenter.View
{
  interface Binder
    extends UiBinder<Widget, TroopView>
  {
  }

  @Inject
  TroopView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }
}
