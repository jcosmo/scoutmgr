package scoutmgr.client.ioc;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.user.client.ui.SimplePanel;
import scoutmgr.client.net.ScoutmgrDataLoaderService;
import org.realityforge.replicant.client.json.gwt.ReplicantGinModule;

@GinModules( { ScoutmgrModule.class,
               ReplicantGinModule.class,
               ScoutmgrImitServicesModule.class,
               ScoutmgrGwtRpcServicesModule.class } )
public interface ScoutmgrGinjector
  extends Ginjector
{
  ScoutmgrDataLoaderService getDataLoaderService();

  SimplePanel getMainPanel();
}
