package scoutmgr.server.service;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Typed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.realityforge.replicant.server.EntityMessage;
import org.realityforge.replicant.server.EntityMessageSet;
import scoutmgr.server.data_type.PeopleFilterDTO;
import scoutmgr.server.entity.dao.PersonRepository;
import scoutmgr.server.net.ScoutmgrGraphEncoder;
import scoutmgr.server.net.ScoutmgrSession;

@ApplicationScoped
@Transactional( Transactional.TxType.REQUIRED )
@Typed( ScoutmgrSessionContext.class )
public class ScoutmgrSessionContextImpl
  extends AbstractScoutmgrSessionContextImpl
  implements ScoutmgrSessionContext
{
  @Inject
  PersonRepository _personRepository;

  @Inject
  ScoutmgrGraphEncoder _encoder;

  @Override
  protected boolean isPeopleInteresting( @Nonnull final EntityMessage message,
                                         @Nonnull final ScoutmgrSession session,
                                         @Nonnull final PeopleFilterDTO filter )
  {
    // TODO: implement filter properly
    return true;
  }

  @Override
  public void collectForFilterChangePeople( @Nonnull final EntityMessageSet messages,
                                            @Nonnull final PeopleFilterDTO originalFilter,
                                            @Nonnull final PeopleFilterDTO currentFilter )
  {
    // never going to change filter, it's tied to a login.
  }

  @Override
  public void collectPeople( @Nonnull final EntityMessageSet messages,
                             @Nonnull final PeopleFilterDTO filter )
  {
    // TODO: implement filter properly
    _encoder.encodeObjects( messages, _personRepository.findAll());
  }
}
