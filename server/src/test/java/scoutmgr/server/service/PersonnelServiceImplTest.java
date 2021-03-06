package scoutmgr.server.service;

import java.util.List;
import org.testng.annotations.Test;
import scoutmgr.server.data_type.PersonDTO;
import scoutmgr.server.entity.Person;
import static org.testng.Assert.*;

public class PersonnelServiceImplTest
  extends AbstractPersonnelServiceImplTest
{
  @Test
  public void getPeople()
  {
    final Person person = sc.createPerson( "Bob", "Brown" );
    final List<PersonDTO> people = service().getPeople();
    assertEquals( people.size(), 1 );
    final PersonDTO personDTO = people.get( 0 );
    assertEquals( personDTO.getFirstName(), person.getFirstName() );
    assertEquals( personDTO.getLastName(), person.getLastName() );
    assertEquals( personDTO.getDob(), person.getDob() );
    assertEquals( personDTO.getRegistrationNumber(), person.getRegistrationNumber() );
  }

  @Override
  public void addPerson()
    throws Exception
  {

  }

  @Override
  public void updatePerson()
    throws Exception
  {

  }

  @Override
  public void updateCompletion()
    throws Exception
  {

  }

  @Override
  public void deletePerson()
    throws Exception
  {

  }
}
