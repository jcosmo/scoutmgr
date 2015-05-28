package scoutmgr.client.members;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiRenderer;
import scoutmgr.client.entity.Person;
import scoutmgr.client.view.model.ListItemViewModel;

public class ScoutCell
  extends AbstractCell<ListItemViewModel>
{
  interface ScoutUiRenderer
      extends UiRenderer
    {
      void render( final SafeHtmlBuilder sb, final SafeHtml firstName, final SafeHtml lastName );

      void onBrowserEvent( final ScoutCell cell,
                           final NativeEvent event,
                           final Element parent,
                           final ListItemViewModel viewModel,
                           final Context context );
    }

  private static final ScoutUiRenderer RENDERER = GWT.create( ScoutUiRenderer.class );

  @Override
  public void render( final Context context, final ListItemViewModel value, final SafeHtmlBuilder sb )
  {
    if ( null == value || null == value.asModelObject())
    {
      return;
    }

    final Person person = value.asModelObject();
    RENDERER.render( sb, SafeHtmlUtils.fromString( person.getFirstName() ),
                     SafeHtmlUtils.fromString( person.getLastName() ) );
  }
}
