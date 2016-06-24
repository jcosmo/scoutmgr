package scoutmgr.client.application.admin;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import scoutmgr.client.application.admin.badges.BadgesModule;
import scoutmgr.client.application.admin.members.MembersModule;
import scoutmgr.client.application.admin.users.UsersModule;

public class AdminModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    install( new UsersModule() );
    install( new BadgesModule() );
    install( new MembersModule() );
  }
}
