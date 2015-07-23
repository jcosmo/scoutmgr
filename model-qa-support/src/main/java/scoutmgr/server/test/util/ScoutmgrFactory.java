package scoutmgr.server.test.util;

import com.google.inject.Injector;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nonnull;
import scoutmgr.server.entity.Person;

public class ScoutmgrFactory
  extends AbstractScoutmgrFactory
{
  private static final int MAX_STRING_LENGTH = 20;
  public static final Random RANDOM = new Random();

  public ScoutmgrFactory( @Nonnull final ScoutmgrFactorySet factorySet,
                           @Nonnull final Injector injector )
  {
    super( factorySet, injector );
  }

  public Person createPerson()
  {
    return createPerson( randomString(), randomString() );
  }

  public Person createPerson( @Nonnull final String fname, @Nonnull final String lname )
  {
    return createPerson( fname, lname, now(), randomString(), createScoutLevel( "Scout", 2 ) );
  }

  public Date now()
  {
    // Our database does not support milliseconds so lets remove
    // them to make testing equality easier
    final long millisToSeconds = 1000L;
    return new Date( ( System.currentTimeMillis() / millisToSeconds ) * millisToSeconds );
  }

  public long randomLong()
  {
    return RANDOM.nextLong();
  }

  public int randomInt()
  {
    return RANDOM.nextInt();
  }

  public String randomString()
  {
    final String randomUUID = UUID.randomUUID().toString();
    return randomUUID.substring( 0, Math.min( MAX_STRING_LENGTH, randomUUID.length() ) );
  }
}
