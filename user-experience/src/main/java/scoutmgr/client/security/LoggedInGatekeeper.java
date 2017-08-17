package scoutmgr.client.security;

import com.gwtplatform.mvp.client.annotations.DefaultGatekeeper;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;
import javax.inject.Inject;
import scoutmgr.client.ScoutmgrApp;

@DefaultGatekeeper
public class LoggedInGatekeeper
  implements Gatekeeper
{
  @Inject
  private ScoutmgrApp _app;

  @Override
  public boolean canReveal()
  {
    return _app.isLoggedIn();
  }
}
