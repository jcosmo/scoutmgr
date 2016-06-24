package scoutmgr.client.application.admin.users;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class UsersModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindPresenter( UsersPresenter.class, UsersPresenter.View.class, UsersView.class,
                   UsersPresenter.Proxy.class );
    bindPresenter( UserFormPresenter.class, UserFormPresenter.View.class, UserFormView.class,
                   UserFormPresenter.Proxy.class );
  }
}
