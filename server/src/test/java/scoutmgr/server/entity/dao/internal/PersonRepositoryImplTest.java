package scoutmgr.server.entity.dao.internal;

import java.util.List;
import org.testng.annotations.BeforeMethod;
import scoutmgr.server.entity.Person;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.postgresql.Driver;

public class PersonRepositoryImplTest
  extends AbstractPersonRepositoryImplTest
{
  @BeforeMethod
  @Override
  public void preTest()
    throws Exception
  {
    System.setProperty( "test.db.driver", Driver.class.getName() );
    super.preTest();
  }

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
