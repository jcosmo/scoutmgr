package scoutmgr.server.service.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import org.apache.commons.codec.binary.Hex;
import scoutmgr.server.entity.ScoutmgrPersistenceUnit;
import scoutmgr.server.entity.security.Credential;
import scoutmgr.server.entity.security.User;
import scoutmgr.server.entity.security.dao.CredentialRepository;
import scoutmgr.server.entity.security.dao.SessionRepository;
import scoutmgr.server.entity.security.dao.UserRepository;

@Stateless( name = UserService.NAME )
public class UserServiceEJB
  implements UserService
{
  public static final int SALT_BYTES = 32;

  @PersistenceContext( unitName = ScoutmgrPersistenceUnit.NAME )
  private EntityManager _entityManager;
  @Inject
  private SessionRepository _sessionRepository;
  @Inject
  private UserRepository _userRepository;
  @Inject
  private CredentialRepository _credentialRepository;

  @Override
  @Nonnull
  public User addUser( @Nonnull final String rawUserName,
                       @Nonnull final String rawEmail,
                       @Nonnull final String password )
    throws DuplicateUserNameException
  {
    final String userName = rawUserName.toLowerCase().trim();
    final String email = rawEmail.toLowerCase().trim();
    final User existingUser = _userRepository.findByUserName( userName );
    if ( null != existingUser )
    {
      throw new DuplicateUserNameException( "User with username " + userName + " already exists" );
    }

    final User user = createUserEntity( userName, email );

    final String salt = generateSalt();
    createCredentalEntity( user.getUserName(), user, hashPassword( password, salt ), salt );

    return user;
  }

  private User createUserEntity( final String userName, final String email )
  {
    final User $_ = new User();
    $_.setUserName( userName );
    $_.setEmail( email );
    $_.setActive( true );
    _userRepository.persist( $_ );
    return $_;
  }

  @Override
  public void updateUser( final int userId,
                          @Nonnull final String rawEmail,
                          @Nullable final String password )
    throws DuplicateUserNameException
  {
    final String email = rawEmail.toLowerCase().trim();
    final User user = _userRepository.getByID( userId );
    _entityManager.lock( user, LockModeType.PESSIMISTIC_WRITE );
    user.setEmail( email );
    if ( null != password )
    {
      final String salt = generateSalt();
      final String hashedPassword = hashPassword( password, salt );
      if ( null != user.getCredential() )
      {
        user.getCredential().setPassword( hashedPassword );
        user.getCredential().setSalt( salt );
      }
      else
      {
        createCredentalEntity( user.getUserName(), user, hashedPassword, salt );
      }
    }
  }

  private Credential createCredentalEntity( final String userName,
                                            final User user,
                                            final String hashedPassword,
                                            final String salt )
  {
    final Credential $_ = new Credential( user );
    $_.setUserName( userName );
    $_.setPassword( hashedPassword );
    $_.setSalt( salt );
    _credentialRepository.persist( $_ );
    return $_;
  }

  @Override
  public void deleteUser( final int userId )
  {
    final User user = _userRepository.getByID( userId );
    _sessionRepository.deleteUserSessions( user );
    _credentialRepository.deleteUserCredentials( user );
    _userRepository.remove( user );
  }

  private String generateSalt()
  {
    final SecureRandom secureRandom = new SecureRandom();
    final byte[] bytes = new byte[ SALT_BYTES ];
    secureRandom.nextBytes( bytes );
    return Hex.encodeHexString( bytes );
  }

  private String hashPassword( final String password, final String salt )
  {
    try
    {
      final MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
      final byte[] hash = digest.digest( ( salt + password ).getBytes( StandardCharsets.UTF_8 ) );
      return Hex.encodeHexString( hash );
    }
    catch ( final NoSuchAlgorithmException e )
    {
      throw new IllegalStateException( e );
    }
  }
}
