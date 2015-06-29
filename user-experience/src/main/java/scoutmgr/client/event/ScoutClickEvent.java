package scoutmgr.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import javax.annotation.Nonnull;
import scoutmgr.client.view.model.ScoutViewModel;

public class ScoutClickEvent
  extends GwtEvent<ScoutClickEvent.Handler>
{
  public enum ACTION
  {
    EDIT,
    DELETE
  }

  public static void fireEdit( final com.google.gwt.event.shared.HasHandlers source, final ScoutViewModel model )
  {
    source.fireEvent( new ScoutClickEvent( model, ACTION.EDIT ) );
  }

  public static void fireDelete( final com.google.gwt.event.shared.HasHandlers source, final ScoutViewModel model )
  {
    source.fireEvent( new ScoutClickEvent( model, ACTION.DELETE ) );
  }

  public interface HasHandlers
  {
    HandlerRegistration addScoutClickEventHandler( Handler h );
  }

  public interface Handler
    extends EventHandler
  {
    void onScoutDelete( @Nonnull ScoutClickEvent event );
    void onScoutEdit( @Nonnull ScoutClickEvent event );
  }

  public static final GwtEvent.Type<Handler> TYPE = new GwtEvent.Type<>();
  private final ScoutViewModel _scoutViewModel;
  private final ACTION _action;

  public ScoutClickEvent( @Nonnull final ScoutViewModel scoutViewModel,
                          @Nonnull final ACTION action)
  {
    _scoutViewModel = scoutViewModel;
    _action = action;
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
    if ( ACTION.EDIT == _action )
    {
      handler.onScoutEdit( this );
    }
    else if ( ACTION.DELETE == _action )
    {
      handler.onScoutDelete( this );
    }
  }

  public String toDebugString()
  {
    return toString();
  }

  public String toString()
  {
    return "Scout " + _action +  " [" + "Scout=" + _scoutViewModel.getDisplayString() + "]";
  }
}
