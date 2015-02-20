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
import scoutmgr.server.data_type.PersonStatus;
import scoutmgr.server.data_type.PersonStatus2;
import scoutmgr.server.entity.Person;
import scoutmgr.shared.net.ScoutmgrReplicationGraph;
import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertNull;

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

    final Person bilbo = s.createPerson( "bilbo", null, PersonStatus2.COMMENCED );
    final Map<String, Serializable> simpleEncodeResult = ScoutmgrJpaEncoder.encodePerson( bilbo );
    assertEquals( simpleEncodeResult.get( "Name" ), "bilbo" );
    assertNull( simpleEncodeResult.get( "Status" ) );
    assertEquals( simpleEncodeResult.get( "Status2" ), "COMMENCED" );

    final Connection connection = _em.unwrap( Connection.class );

    final String sql = "SELECT * FROM \"Scoutmgr\".\"tblPerson\"";

    final PreparedStatement statement = connection.prepareStatement( sql );
    ChangeSet changeSet = new ChangeSet();

    ScoutmgrJpaEncoder.encodePerson( changeSet, ScoutmgrReplicationGraph.PERSON, statement, null );
    Map<String, Serializable> dbEncodeResult = getStringSerializableMap( changeSet );
    assertEquals( dbEncodeResult.get( "Name" ), "bilbo" );
    assertNull( dbEncodeResult.get( "Status" ) );
    assertEquals( dbEncodeResult.get( "Status2" ), "COMMENCED" );

    bilbo.setStatus( PersonStatus.CANDIDATE );
    assertEquals( ScoutmgrJpaEncoder.encodePerson( bilbo ).get( "Status" ), PersonStatus.CANDIDATE.ordinal() );
    s(scoutmgr.server.entity.dao.PersonRepository.class).persist( bilbo );

    changeSet = new ChangeSet();
    ScoutmgrJpaEncoder.encodePerson( changeSet, ScoutmgrReplicationGraph.PERSON, statement, null );
    dbEncodeResult = getStringSerializableMap( changeSet );
    assertEquals( dbEncodeResult.get( "Status" ), PersonStatus.COMPLETED.ordinal() );

    statement.close();
  }

  private Map<String, Serializable> getStringSerializableMap( final ChangeSet changeSet )
  {
    final Collection<Change> changes = changeSet.getChanges();
    final Change[] changesArray = changes.toArray( new Change[ changes.size() ] );
    return changesArray[ 0 ].getEntityMessage().getAttributeValues();
  }
}
