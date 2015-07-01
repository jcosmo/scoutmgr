package scoutmgr.client.application.dialog;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class DialogModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindSingletonPresenterWidget( DialogPresenter.class, DialogPresenter.View.class, DialogView.class );
  }
}
