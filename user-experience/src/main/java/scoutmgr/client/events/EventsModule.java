package scoutmgr.client.events;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class EventsModule
  extends AbstractPresenterModule
{
  @Override
  protected void configure()
  {
    bindPresenter( EventsPresenter.class, EventsPresenter.View.class, EventsView.class,
                   EventsPresenter.Proxy.class );
  }
}
