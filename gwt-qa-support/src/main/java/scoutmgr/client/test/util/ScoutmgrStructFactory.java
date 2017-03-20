package scoutmgr.client.test.util;

import com.google.inject.Injector;
import javax.annotation.Nonnull;
import scoutmgr.client.test.util.AbstractScoutmgrStructFactory;

public class ScoutmgrStructFactory
  extends AbstractScoutmgrStructFactory
{
  public ScoutmgrStructFactory( @Nonnull final Injector injector )
  {
    super( injector );
  }
}
