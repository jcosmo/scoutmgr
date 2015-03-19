package scoutmgr.client.ioc;

import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import scoutmgr.client.application.ApplicationModule;
import scoutmgr.client.place.NameTokens;

public class ScoutmgrMVPModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    install( new DefaultModule() );
    install( new ApplicationModule() );

    bindConstant().annotatedWith( DefaultPlace.class ).to( NameTokens.home );
    bindConstant().annotatedWith( ErrorPlace.class ).to( NameTokens.home );
    bindConstant().annotatedWith( UnauthorizedPlace.class ).to( NameTokens.home );
  }
}
