package scoutmgr.server.service.security;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import scoutmgr.server.data_type.security.TokenDTO;
import scoutmgr.server.entity.dao.PersonRepository;
import scoutmgr.server.entity.security.Session;
import scoutmgr.server.entity.security.User;
import scoutmgr.server.entity.security.dao.SessionRepository;
import scoutmgr.server.entity.security.dao.UserRepository;

@Stateless( name = AuthenticationService.NAME )
public class AuthenticationServiceEJB
  implements AuthenticationService
{
  @Inject
  private UserRepository _userRepository;
  @Inject
  private PersonRepository _personRepository;
  @Inject
  private SessionRepository _sessionRepository;

  private static final int SESSION_DURATION = 2;

  @Override
  public void logout( @Nonnull final String token )
  {
    final Session session = _sessionRepository.findByToken( token );
    if ( null != session )
    {
      _sessionRepository.remove( session );
    }
  }

  @Override
  @Nullable
  public Integer reAuthenticate( @Nonnull final String token )
  {
    final Session session = _sessionRepository.findByToken( token );
    if ( null == session )
    {
      return null;
    }
    else if ( !isSessionValid( session ) )
    {
      _sessionRepository.remove( session );
      return null;
    }
    else
    {
      session.setUpdatedAt( new Date() );
      return session.getUser().getID();
    }
  }

  @Override
  @Nullable
  public TokenDTO authenticate( @Nonnull final HttpServletRequest servlet,
                                @Nonnull final String username,
                                @Nonnull final String password )
  {
    final User user = _userRepository.findByUserName( username );
    if ( null != user )
    {
      final String salt = null != user.getCredential() ? user.getCredential().getSalt() : "";
      if ( checkLogin( servlet, username, salt +  password ) )
      {
        final Session session = createSession( user );
        return new TokenDTO( user.getID(), session.getToken() );
      }
    }

    return null;
  }

  private Session createSession( final User user )
  {
    final Session session = new Session( user, new Date(), UUID.randomUUID().toString() );
    session.setUpdatedAt( session.getCreatedAt() );
    _sessionRepository.persist( session );
    return session;
  }

  private boolean isSessionValid( @Nonnull final Session session )
  {
    final Calendar calendar = Calendar.getInstance();
    calendar.add( Calendar.HOUR, -SESSION_DURATION );
    final Date expiryTime = calendar.getTime();
    final Date updatedAt = session.getUpdatedAt();
    return !updatedAt.before( expiryTime );
  }

  private boolean checkLogin( final HttpServletRequest servlet, final String username, final String password )
  {
    try
    {
      servlet.login( username, password );
      return true;
    }
    catch ( final ServletException e )
    {
      return false;
    }
  }
}
