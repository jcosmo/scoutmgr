package scoutmgr.client.view.model;

import javax.annotation.Nullable;

public final class GridItemViewModel
  extends AbstractViewModel
{
  @Nullable
  private Object _columnIdentifier;
  private GridRowViewModel _row;

  public GridItemViewModel()
  {
  }

  public GridItemViewModel( final GridRowViewModel row )
  {
    _row = row;
  }

  @Nullable
  public Object getColumnIdentifier()
  {
    return _columnIdentifier;
  }

  public void setColumnIdentifier( @Nullable final Object columnIdentifier )
  {
    _columnIdentifier = columnIdentifier;
  }

  public GridRowViewModel getRow()
  {
    return _row;
  }

  public void setRow( final GridRowViewModel row )
  {
    _row = row;
  }
}
