package scoutmgr.client.ioc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.name.Names;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import org.realityforge.replicant.client.json.gwt.ReplicantGinModule;
import org.realityforge.replicant.client.transport.CacheService;
import org.realityforge.replicant.client.transport.gwt.LocalCacheService;
import scoutmgr.client.application.ApplicationModule;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.util.GlobalAsyncCallback;

public class ScoutmgrModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindNamedService( "GLOBAL", AsyncCallback.class, GlobalAsyncCallback.class );
    bind( CacheService.class ).to( LocalCacheService.class ).asEagerSingleton();
    bind( FrontendContext.class ).to( FrontendContextImpl.class ).asEagerSingleton();

    // Simple panel for now, until we have a UI
    bind( SimplePanel.class ).asEagerSingleton();

    install( new ReplicantGinModule() );
    install( new ScoutmgrImitServicesModule() );
    install( new ScoutmgrGwtRpcServicesModule() );

    install( new DefaultModule() );
    install( new ApplicationModule() );

    bindConstant().annotatedWith( DefaultPlace.class ).to( NameTokens.home );
    bindConstant().annotatedWith( ErrorPlace.class ).to( NameTokens.home );
    bindConstant().annotatedWith( UnauthorizedPlace.class ).to( NameTokens.home );
  }

  protected <T> void bindNamedService( final String name,
                                       final Class<T> service,
                                       final Class<? extends T> implementation )
  {
    bind( service ).annotatedWith( Names.named( name ) ).to( implementation ).asEagerSingleton();
  }
}
