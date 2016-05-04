package scoutmgr.client.application.scout.badgework;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class BadgeworkModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindSingletonPresenterWidget( BadgeworkPresenter.class, BadgeworkPresenter.View.class, BadgeworkView.class );
    bindSingletonPresenterWidget( BadgeworkProgressPresenter.class, BadgeworkProgressPresenter.View.class, BadgeworkProgressView.class );
  }
}
