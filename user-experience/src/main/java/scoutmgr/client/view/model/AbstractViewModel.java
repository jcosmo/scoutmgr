package scoutmgr.client.view.model;

public class AbstractViewModel
{
  private Object _object;
  private String _displayString;

  public AbstractViewModel( final Object object, final String displayString )
  {
    _object = object;
    _displayString = displayString;
  }

  public String getDisplayString()
  {
    return _displayString;
  }

  @SuppressWarnings( "unchecked" )
  public <T> T asModelObject()
  {
    return (T) _object;
  }
}
