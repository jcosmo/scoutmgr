package myproject.server.test.util;

import myproject.server.entity.MyprojectPersistenceUnit;
import org.realityforge.guiceyloops.server.glassfish.GlassFishContainer;
import org.realityforge.guiceyloops.server.glassfish.GlassFishContainerUtil;

public final class AppServer
{
  private static final String APP_NAME = "myproject";
  private static final String CONTEXT_ROOT = "/" + APP_NAME;
  private static GlassFishContainer c_glassfish;
  private static String c_baseHttpURL;

  private AppServer()
  {
  }

  protected static String getServiceURL( final String service )
  {
    return c_baseHttpURL + "/api/soap/Myproject/Myproject/" + service;
  }

  protected static String getSiteURL()
  {
    return c_baseHttpURL + CONTEXT_ROOT;
  }

  static void setUpAppServer()
    throws Exception
  {
    if ( null == c_glassfish )
    {
      c_glassfish = new GlassFishContainer();
      c_glassfish.start();
      c_glassfish.createSqlServerJdbcResource( MyprojectPersistenceUnit.RESOURCE_NAME );
      c_glassfish.deploy( CONTEXT_ROOT, APP_NAME, GlassFishContainerUtil.getWarFile() );

      c_baseHttpURL = c_glassfish.getBaseHttpURL();
    }
  }

  static void tearDownAppServer()
  {
    if ( null != c_glassfish )
    {
      c_glassfish.stop();
      c_glassfish = null;
      c_baseHttpURL = null;
    }
  }
}
