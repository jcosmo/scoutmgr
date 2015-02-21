package scoutmgr.server.entity.dao.internal;

import java.util.List;
import org.testng.annotations.Test;
import scoutmgr.server.entity.Person;
import static org.testng.Assert.*;

public class PersonRepositoryImplTest
  extends AbstractPersonRepositoryImplTest
{
  @Test
  public void findAllWhereNameLike()
    throws Exception
  {
    s.createPerson( "Bilbo" );
    s.createPerson( "Gorgon" );
    s.createPerson( "Greg" );
    s.createPerson();

    final List<Person> persons = dao().findAllWhereNameLike( "G%" );
    assertEquals( persons.size(), 2 );
  }
}
