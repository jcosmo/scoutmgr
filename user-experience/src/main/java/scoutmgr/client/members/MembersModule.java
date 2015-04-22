package scoutmgr.client.members;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class MembersModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindPresenter( MembersPresenter.class, MembersPresenter.View.class, MembersView.class,
                   MembersPresenter.Proxy.class );
  }
}
