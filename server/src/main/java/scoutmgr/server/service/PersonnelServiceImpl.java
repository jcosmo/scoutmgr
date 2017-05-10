package scoutmgr.server.service;

import java.sql.Date;
import java.time.LocalDate;
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
import scoutmgr.server.data_type.TaskCompletionUpdateDTO;
import scoutmgr.server.entity.BadgeTask;
import scoutmgr.server.entity.Person;
import scoutmgr.server.entity.TaskCompletion;
import scoutmgr.server.entity.dao.BadgeTaskRepository;
import scoutmgr.server.entity.dao.PersonRepository;
import scoutmgr.server.entity.dao.ScoutSectionRepository;
import scoutmgr.server.entity.dao.TaskCompletionRepository;
import scoutmgr.server.entity.security.User;
import scoutmgr.server.entity.security.dao.SessionRepository;
import scoutmgr.server.service.security.PermissionUtil;
import scoutmgr.server.service.security.UserService;

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

  @Inject
  private SessionRepository _sessionRepository;

  @Inject
  private UserService _userService;

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
  public void updateCompletion( final String authToken,
                                final int personID,
                                final int badgeID,
                                @Nonnull final List<TaskCompletionUpdateDTO> taskCompletionUpdateDTOs )
  {
    final User currentUser = getLoggedInUser( authToken );

    final Person person = _personRepository.getByID( personID );
    final List<TaskCompletion> existing = person.getTaskCompletions();

    final List<Integer> incomingBadgeTaskIDs =
      taskCompletionUpdateDTOs.stream().map( TaskCompletionUpdateDTO::getBadgeTaskID ).collect( Collectors.toList() );

    // Remove deleted
    final List<TaskCompletion> toRemove = existing.stream().
      filter( x -> !incomingBadgeTaskIDs.contains( x.getBadgeTask().getID() ) ).
      collect( Collectors.toList() );
    toRemove.forEach( _taskCompletionRepository::remove );

    // update existing
    final Stream<TaskCompletion> toUpdate = existing.stream().
      filter( x -> incomingBadgeTaskIDs.contains( x.getBadgeTask().getID() ) );
    toUpdate.
      forEach( x -> updateCompletion( currentUser,
                                      person,
                                      x,
                                      findCompletion( taskCompletionUpdateDTOs, x.getBadgeTask() ) ) );

    // create new
    final List<Integer> existingBadgeTaskIDs =
      existing.stream().map( x -> x.getBadgeTask().getID() ).collect( Collectors.toList() );
    taskCompletionUpdateDTOs.stream().
      filter( x -> !existingBadgeTaskIDs.contains( x.getBadgeTaskID() ) ).
      forEach( x -> createCompletion( currentUser, x, person ) );
  }

  private void createCompletion( final User currentUser, final TaskCompletionUpdateDTO x, final Person person )
  {
    final Date now = Date.valueOf( LocalDate.now() );
    final String userName = getPersonName( currentUser );

    final TaskCompletion taskCompletion = new TaskCompletion();
    taskCompletion.setBadgeTask( _badgeTaskRepository.findByID( x.getBadgeTaskID() ) );
    taskCompletion.setDateCompleted( x.getDateCompleted() );
    taskCompletion.setDateRecorded( now );
    taskCompletion.setRecordedBy( userName );
    taskCompletion.setPerson( person );
    if ( PermissionUtil.maySignOff( currentUser, person ) )
    {
      taskCompletion.setSignedBy( userName );
      taskCompletion.setDateSigned( now );
    }

    _taskCompletionRepository.persist( taskCompletion );
  }

  private String getPersonName( final User currentUser )
  {
    if ( null != currentUser.getPerson() )
    {
      return currentUser.getPerson().getFirstName() + " " + currentUser.getPerson().getLastName();
    }

    return currentUser.getUserName();
  }

  private void updateCompletion( final User currentUser,
                                 final Person person,
                                 final TaskCompletion existing,
                                 final TaskCompletionUpdateDTO update )
  {
    final Date now = Date.valueOf( LocalDate.now() );
    final String userName = getPersonName( currentUser );

    if ( !update.getDateCompleted().equals( existing.getDateCompleted() ) )
    {
      existing.setDateCompleted( update.getDateCompleted() );
      existing.setDateRecorded( now );
      existing.setRecordedBy( userName );
      if ( PermissionUtil.maySignOff( currentUser, person ) )
      {
        existing.setSignedBy( userName );
        existing.setDateSigned( now );
      }
      else
      {
        existing.setSignedBy( null );
        existing.setDateSigned( null );
      }
    }
    else if ( Boolean.TRUE.equals( update.isApplySignature() ) )
    {
      if ( PermissionUtil.maySignOff( currentUser, person ) )
      {
        existing.setSignedBy( userName );
        existing.setDateSigned( now );
      }
    }
    else if ( Boolean.TRUE.equals( update.isRemoveSignature() ) )
    {
      if ( PermissionUtil.maySignOff( currentUser, person ) )
      {
        existing.setSignedBy( null );
        existing.setDateSigned( null );
      }
    }

    _taskCompletionRepository.persist( existing );
  }

  private TaskCompletionUpdateDTO findCompletion( final List<TaskCompletionUpdateDTO> taskCompletionUpdateDTOs,
                                                  final BadgeTask badgeTask )
  {
    for ( TaskCompletionUpdateDTO taskCompletionUpdateDTO : taskCompletionUpdateDTOs )
    {
      if ( taskCompletionUpdateDTO.getBadgeTaskID() == badgeTask.getID() )
      {
        return taskCompletionUpdateDTO;
      }
    }
    return null;
  }

  @Nonnull
  private User getLoggedInUser( @Nonnull final String token )
  {
    return _sessionRepository.findByToken( token ).getUser();
  }
}
