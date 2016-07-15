package scoutmgr.client.security;

import com.gwtplatform.mvp.client.annotations.DefaultGatekeeper;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;
import javax.inject.Inject;
import scoutmgr.client.ioc.FrontendContext;

@DefaultGatekeeper
public class LoggedInGatekeeper
  implements Gatekeeper
{
  @Inject
  private FrontendContext _frontendContext;

  @Override
  public boolean canReveal()
  {
    return _frontendContext.isLoggedIn();
  }
}
