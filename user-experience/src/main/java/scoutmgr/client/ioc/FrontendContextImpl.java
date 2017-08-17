package scoutmgr.client.ioc;

import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import org.realityforge.replicant.client.EntityRepository;
import org.realityforge.replicant.client.EntitySubscriptionManager;
import org.realityforge.replicant.client.net.gwt.AbstractFrontendContextImpl;
import org.realityforge.replicant.client.runtime.AreaOfInterestService;
import org.realityforge.replicant.client.runtime.ContextConverger;
import org.realityforge.replicant.client.runtime.ReplicantClientSystem;
import scoutmgr.client.entity.Person;
import scoutmgr.client.entity.security.User;
import scoutmgr.client.net.ScoutmgrGwtDataLoaderService;
import scoutmgr.client.net.ScoutmgrGwtRuntimeExtension;
import scoutmgr.client.service.DataSubscriptionService;

public class FrontendContextImpl
  extends AbstractFrontendContextImpl
  implements ScoutmgrGwtRuntimeExtension, FrontendContext
{
  final static Logger LOG = Logger.getLogger( FrontendContextImpl.class.getName() );

  private final ScoutmgrGwtDataLoaderService _dataloader;
  private final EntityRepository _entityRepository;
  private final DataSubscriptionService _dataSubscriptionService;

  private User _user;
  private Person _person;

  @Inject
  public FrontendContextImpl( @Nonnull final ContextConverger converger,
                              @Nonnull final EntityRepository repository,
                              @Nonnull final EntitySubscriptionManager subscriptionManager,
                              @Nonnull final ReplicantClientSystem replicantClientSystem,
                              @Nonnull final AreaOfInterestService areaOfInterestService,
                              @Nonnull final ScoutmgrGwtDataLoaderService dataloader,
                              @Nonnull final EntityRepository entityRepository,
                              @Nonnull final DataSubscriptionService dataSubscriptionService )
  {
    super( converger, repository, subscriptionManager, replicantClientSystem, areaOfInterestService );
    _dataloader = dataloader;
    _entityRepository = entityRepository;
    _dataSubscriptionService = dataSubscriptionService;
  }

  @Override
  protected void initialSubscriptionSetup()
  {
    // Nothing to subscribe to until there is a login
  }


  private void connectAndLoadMetadata( final Runnable postLoad )
  {
/*    _dataloader.connect(
      () ->
      {
        final String sessionID = _dataloader.getSession().getSessionID();
        final Integer userID = _loginManager.getUserID();
        assert null != userID;

        LOG.info( "Connected to data loader" );

        _dataSubscriptionService.subscribeForUser(
          sessionID,
          userID,
          ( result ) ->
          {
            LOG.info( "Subscribed to Metadata" );
            _eventBus.fireEvent( new MetadataLoadedEvent() );
            _user = _entityRepository.getByID( User.class, userID );
            LOG.info( "User record retrieved for '" + _user.getUserName() + "'." );

            if ( null != _user.getPerson() )
            {
              _person = _user.getPerson();
              LOG.info( "Person downloaded for user '" + _user.getUserName() + "'." );
            }
            else
            {
              LOG.info( "No person associated with user" );
              _person = null;
            }
            _eventBus.fireEvent( new UserLoadedEvent( _user ) );
            runIfPresent( postLoad );
          }
        );
      } );
*/
  }

  @Override
  public User getUser()
  {
    return _user;
  }

  @Override
  public Person getPerson()
  {
    return _person;
  }
}
