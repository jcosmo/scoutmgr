package scoutmgr.client.application.navbar;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class NavbarModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindSingletonPresenterWidget( NavbarPresenter.class, NavbarPresenter.View.class, NavbarView.class );
  }
}
