package scoutmgr.client.ioc;

import com.google.web.bindery.event.shared.EventBus;
import java.util.logging.Logger;
import javax.inject.Inject;
import scoutmgr.client.event.MetadataLoadedEvent;
import scoutmgr.client.net.ScoutmgrDataLoaderService;

public class FrontendContextImpl
  implements  FrontendContext
{
  final static Logger LOG = Logger.getLogger( FrontendContextImpl.class.getName() );

  @Inject
  EventBus _eventBus;

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
        LOG.info( "Connected to data loader" );
        _dataloader.getSession().subscribeToMetadata( new Runnable()
        {
          @Override
          public void run()
          {
            LOG.info( "Subscribed to Metadata" );
            _eventBus.fireEvent( new MetadataLoadedEvent() );
          }
        } );
      }
    } );
  }
}
