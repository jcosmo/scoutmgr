package myproject.server.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import javax.inject.Inject;
import myproject.server.data_type.PersonDTO;
import myproject.server.entity.Person;
import myproject.server.entity.dao.PersonRepository;

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
      results.add( new PersonDTO( person.getID(), person.getName() ) );
    }
    return results;
  }
}
