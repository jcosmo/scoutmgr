package scoutmgr.client.ioc;

import com.gwtplatform.mvp.client.proxy.PlaceManager;

public interface ScoutmgrGinjector
{
  LoginManager getLoginManager();

  FrontendContext getFrontendContext();

  PlaceManager getPlaceManager();
}
