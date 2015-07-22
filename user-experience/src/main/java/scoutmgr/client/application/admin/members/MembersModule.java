package scoutmgr.client.application.admin.members;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class MembersModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindPresenter( MembersPresenter.class, MembersPresenter.View.class, MembersView.class,
                   MembersPresenter.Proxy.class );
    bindPresenter( MemberFormPresenter.class, MemberFormPresenter.View.class, MemberFormView.class,
                   MemberFormPresenter.Proxy.class );
  }
}
