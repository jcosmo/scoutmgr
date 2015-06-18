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
import scoutmgr.client.events.EventsModule;
import scoutmgr.client.appbar.AppbarModule;
import scoutmgr.client.footer.FooterModule;
import scoutmgr.client.members.MembersModule;
import scoutmgr.client.navbar.NavbarModule;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.resource.ScoutmgrResourceBundle;
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
    install( new EventsModule() );
    install( new MembersModule() );
    install( new AppbarModule() );
    install( new FooterModule() );
    install( new NavbarModule() );

    bindConstant().annotatedWith( DefaultPlace.class ).to( NameTokens.MEMBERS );
    bindConstant().annotatedWith( ErrorPlace.class ).to( NameTokens.MEMBERS );
    bindConstant().annotatedWith( UnauthorizedPlace.class ).to( NameTokens.MEMBERS );

    bind(ResourceLoader.class).asEagerSingleton();
  }

  protected <T> void bindNamedService( final String name,
                                       final Class<T> service,
                                       final Class<? extends T> implementation )
  {
    bind( service ).annotatedWith( Names.named( name ) ).to( implementation ).asEagerSingleton();
  }
}
