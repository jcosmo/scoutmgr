package scoutmgr.client.navbar;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class NavbarModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindPresenterWidget( NavbarPresenter.class, NavbarPresenter.View.class, NavbarView.class );
  }
}
