package scoutmgr.client.security;

import com.gwtplatform.mvp.client.proxy.Gatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import javax.inject.Inject;
import scoutmgr.client.entity.Person;
import scoutmgr.client.ioc.FrontendContext;

public class ScoutViewGatekeeper
  implements Gatekeeper
{
  private static final String[] VIEW_ALL_SCOUT_ROLES = new String[]{ "SITE_ADMIN", "MEMBER_ADMIN" };
  @Inject
  private FrontendContext _frontendContext;
  @Inject
  private PlaceManager _placeManager;

  @Override
  public boolean canReveal()
  {
    if ( !_frontendContext.isLoggedIn() )
    {
      return false;
    }

    // Roles allowed to view all scouts
    if ( PermissionUtil.hasAnyOf( _frontendContext.getUser(),
                                  VIEW_ALL_SCOUT_ROLES ) )
    {
      return true;
    }

    final Person person = _frontendContext.getPerson();
    if ( null == person )
    {
      return false;
    }

    // ID of scout trying to be viewed
    final PlaceRequest currentPlaceRequest = _placeManager.getCurrentPlaceRequest();
    final String scoutID = currentPlaceRequest.getParameter( "id", null );
    if ( null == scoutID )
    {
      return false;
    }

    // Ability to view yourself
    if ( scoutID.equals(person.getID().toString()))
    {
      return true;
    }

    // Viewing all scouts in your Section
    return false;
  }
}
