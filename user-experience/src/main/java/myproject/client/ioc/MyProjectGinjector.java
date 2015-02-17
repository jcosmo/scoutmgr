package myproject.client.ioc;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.user.client.ui.SimplePanel;
import myproject.client.net.MyprojectDataLoaderService;
import org.realityforge.replicant.client.json.gwt.ReplicantGinModule;

@GinModules( { MyprojectModule.class,
               ReplicantGinModule.class,
               MyprojectImitServicesModule.class,
               MyprojectGwtRpcServicesModule.class } )
public interface MyprojectGinjector
  extends Ginjector
{
  MyprojectDataLoaderService getDataLoaderService();

  SimplePanel getMainPanel();
}
