package scoutmgr.client.ioc;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.name.Names;
import com.google.web.bindery.event.shared.EventBus;
import org.realityforge.gwt.lognice.LoggingEventBus;
import org.realityforge.replicant.client.transport.CacheService;
import org.realityforge.replicant.client.transport.gwt.LocalCacheService;
import scoutmgr.client.util.GlobalAsyncCallback;

public class ScoutmgrModule
  extends AbstractGinModule
{
  @Override
  protected void configure()
  {
    bindNamedService( "GLOBAL", AsyncCallback.class, GlobalAsyncCallback.class );
    bind( EventBus.class ).to( LoggingEventBus.class ).asEagerSingleton();
    bind( CacheService.class ).to( LocalCacheService.class ).asEagerSingleton();

    // Simple panel for now, until we have a UI
    bind( SimplePanel.class ).asEagerSingleton();
  }

  /*
  Double bind, why does the dev template do this?
  Provider here, and bound in the configure method above

  @Provides
  @Singleton
  public com.google.web.bindery.event.shared.EventBus getSharedEventBus( final EventBus eventBus )
  {
    return eventBus;
  }
  */

  protected <T> void bindNamedService( final String name,
                                       final Class<T> service,
                                       final Class<? extends T> implementation )
  {
    bind( service ).annotatedWith( Names.named( name ) ).to( implementation ).asEagerSingleton();
  }
}
