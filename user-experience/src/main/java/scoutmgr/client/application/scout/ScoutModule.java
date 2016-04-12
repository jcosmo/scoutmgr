package scoutmgr.client.application.scout;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import java.util.logging.Logger;

public class ScoutModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindPresenter( ScoutPresenter.class, ScoutPresenter.View.class, ScoutView.class,
                   ScoutPresenter.Proxy.class);
  }
}
