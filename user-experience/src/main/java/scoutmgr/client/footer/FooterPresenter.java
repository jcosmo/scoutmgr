package scoutmgr.client.footer;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;

public class FooterPresenter
  extends PresenterWidget<FooterPresenter.View>
{
  interface View
    extends com.gwtplatform.mvp.client.View
  {
  }

  @Inject
  FooterPresenter( final EventBus eventBus,
                   final View view )
  {
    super( eventBus, view );
  }
}
