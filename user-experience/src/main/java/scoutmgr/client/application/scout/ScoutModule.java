package scoutmgr.client.application.scout;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import scoutmgr.client.application.scout.badgework.BadgeworkModule;

public class ScoutModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    install( new BadgeworkModule() );
    bindPresenter( ScoutPresenter.class, ScoutPresenter.View.class, ScoutView.class,
                   ScoutPresenter.Proxy.class);
  }
}
