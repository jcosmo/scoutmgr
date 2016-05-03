package scoutmgr.client.view.cell;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.DOM;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialIcon;
import scoutmgr.client.view.model.AbstractViewModel;

public class ActionCell<T extends AbstractViewModel>
  extends AbstractCell<T>
{
  public static final IconType DELETE_ACTION = IconType.CLEAR;
  public static final IconType EDIT_ACTION = IconType.EDIT;
  public static final IconType VIEW_ACTION = IconType.VISIBILITY;
  private UiActionHandler<T> _uiHandler;

  private final boolean _view;
  private boolean _text;
  private boolean _edit;
  private boolean _delete;

  public interface UiActionHandler<T>
  {
    void onView( T viewModel );

    void onEdit( T viewModel );

    void onDelete( T viewModel );
  }

  public ActionCell( final UiActionHandler<T> uiHandler,
                     final boolean canView,
                     final boolean canEdit,
                     final boolean canDelete,
                     final boolean showText )
  {
    super( BrowserEvents.CLICK );
    _uiHandler = uiHandler;
    _view = canView;
    _delete = canDelete;
    _edit = canEdit;
    _text = showText;
  }

  @Override
  public void render( final Context context, final T viewModel, final SafeHtmlBuilder sb )
  {
    if ( null == viewModel )
    {
      return;
    }


    if ( _view )
    {
      sb.appendHtmlConstant( DOM.toString( makeButton( _text ? "View" : null, VIEW_ACTION, "blue darken-2",
                                                       !_edit && !_delete ).getElement() ) );
    }
    if ( _edit )
    {
      sb.appendHtmlConstant( DOM.toString( makeButton( _text ? "Edit" : null, EDIT_ACTION, "green darken-2",
                                                       !_delete ).getElement() ) );
    }
    if ( _delete )
    {
      sb.appendHtmlConstant(
        DOM.toString( makeButton( _text ? "Delete" : null, DELETE_ACTION, "red darken-4", true ).getElement() ) );
    }
  }

  private MaterialIcon makeButton( final String text,
                                   final IconType iconType,
                                   final String iconColor, final boolean lastButton )
  {
    MaterialIcon mb = new MaterialIcon();
    if ( null != text )
    {
      mb.setText( text );
    }

    mb.setIconColor( iconColor );
    mb.setIconSize( IconSize.SMALL );
    mb.setIconType( iconType );
    mb.setCircle( true );
    mb.setWaves( WavesType.DEFAULT );
    mb.addStyleName( "action-" + iconType );
    if ( !lastButton )
    {
      mb.setPaddingRight( 5 );
    }
    return mb;
  }

  @Override
  public void onBrowserEvent( final Context context,
                              final Element parent,
                              final T value,
                              final NativeEvent event,
                              final ValueUpdater<T> valueUpdater )
  {
    super.onBrowserEvent( context, parent, value, event, valueUpdater );
    event.stopPropagation();
    event.preventDefault();
    final Element element = Element.as( event.getEventTarget() );
    if ( element.hasClassName( "action-" + VIEW_ACTION ) )
    {
      _uiHandler.onView( value );
    }
    if ( element.hasClassName( "action-" + EDIT_ACTION ) )
    {
      _uiHandler.onEdit( value );
    }
    else if ( element.hasClassName( "action-" + DELETE_ACTION ) )
    {
      _uiHandler.onDelete( value );
    }
  }
}
