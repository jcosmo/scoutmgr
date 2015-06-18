package scoutmgr.client.members;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
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

  public ActionsCell()
  {
    super( "click" );
  }

  @Override
  public void render( final Context context, final ScoutViewModel value, final SafeHtmlBuilder sb )
  {
    if ( null == value || null == value.asModelObject() )
    {
      return;
    }

    RENDERER.render( sb );
  }

  private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger( ActionsCell.class.getName() );

  public void editClicked( final ClickEvent e )
  {
    LOG.warning( "Edit clicked" );
  }

  @Override
  public void onBrowserEvent( final Context context,
                              final Element parent,
                              final ScoutViewModel value,
                              final NativeEvent event,
                              final ValueUpdater<ScoutViewModel> valueUpdater )
  {
    super.onBrowserEvent( context, parent, value, event, valueUpdater );
    if ( "click".equals( event.getType() ) )
    {
      LOG.warning( "Edit clicked for " + value.getFirstName() + "/" + value.getLastName() );
    }
  }
}
