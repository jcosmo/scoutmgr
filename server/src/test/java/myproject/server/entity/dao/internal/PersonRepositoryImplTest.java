package myproject.server.entity.dao.internal;

import java.util.List;
import myproject.server.entity.Person;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class PersonRepositoryImplTest
  extends AbstractPersonRepositoryImplTest
{
  @Test
  public void findAllWhereNameLike()
    throws Exception
  {
    m.createPerson( "Bilbo" );
    m.createPerson( "Gorgon" );
    m.createPerson( "Greg" );
    m.createPerson();

    final List<Person> persons = dao().findAllWhereNameLike( "G%" );
    assertEquals( persons.size(), 2 );
  }
}
