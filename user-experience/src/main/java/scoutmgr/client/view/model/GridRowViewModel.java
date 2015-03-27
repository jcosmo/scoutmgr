package scoutmgr.client.view.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * A list model representing a grid row of values and column identifiers .
 */
public class GridRowViewModel
  extends AbstractViewModel
{
  private final ArrayList<GridItemViewModel> _rowItems = new ArrayList<>();

  @Nonnull
  public List<GridItemViewModel> getRowItems()
  {
    return Collections.unmodifiableList( _rowItems );
  }

  public void addItem( final GridItemViewModel item )
  {
    item.setRow( this );
    _rowItems.add( item );
  }

  public void removeItem( final GridItemViewModel item )
  {
    _rowItems.remove( item );
  }
}
