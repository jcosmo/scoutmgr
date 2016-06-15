package scoutmgr.client.ioc;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import java.util.logging.Logger;
import javax.inject.Inject;
import scoutmgr.client.event.MetadataLoadedEvent;
import scoutmgr.client.net.ScoutmgrDataLoaderService;

public class FrontendContextImpl
  implements  FrontendContext
{
  final static Logger LOG = Logger.getLogger( FrontendContextImpl.class.getName() );

  boolean _loggedIn;

  @Inject
  EventBus _eventBus;

  @Inject
  PlaceManager _placeManager;

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

  @Override
  public void login()
  {
    _loggedIn = true;
    _placeManager.revealCurrentPlace();
  }

  @Override
  public void logout()
  {
    _loggedIn = false;
    _placeManager.revealCurrentPlace();
  }

  public boolean isLoggedIn()
  {
    return _loggedIn;
  }
}

