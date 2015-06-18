package scoutmgr.client.scoutdetails;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ScoutdetailsModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindSingletonPresenterWidget( ScoutdetailsPresenter.class, ScoutdetailsPresenter.View.class, ScoutdetailsView.class );
  }
}
