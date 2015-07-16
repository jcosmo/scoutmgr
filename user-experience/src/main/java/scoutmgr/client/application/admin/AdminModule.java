package scoutmgr.client.application.admin;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import scoutmgr.client.application.admin.badges.BadgesPresenter;
import scoutmgr.client.application.admin.badges.BadgesView;

public class AdminModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindPresenter( BadgesPresenter.class, BadgesPresenter.View.class, BadgesView.class, BadgesPresenter.Proxy.class );
  }
}
