package scoutmgr.server.service;

import java.util.List;
import scoutmgr.server.data_type.PersonDTO;
import scoutmgr.server.data_type.PersonStatus;
import scoutmgr.server.entity.Person;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class PersonnelServiceEJBTest
  extends AbstractPersonnelServiceEJBTest
{
  @Test
  public void getPeople()
  {
    final Person person = s.createPerson( "Bob", PersonStatus.COMPLETED );
    final List<PersonDTO> people = service().getPeople();
    assertEquals( people.size(), 1 );
    final PersonDTO personDTO = people.get( 0 );
    assertEquals( personDTO.getName(), person.getName() );
  }
}
