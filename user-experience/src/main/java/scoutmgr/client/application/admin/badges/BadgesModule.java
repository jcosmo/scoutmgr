package scoutmgr.client.application.admin.badges;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class BadgesModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindPresenter( BadgesPresenter.class, BadgesPresenter.View.class, BadgesView.class, BadgesPresenter.Proxy.class );
    bindSingletonPresenterWidget( NavPresenter.class, NavPresenter.View.class, NavView.class );
    bindSingletonPresenterWidget( BadgeLevelPresenter.class, BadgeLevelPresenter.View.class, BadgeLevelView.class );  }
}
