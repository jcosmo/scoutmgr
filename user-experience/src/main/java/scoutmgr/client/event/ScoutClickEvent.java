package scoutmgr.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import javax.annotation.Nonnull;
import scoutmgr.client.view.model.ScoutViewModel;

public class ScoutClickEvent
  extends GwtEvent<ScoutClickEvent.Handler>
{
  public static void fire( final com.google.gwt.event.shared.HasHandlers source, final ScoutViewModel model )
  {
    source.fireEvent( new ScoutClickEvent( model ) );
  }

  public interface HasHandlers
  {
    HandlerRegistration addScoutClickEventHandler( Handler h );
  }

  public interface Handler
    extends EventHandler
  {
    void onScoutClicked( @Nonnull ScoutClickEvent event );
  }

  public static final GwtEvent.Type<Handler> TYPE = new GwtEvent.Type<>();
  private ScoutViewModel _scoutViewModel;

  public ScoutClickEvent( @Nonnull final ScoutViewModel scoutViewModel )
  {
    _scoutViewModel = scoutViewModel;
  }

  @Nonnull
  public ScoutViewModel getScoutViewModel()
  {
    return _scoutViewModel;
  }

  @Override
  public GwtEvent.Type<Handler> getAssociatedType()
  {
    return TYPE;
  }

  @Override
  protected void dispatch( final Handler handler )
  {
    handler.onScoutClicked( this );
  }

  public String toDebugString()
  {
    return toString();
  }

  public String toString()
  {
    return "Scout Clicked[" + "Scout=" + _scoutViewModel.getDisplayString() + "]";
  }
}
