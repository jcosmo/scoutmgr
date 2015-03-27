package scoutmgr.client.ioc;

import javax.inject.Inject;
import scoutmgr.client.net.ScoutmgrDataLoaderService;

public class FrontendContextImpl
  implements  FrontendContext
{
  @Inject
  ScoutmgrDataLoaderService _dataloader;

  @Override
  public void initialArrival()
  {
    _dataloader.connect( new Runnable()
    {
      @Override
      public void run()
      {
        // Load metadata
      }
    } );
  }
}
