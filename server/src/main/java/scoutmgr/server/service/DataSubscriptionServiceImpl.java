package scoutmgr.server.service;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Typed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import scoutmgr.server.data_type.PeopleFilterDTO;
import scoutmgr.server.entity.security.User;
import scoutmgr.server.entity.security.dao.UserRepository;

@ApplicationScoped
@Transactional( Transactional.TxType.REQUIRED )
@Typed( DataSubscriptionService.class )
public class DataSubscriptionServiceImpl
  implements DataSubscriptionService
{
  @Inject
  private SubscriptionService _subscriptionService;

  @Inject
  private UserRepository _userRepository;

  @Override
  public void subscribeForUser( @Nonnull final String sessionID, final int userID )
    throws BadSessionException
  {
    _subscriptionService.subscribeToMetadata( sessionID );
    final User user = _userRepository.getByID( userID );
    _subscriptionService.subscribeToUser( sessionID, user );
    _subscriptionService.subscribeToPeople( sessionID, new PeopleFilterDTO( userID ) );
  }

  @Override
  public void unsubscribeFromUser( @Nonnull final String sessionID, final int userID )
    throws BadSessionException
  {
    final User user = _userRepository.getByID( userID );
    _subscriptionService.unsubscribeFromPeople( sessionID );
    _subscriptionService.unsubscribeFromUser( sessionID, user );
    _subscriptionService.unsubscribeFromMetadata( sessionID );
  }
}
