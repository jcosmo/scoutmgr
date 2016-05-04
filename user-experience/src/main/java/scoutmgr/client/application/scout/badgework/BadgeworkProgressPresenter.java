package scoutmgr.client.application.scout.badgework;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import javax.inject.Inject;
import scoutmgr.client.entity.Badge;
import scoutmgr.client.entity.Person;

public class BadgeworkProgressPresenter
  extends PresenterWidget<BadgeworkProgressPresenter.View>
{
  interface View
    extends com.gwtplatform.mvp.client.View
  {
    void configure( Person scout, Badge badge );
  }

  @Inject
  BadgeworkProgressPresenter( final EventBus eventBus,
                              final View view )
  {
    super( eventBus, view );
  }

  public void configure( final Person scout, final Badge badge )
  {
    getView().configure( scout, badge );
  }
}
