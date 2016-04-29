package scoutmgr.client.view.cell;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiRenderer;
import com.google.gwt.user.client.DOM;
import gwt.material.design.client.base.MaterialButtonCell;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import scoutmgr.client.resource.ScoutmgrResourceBundle;
import scoutmgr.client.view.model.AbstractViewModel;

public class ActionCell<T extends AbstractViewModel>
  extends AbstractCell<T>
{
  private final boolean _view;
  private UiActionHandler<T> _uiHandler;
  private scoutmgr.client.resource.ScoutmgrResourceBundle _bundle;
  private boolean _text;
  private boolean _edit;
  private boolean _delete;

  public interface UiActionHandler<T>
  {
    void onView( T viewModel );

    void onEdit( T viewModel );

    void onDelete( T viewModel );
  }

  public interface ActionStyle
    extends CssResource
  {
    String edit();

    String delete();

    String view();
  }

  interface Renderer
    extends UiRenderer
  {
    void render( SafeHtmlBuilder sb, String text, String css, gwt.material.design.client.constants.IconType iconType );

    ActionStyle getActionStyles();
  }

  private static final Renderer RENDERER = GWT.create( Renderer.class );

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
    _text = true || showText;
  }

  public void setBundle( final ScoutmgrResourceBundle bundle )
  {
    _bundle = bundle;
  }

  @Override
  public void render( final Context context, final T viewModel, final SafeHtmlBuilder sb )
  {
    MaterialButton mb = new MaterialButton( ButtonType.RAISED);
    mb.setText("Show log");
    mb.setBackgroundColor("blue");
    mb.setWaves( WavesType.LIGHT);
    mb.setIconType(IconType.POLYMER);
    mb.setIconPosition( IconPosition.LEFT);

    sb.appendHtmlConstant( DOM.toString( mb.getElement() ));

/*
    if ( _view )
    {
      RENDERER.render( sb, _text ? "View" : "", "btn btn-link " + RENDERER.getActionStyles().view(),
                       IconType.FOLDER );
    }
    if ( _edit )
    {
      RENDERER.render( sb, _text ? "Edit" : "", "btn btn-link " + RENDERER.getActionStyles().edit(),
                       IconType.EDIT );
    }
    if ( _delete )
    {
      RENDERER.render( sb, _text ? "Delete" : "", "btn btn-link " + RENDERER.getActionStyles().delete(),
                       IconType.DELETE );
    }
*/
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
    if ( element.hasClassName( RENDERER.getActionStyles().view() ) )
    {
      _uiHandler.onView( value );
    }
    if ( element.hasClassName( RENDERER.getActionStyles().edit() ) )
    {
      _uiHandler.onEdit( value );
    }
    else if ( element.hasClassName( RENDERER.getActionStyles().delete() ) )
    {
      _uiHandler.onDelete( value );
    }
  }
}
