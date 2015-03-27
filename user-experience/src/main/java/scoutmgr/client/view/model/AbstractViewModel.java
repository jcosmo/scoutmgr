package scoutmgr.client.view.model;

import java.util.HashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AbstractViewModel
{
  @Nullable
  private Object _modelObject;
  @Nullable
  private String _displayString;
  private final HashMap<String, String> _renderAttributes = new HashMap<>();
  private boolean _filtered;

  @Nullable
  public String getDisplayString()
  {
    return _displayString;
  }

  public void setDisplayString( @Nullable final String displayString )
  {
    _displayString = displayString;
  }

  public void setModelObject( @Nullable final Object modelObject )
  {
    _modelObject = modelObject;
  }

  @Nullable
  @SuppressWarnings( "unchecked" )
  public <T> T asModelObject()
  {
    return (T) _modelObject;
  }

  @Nonnull
  public HashMap<String, String> getRenderAttributes()
  {
    return _renderAttributes;
  }

  public void addRenderAttribute( final String key, final String value )
  {
    _renderAttributes.put( key, value );
  }

  public void removeRenderAttribute( final String key )
  {
    _renderAttributes.remove( key );
  }

  public boolean hasRenderAttribute( final String key )
  {
    return _renderAttributes.containsKey( key );
  }

  public boolean isFiltered()
  {
    return _filtered;
  }

  public void setFiltered( final boolean filtered )
  {
    _filtered = filtered;
  }
}
