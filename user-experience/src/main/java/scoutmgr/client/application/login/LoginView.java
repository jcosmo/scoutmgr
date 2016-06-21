package scoutmgr.client.application.login;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import javax.inject.Inject;

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
    MaterialLoader.showLoading( true );
    getUiHandlers().onLogin( _username.getValue(), _password.getValue() );
  }

  @Override
  public void onFailedLogin()
  {
    MaterialLoader.showLoading( false );
    MaterialToast.fireToast( "Login failed, please try again" );
  }

  @Override
  public void onSuccessfulLogin()
  {
    MaterialLoader.showLoading( false );
  }
}
