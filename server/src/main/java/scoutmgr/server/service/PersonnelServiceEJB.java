package scoutmgr.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import javax.inject.Inject;
import scoutmgr.server.data_type.PersonDTO;
import scoutmgr.server.entity.Person;
import scoutmgr.server.entity.dao.PersonRepository;

@Stateless( name = PersonnelService.NAME )
public class PersonnelServiceEJB
  implements PersonnelService
{
  @Inject
  private PersonRepository _personRepository;

  @Nonnull
  public List<PersonDTO> getPeople()
  {
    final ArrayList<PersonDTO> results = new ArrayList<>();
    for ( final Person person : _personRepository.findAll() )
    {
      results.add( new PersonDTO( person.getID(), person.getFirstName(), person.getLastName(), person.getDob(), person.getRegistrationNumber() ) );
    }
    return results;
  }

  @Override
  public void addScout( @Nonnull final String firstName, @Nonnull final String lastName, @Nonnull final Date dob, @Nonnull final String registrationNumber)
  {
    Person p = new Person();
    p.setFirstName( firstName );
    p.setLastName( lastName );
    p.setDob( dob );
    p.setRegistrationNumber( registrationNumber );
    try
    {
      _personRepository.persist( p );
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }
  }

  @Override
  public void deleteScout( final int id )
  {
    final Person person = _personRepository.getByID( id );
    _personRepository.remove( person );
  }
}
