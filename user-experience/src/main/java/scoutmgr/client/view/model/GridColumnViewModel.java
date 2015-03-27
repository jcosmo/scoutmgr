package scoutmgr.client.view.model;

import java.util.LinkedList;

public class GridColumnViewModel
  extends AbstractViewModel
{
  private final LinkedList<GridColumnViewModel> _columns = new LinkedList<>();
  private Integer _width;

  public LinkedList<GridColumnViewModel> getColumns()
  {
    return _columns;
  }

  public Integer getWidth()
  {
    return _width;
  }

  public void setWidth( final Integer width )
  {
    _width = width;
  }
}
