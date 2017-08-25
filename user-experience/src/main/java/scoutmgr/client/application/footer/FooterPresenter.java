package scoutmgr.client.application.footer;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import javax.inject.Inject;

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
