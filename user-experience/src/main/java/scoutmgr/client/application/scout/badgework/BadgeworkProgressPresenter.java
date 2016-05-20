package scoutmgr.client.application.scout.badgework;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import javax.inject.Inject;
import scoutmgr.client.entity.Badge;
import scoutmgr.client.entity.Person;
import scoutmgr.client.view.model.ScoutViewModel;

public class BadgeworkProgressPresenter
  extends PresenterWidget<BadgeworkProgressPresenter.View>
  implements BadgeworkProgressUiHandlers
{
  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<BadgeworkProgressUiHandlers>
  {
    void configure( ScoutViewModel scout, Badge badge );

    void close();
  }

  @Inject
  BadgeworkProgressPresenter( final EventBus eventBus,
                              final View view )
  {
    super( eventBus, view );
    getView().setUiHandlers( this );
  }

  public void configure( final Person scout, final Badge badge )
  {
    getView().configure( new ScoutViewModel( scout ), badge );
  }

  @Override
  public void onCancel()
  {
    getView().close();
  }

  @Override
  public void onSave( final ScoutViewModel model )
  {
    getView().close();
  }
}
