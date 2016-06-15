package scoutmgr.client.application.login;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import java.util.logging.Logger;
import javax.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import scoutmgr.client.application.events.EventsUiHandlers;
import scoutmgr.client.ioc.FrontendContext;

public class LoginView
  extends ViewWithUiHandlers<EventsUiHandlers>
  implements LoginPresenter.View
{
  @UiField
  Button _login;

  @Inject
  FrontendContext _frontendContext;

  interface Binder
    extends UiBinder<Widget, LoginView>
  {
  }

  @Inject
  LoginView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }

  private static final Logger LOG = Logger.getLogger( LoginView.class.getName() );

  @UiHandler( "_login" )
  public void onLogin( final ClickEvent e )
  {
    _frontendContext.login();
  }
}
