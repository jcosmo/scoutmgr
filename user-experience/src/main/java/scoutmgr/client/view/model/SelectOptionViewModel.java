package scoutmgr.client.view.model;

public class SelectOptionViewModel
  extends AbstractViewModel
{
  private String _value;

  public String getValue()
  {
    return _value;
  }

  public void setValue( final String value )
  {
    _value = value;
  }
}
