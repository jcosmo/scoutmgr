package scoutmgr.client.footer;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class FooterModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindPresenterWidget( FooterPresenter.class, FooterPresenter.View.class, FooterView.class );
  }
}
