package scoutmgr.client.ioc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.gwtplatform.mvp.shared.proxy.RouteTokenFormatter;
import javax.annotation.Nonnull;
import javax.inject.Singleton;
import org.realityforge.gwt.datatypes.client.date.DateTimeService;
import org.realityforge.gwt.datatypes.client.date.GwtDateTimeService;
import org.realityforge.replicant.client.EntityRepository;
import org.realityforge.replicant.client.gwt.ReplicantGinModule;
import org.realityforge.replicant.client.runtime.DataLoaderEntry;
import org.realityforge.replicant.client.runtime.gwt.ReplicantNetworkModule;
import scoutmgr.client.ScoutmgrApp;
import scoutmgr.client.application.ApplicationModule;
import scoutmgr.client.application.admin.AdminModule;
import scoutmgr.client.application.crash.CrashModule;
import scoutmgr.client.application.dialog.DialogModule;
import scoutmgr.client.application.events.EventsModule;
import scoutmgr.client.application.footer.FooterModule;
import scoutmgr.client.application.login.LoginModule;
import scoutmgr.client.application.navbar.NavbarModule;
import scoutmgr.client.application.scout.ScoutModule;
import scoutmgr.client.application.troop.TroopModule;
import scoutmgr.client.application.unauthorised.UnauthorisedModule;
import scoutmgr.client.net.ScoutmgrGwtDataLoaderListener;
import scoutmgr.client.net.ScoutmgrGwtDataLoaderService;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.util.GlobalAsyncCallback;

public class ScoutmgrClientModule
  extends AbstractPresenterModule
{
  private static ScoutmgrApp c_app;

  public static void setApp( @javax.annotation.Nonnull final ScoutmgrApp app )
  {
    c_app = app;
  }

  public static class AppProvider
    implements javax.inject.Provider<ScoutmgrApp>
  {
    @Override
    public ScoutmgrApp get()
    {
      if ( null == c_app )
      {
        throw new IllegalStateException( "Missing application of type ScoutmgrApp from injection" );
      }
      return c_app;
    }
  }

  @Override
  protected void configure()
  {
    bindNamedService( "GLOBAL", AsyncCallback.class, GlobalAsyncCallback.class );
    bind( FrontendContext.class ).to( FrontendContextImpl.class ).asEagerSingleton();
    bind( DateTimeService.class ).to( GwtDateTimeService.class ).asEagerSingleton();
    bind( ScoutmgrApp.class ).toProvider( AppProvider.class ).asEagerSingleton();

    // Simple panel for now, until we have a UI
    bind( SimplePanel.class ).asEagerSingleton();

    install( new ReplicantGinModule() );
    install( new ReplicantNetworkModule() );
    install( new ScoutmgrImitServicesModule() );
    install( new ScoutmgrGwtRpcServicesModule() );

    install( new DefaultModule.Builder().tokenFormatter( RouteTokenFormatter.class ).build() );
    install( new ApplicationModule() );
    install( new EventsModule() );
    install( new FooterModule() );
    install( new NavbarModule() );
    install( new DialogModule() );
    install( new CrashModule() );
    install( new AdminModule() );
    install( new LoginModule() );
    install( new UnauthorisedModule() );
    install( new ScoutModule() );
    install( new TroopModule() );

    bindConstant().annotatedWith( DefaultPlace.class ).to( NameTokens.TROOP );
    bindConstant().annotatedWith( ErrorPlace.class ).to( NameTokens.CRASH );
    bindConstant().annotatedWith( UnauthorizedPlace.class ).to( NameTokens.LOGIN );

    bind( ResourceLoader.class ).asEagerSingleton();
  }

  protected <T> void bindNamedService( final String name,
                                       final Class<T> service,
                                       final Class<? extends T> implementation )
  {
    bind( service ).annotatedWith( Names.named( name ) ).to( implementation ).asEagerSingleton();
  }

  @Provides
  @Singleton
  public final DataLoaderEntry[] getDataLoaderEntries( @Nonnull final EventBus eventBus,
                                                       @Nonnull final EntityRepository repository,
                                                       @Nonnull final ScoutmgrGwtDataLoaderService scoutmgrLoader )
  {
    final DataLoaderEntry[] dataLoaders =
      {
        new DataLoaderEntry( scoutmgrLoader, true ),
      };
    scoutmgrLoader.addDataLoaderListener( new ScoutmgrGwtDataLoaderListener( repository, eventBus ) );
    return dataLoaders;
  }

  @Provides
  @Singleton
  public com.google.gwt.event.shared.EventBus getSharedEventBus( @Nonnull final com.google.web.bindery.event.shared.EventBus eventBus )
  {
    return (EventBus) eventBus;
  }
}
