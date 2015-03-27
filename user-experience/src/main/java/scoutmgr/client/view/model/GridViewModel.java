package scoutmgr.client.view.model;

import java.util.LinkedList;

/**
 * A grid model representing a sequenced structure.
 */
public class GridViewModel
{
  private final LinkedList<GridColumnViewModel> _columns = new LinkedList<>();
  private final LinkedList<GridRowViewModel> _rows = new LinkedList<>();

  public LinkedList<GridColumnViewModel> getColumns()
  {
    return _columns;
  }

  public LinkedList<GridRowViewModel> getRows()
  {
    return _rows;
  }
}
