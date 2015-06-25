package scoutmgr.client.members;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiRenderer;
import scoutmgr.client.event.ScoutClickEvent;
import scoutmgr.client.view.model.ScoutViewModel;

public class ActionsCell
  extends AbstractCell<ScoutViewModel>
{
  private final SimpleEventBus _eventBus = new SimpleEventBus();

  interface ActionsUiRenderer
    extends UiRenderer
  {
    void render( final SafeHtmlBuilder sb );

    void onBrowserEvent( final ActionsCell cell,
                         final NativeEvent event,
                         final Element parent,
                         final ScoutViewModel viewModel );
  }

  private static final ActionsUiRenderer RENDERER = GWT.create( ActionsUiRenderer.class );

  public ActionsCell()
  {
    super( BrowserEvents.CLICK );
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

  @Override
  public void onBrowserEvent( final Context context,
                              final Element parent,
                              final ScoutViewModel value,
                              final NativeEvent event,
                              final ValueUpdater<ScoutViewModel> valueUpdater )
  {
    RENDERER.onBrowserEvent( this, event, parent, value );
    if ( BrowserEvents.CLICK.equals( event.getType() ) )
    {
      _eventBus.fireEvent( new ScoutClickEvent( value ) );
      LOG.warning( "Edit clicked for " + value.getFirstName() + "/" + value.getLastName() );
    }
  }

  private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger( ActionsCell.class.getName() );

  @SuppressWarnings( "GwtUiHandlerErrors" )
  @UiHandler( "_editLink" )
  void editLinkClicked( final ClickEvent event, final Element parent, final ScoutViewModel viewModel )
  {
    LOG.warning( "Edit scout clicked" );
    _eventBus.fireEvent( new ScoutClickEvent( viewModel ) );
  }

  public HandlerRegistration addClickEventHandler( final ScoutClickEvent.Handler handler )
  {
    return _eventBus.addHandler( ScoutClickEvent.TYPE, handler );
  }
}