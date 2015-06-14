package scoutmgr.client.members;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiRenderer;
import scoutmgr.client.view.model.ListItemViewModel;
import scoutmgr.client.view.model.ScoutViewModel;

public class ActionsCell
  extends AbstractCell<ScoutViewModel>
{
  interface ActionsUiRenderer
      extends UiRenderer
    {
      void render( final SafeHtmlBuilder sb );

      void onBrowserEvent( final ActionsCell cell,
                           final NativeEvent event,
                           final Element parent,
                           final ListItemViewModel viewModel,
                           final Context context );
    }

  private static final ActionsUiRenderer RENDERER = GWT.create( ActionsUiRenderer.class );

  @Override
  public void render( final Context context, final ScoutViewModel value, final SafeHtmlBuilder sb )
  {
    if ( null == value || null == value.asModelObject())
    {
      return;
    }

    RENDERER.render( sb );
  }
}
