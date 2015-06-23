package scoutmgr.server.net;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import org.realityforge.replicant.server.Change;
import org.realityforge.replicant.server.ChangeSet;
import org.testng.annotations.Test;
import scoutmgr.server.entity.Person;
import scoutmgr.shared.net.ScoutmgrReplicationGraph;
import static org.testng.Assert.*;

public class ScoutmgrJpaEncoderTest
  extends AbstractScoutmgrSessionContextEJBTest
{
  @javax.persistence.PersistenceContext( unitName = scoutmgr.server.entity.ScoutmgrPersistenceUnit.NAME )
  javax.persistence.EntityManager _em;

  @Test
  void testStuff()
    throws SQLException
  {
    getInjector().injectMembers( this );

    final Person bilbo = s.createPerson( "bilbo", "baggins" );
    final Map<String, Serializable> simpleEncodeResult = ScoutmgrJpaEncoder.encodePerson( bilbo );
    assertEquals( simpleEncodeResult.get( "FirstName" ), "bilbo" );
    assertEquals( simpleEncodeResult.get( "LastName" ), "baggins" );
    assertNotNull( simpleEncodeResult.get( "Dob" ) );
    assertNotNull( simpleEncodeResult.get( "RegistrationNumber" ) );

    final Connection connection = _em.unwrap( Connection.class );

    final String sql = "SELECT * FROM \"Scoutmgr\".\"tblPerson\"";

    final PreparedStatement statement = connection.prepareStatement( sql );
    ChangeSet changeSet = new ChangeSet();

    ScoutmgrJpaEncoder.encodeScoutmgrPerson( changeSet, ScoutmgrReplicationGraph.PERSON, statement, null );
    Map<String, Serializable> dbEncodeResult = getStringSerializableMap( changeSet );
    assertEquals( dbEncodeResult.get( "FirstName" ), "bilbo" );
    assertEquals( dbEncodeResult.get( "LastName" ), "baggins" );
    assertEquals( simpleEncodeResult.get( "Dob" ), dbEncodeResult.get( "Dob" ) );
    assertEquals( simpleEncodeResult.get( "RegistrationNumber" ), dbEncodeResult.get( "RegistrationNumber" ) );

    statement.close();
  }

  private Map<String, Serializable> getStringSerializableMap( final ChangeSet changeSet )
  {
    final Collection<Change> changes = changeSet.getChanges();
    final Change[] changesArray = changes.toArray( new Change[ changes.size() ] );
    return changesArray[ 0 ].getEntityMessage().getAttributeValues();
  }
}
