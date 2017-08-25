package scoutmgr.client.ioc;

public interface ScoutmgrGinjector
{
  LoginManager getLoginManager();

  FrontendContext getFrontendContext();
}
