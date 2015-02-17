package myproject.api;

import java.util.List;
import javax.xml.ws.BindingProvider;
import myproject.api.personnel.PersonDTO;
import myproject.api.personnel.PersonnelService;
import myproject.api.personnel.PersonnelServiceService;
import myproject.server.data_type.PersonStatus;
import myproject.server.test.util.AbstractGlassFishTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class PersonnelServiceTest
  extends AbstractGlassFishTest
{
  private PersonnelService _service;

  @BeforeMethod
  @Override
  public void preTest()
    throws Exception
  {
    super.preTest();
    _service = new PersonnelServiceService().getPersonnelServicePort();
    ( (BindingProvider) _service ).getRequestContext().
      put( BindingProvider.ENDPOINT_ADDRESS_PROPERTY, getServiceURL( "PersonnelService" ) );
  }

  @Test
  public void simpleWorkflow()
    throws Exception
  {
    {
      final List<PersonDTO> people = _service.getPeople();
      assertEquals( people.size(), 0 );
    }

    em().getTransaction().begin();
    m.createPerson( "Bob", PersonStatus.CANDIDATE );
    em().getTransaction().commit();

    {
      final List<PersonDTO> people = _service.getPeople();
      assertEquals( people.size(), 1 );
      assertEquals( people.get( 0 ).getName(), "Bob" );
    }
  }
}
