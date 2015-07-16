package scoutmgr.client.application.admin;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import scoutmgr.client.application.admin.badges.BadgesModule;

public class AdminModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    install( new BadgesModule() );
  }
}
