package scoutmgr.client.ioc;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.user.client.ui.SimplePanel;
import org.realityforge.replicant.client.json.gwt.ReplicantGinModule;
import scoutmgr.client.net.ScoutmgrDataLoaderService;

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
