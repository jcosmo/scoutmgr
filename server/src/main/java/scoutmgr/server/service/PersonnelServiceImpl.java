package scoutmgr.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Typed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import scoutmgr.server.data_type.PersonDTO;
import scoutmgr.server.data_type.TaskCompletionDTO;
import scoutmgr.server.entity.BadgeTask;
import scoutmgr.server.entity.Person;
import scoutmgr.server.entity.TaskCompletion;
import scoutmgr.server.entity.dao.BadgeTaskRepository;
import scoutmgr.server.entity.dao.PersonRepository;
import scoutmgr.server.entity.dao.ScoutSectionRepository;
import scoutmgr.server.entity.dao.TaskCompletionRepository;

@ApplicationScoped
@Transactional( Transactional.TxType.REQUIRED )
@Typed( PersonnelService.class )
public class PersonnelServiceImpl
  implements PersonnelService
{
  @Inject
  private PersonRepository _personRepository;

  @Inject
  private BadgeTaskRepository _badgeTaskRepository;

  @Inject
  private TaskCompletionRepository _taskCompletionRepository;

  @Inject
  private ScoutSectionRepository _scoutSectionRepository;

  @Nonnull
  public List<PersonDTO> getPeople()
  {
    return _personRepository.findAll().
      stream().
      map( person -> new PersonDTO( person.getID(),
                                    person.getScoutSection().getCode(),
                                    person.getFirstName(),
                                    person.getLastName(),
                                    person.getDob(),
                                    person.getRegistrationNumber() ) ).
      collect( Collectors.toCollection( ArrayList::new ) );
  }

  @Override
  public void addPerson( @Nonnull final PersonDTO person )
  {
    final Person p = new Person();
    p.setScoutSection( _scoutSectionRepository.getByCode( person.getScoutSection() ) );
    p.setFirstName( person.getFirstName() );
    p.setLastName( person.getLastName() );
    p.setDob( person.getDob() );
    p.setRegistrationNumber( person.getRegistrationNumber() );
    try
    {
      _personRepository.persist( p );
    }
    catch ( final Exception e )
    {
      e.printStackTrace();
    }
  }

  @Override
  public void deletePerson( final int id )
  {
    final Person person = _personRepository.getByID( id );
    _personRepository.remove( person );
  }

  @Override
  public void updatePerson( @Nonnull final PersonDTO update )
  {
    final Person person = _personRepository.getByID( update.getID() );
    person.setScoutSection( _scoutSectionRepository.getByCode( update.getScoutSection() ) );
    person.setFirstName( update.getFirstName() );
    person.setLastName( update.getLastName() );
    person.setRegistrationNumber( update.getRegistrationNumber() );
    person.setDob( update.getDob() );
    _personRepository.persist( person );
  }

  @Override
  public void updateCompletion( final int personID,
                                final int badgeID,
                                @Nonnull final List<TaskCompletionDTO> taskCompletionDTOs )
  {
    final Person person = _personRepository.getByID( personID );
    final List<TaskCompletion> existing = person.getTaskCompletions();

    final List<Integer> incomingBadgeTaskIDs =
      taskCompletionDTOs.stream().map( TaskCompletionDTO::getBadgeTaskID ).collect( Collectors.toList() );

    // Remove deleted
    final List<TaskCompletion> toRemove = existing.stream().
      filter( x -> !incomingBadgeTaskIDs.contains( x.getBadgeTask().getID() ) ).
      collect( Collectors.toList() );
    toRemove.forEach( _taskCompletionRepository::remove );

    // update existing
    final Stream<TaskCompletion> toUpdate = existing.stream().
      filter( x -> incomingBadgeTaskIDs.contains( x.getBadgeTask().getID() ) );
    toUpdate.
      forEach( x -> updateCompletion( x, findCompletion( taskCompletionDTOs, x.getBadgeTask() ) ) );

    // create new
    final List<Integer> existingBadgeTaskIDs =
      existing.stream().map( x -> x.getBadgeTask().getID() ).collect( Collectors.toList() );
    taskCompletionDTOs.stream().
      filter( x -> !existingBadgeTaskIDs.contains( x.getBadgeTaskID() ) ).
      forEach( x -> createCompletion( x, person ) );
  }

  private void createCompletion( final TaskCompletionDTO x, final Person person )
  {
    final TaskCompletion taskCompletion = new TaskCompletion();
    taskCompletion.setBadgeTask( _badgeTaskRepository.findByID( x.getBadgeTaskID() ) );
    taskCompletion.setDateCompleted( x.getDateCompleted() );
    taskCompletion.setPerson( person );
    _taskCompletionRepository.persist( taskCompletion );
  }

  private void updateCompletion( final TaskCompletion x, final TaskCompletionDTO completion )
  {
    x.setDateCompleted( completion.getDateCompleted() );
    _taskCompletionRepository.persist( x );
  }

  private TaskCompletionDTO findCompletion( final List<TaskCompletionDTO> taskCompletionDTOs,
                                            final BadgeTask badgeTask )
  {
    for ( TaskCompletionDTO taskCompletionDTO : taskCompletionDTOs )
    {
      if ( taskCompletionDTO.getBadgeTaskID() == badgeTask.getID() )
      {
        return taskCompletionDTO;
      }
    }
    return null;
  }
}
