package scoutmgr.client.application.login;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class LoginModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindPresenter( LoginPresenter.class, LoginPresenter.View.class, LoginView.class,
                   LoginPresenter.Proxy.class );
  }
}
