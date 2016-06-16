package scoutmgr.client.application.login;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialTextBox;
import javax.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class LoginView
  extends ViewWithUiHandlers<LoginUiHandlers>
  implements LoginPresenter.View
{
  @UiField
  MaterialButton _signInButton;
  @UiField
  MaterialTextBox _password;
  @UiField
  MaterialTextBox _username;

  interface Binder
    extends UiBinder<Widget, LoginView>
  {
  }

  @Inject
  LoginView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @UiHandler( "_signInButton" )
  public void onLogin( final ClickEvent e )
  {
    getUiHandlers().onLogin( _username.getValue(), _password.getValue() );
  }
}
