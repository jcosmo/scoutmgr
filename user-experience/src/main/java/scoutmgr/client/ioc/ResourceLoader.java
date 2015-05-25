package scoutmgr.client.ioc;

import javax.inject.Inject;
import scoutmgr.client.resource.ScoutmgrResourceBundle;

public class ResourceLoader
{
  @Inject
  ResourceLoader( final ScoutmgrResourceBundle resources )
  {
    resources.bootstrap().ensureInjected();
    resources.scoutmgr().ensureInjected();
  }
}
