package scoutmgr.client.security;

import com.gwtplatform.mvp.client.proxy.GatekeeperWithParams;
import javax.inject.Inject;
import scoutmgr.client.ioc.FrontendContext;

public class HasRolesGatekeeper
  extends LoggedInGatekeeper
  implements GatekeeperWithParams
{
  @Inject
  private FrontendContext _frontendContext;
  private String[] _roles;


  @Override
  public boolean canReveal()
  {
    if ( !super.canReveal() )
    {
      return false;
    }
    return PermissionUtil.hasAnyOf( _frontendContext.getUser(),
                                    _roles );
  }

  @Override
  public GatekeeperWithParams withParams( final String[] roles )
  {
    _roles = roles;
    return this;
  }
}
