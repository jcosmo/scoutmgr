package scoutmgr.client.view.model.comparator;

import java.util.Comparator;
import javax.annotation.Nullable;
import scoutmgr.client.view.model.AbstractViewModel;

public class ViewModelComparator<T extends AbstractViewModel>
  implements Comparator<T>
{
  public static final ViewModelComparator<AbstractViewModel> DEFAULT_COMPARATOR =
    new ViewModelComparator<>( null );

  @Nullable
  private final Comparator _comparator;

  public ViewModelComparator( @Nullable final Comparator comparator )
  {
    _comparator = comparator;
  }

  @SuppressWarnings( "unchecked" )
  @Override
  public int compare( final T viewModel1, final T viewModel2 )
  {
    if ( null == viewModel1 && null == viewModel2 )
    {
      return 0;
    }
    else if ( null == viewModel1 )
    {
      return 1;
    }
    else if ( null == viewModel2 )
    {
      return -1;
    }

    if ( null == _comparator )
    {
      final String displayString1 = viewModel1.getDisplayString();
      final String displayString2 = viewModel2.getDisplayString();
      if ( null == displayString1 && null == displayString2 )
      {
        return 0;
      }
      else if ( null == displayString1 )
      {
        return 1;
      }
      else if ( null == displayString2 )
      {
        return -1;
      }

      return displayString1.compareTo( displayString2 );
    }
    else
    {
      return _comparator.compare( viewModel1.asModelObject(), viewModel2.asModelObject() );
    }
  }
}
