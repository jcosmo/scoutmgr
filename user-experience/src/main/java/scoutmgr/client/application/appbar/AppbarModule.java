package scoutmgr.client.application.appbar;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class AppbarModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindSingletonPresenterWidget( AppbarPresenter.class, AppbarPresenter.View.class, AppbarView.class );
  }
}
