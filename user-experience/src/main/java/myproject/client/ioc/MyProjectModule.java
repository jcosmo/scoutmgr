package myproject.client.ioc;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import javax.inject.Singleton;
import myproject.client.util.GlobalAsyncCallback;
import org.realityforge.gwt.lognice.LoggingEventBus;
import org.realityforge.replicant.client.transport.CacheService;
import org.realityforge.replicant.client.transport.gwt.LocalCacheService;

public class MyprojectModule
  extends AbstractGinModule
{
  @Override
  protected void configure()
  {
    bindNamedService( "GLOBAL", AsyncCallback.class, GlobalAsyncCallback.class );
    bind( EventBus.class ).to( LoggingEventBus.class ).asEagerSingleton();
    bind( CacheService.class ).to( LocalCacheService.class ).asEagerSingleton();
    bind( SimplePanel.class ).asEagerSingleton();
  }

  @Provides
  @Singleton
  public com.google.web.bindery.event.shared.EventBus getSharedEventBus( final EventBus eventBus )
  {
    return eventBus;
  }


  protected <T> void bindNamedService( final String name,
                                       final Class<T> service,
                                       final Class<? extends T> implementation )
  {
    bind( service ).annotatedWith( Names.named( name ) ).to( implementation ).asEagerSingleton();
  }
}
