package scoutmgr.client.ioc;

import com.google.gwt.inject.client.Ginjector;
import scoutmgr.client.net.ScoutmgrDataLoaderService;

public interface ScoutmgrGinjector
{
  FrontendContext getFrontendContext();
}
