package scoutmgr.client.application.troop;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import scoutmgr.client.application.scout.badgework.BadgeworkModule;

public class TroopModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindPresenter( TroopPresenter.class, TroopPresenter.View.class, TroopView.class,
                   TroopPresenter.Proxy.class);
  }
}
