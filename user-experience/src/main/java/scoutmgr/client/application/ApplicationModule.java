package scoutmgr.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ApplicationModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindPresenter( ApplicationPresenter.class, ApplicationPresenter.View.class, ApplicationView.class,
                   ApplicationPresenter.Proxy.class );
  }
}
