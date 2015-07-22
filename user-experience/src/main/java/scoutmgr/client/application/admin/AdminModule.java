package scoutmgr.client.application.admin;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import scoutmgr.client.application.admin.badges.BadgesModule;
import scoutmgr.client.application.admin.members.MembersModule;

public class AdminModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    install( new BadgesModule() );
    install( new MembersModule() );
  }
}
