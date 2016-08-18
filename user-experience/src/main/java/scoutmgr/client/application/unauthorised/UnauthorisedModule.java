package scoutmgr.client.application.unauthorised;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class UnauthorisedModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindPresenter( UnauthorisedPresenter.class, UnauthorisedPresenter.View.class, UnauthorisedView.class,
                   UnauthorisedPresenter.Proxy.class );
  }
}
