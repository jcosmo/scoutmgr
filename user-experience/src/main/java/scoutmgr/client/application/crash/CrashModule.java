package scoutmgr.client.application.crash;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class CrashModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindPresenter( CrashPresenter.class, CrashPresenter.View.class, CrashView.class,
                   CrashPresenter.Proxy.class );
  }
}
