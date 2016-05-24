package scoutmgr.client.view.model;

import java.util.ArrayList;
import org.realityforge.gwt.datatypes.client.date.RDate;
import scoutmgr.client.entity.BadgeTask;
import scoutmgr.client.entity.Person;
import scoutmgr.client.entity.ScoutLevel;
import scoutmgr.client.entity.TaskCompletion;

public class ScoutViewModel
  extends AbstractViewModel
{
  private ArrayList<TaskCompletionViewModel> _taskCompletions = new ArrayList<>();

  public ScoutViewModel( final Person person )
  {
    super(person, person.getFirstName() + " " + person.getLastName() );
    for ( final TaskCompletion completion : person.getTaskCompletions() )
    {
      _taskCompletions.add( new TaskCompletionViewModel( completion ) );
    }
  }

  public ScoutLevel getScoutLevel()
  {
    return ((Person)asModelObject()).getScoutLevel();
  }

  public String getFirstName()
  {
    return ((Person)asModelObject()).getFirstName();
  }

  public String getLastName()
  {
    return ((Person)asModelObject()).getLastName();
  }

  public RDate getDob()
  {
    return ((Person)asModelObject()).getDob();
  }

  public String getRegistrationNumber()
  {
    return ((Person)asModelObject()).getRegistrationNumber();
  }

  public TaskCompletionViewModel getCompletionRecord( final BadgeTask badgeTask )
  {
    for ( final TaskCompletionViewModel completion : _taskCompletions )
    {
      if ( completion.matches( badgeTask ) )
      {
        return completion;
      }
    }
    return null;
  }

  public TaskCompletionViewModel addCompletionRecord( final BadgeTask badgeTask )
  {
    final TaskCompletionViewModel existing = getCompletionRecord( badgeTask );
    if ( null != existing )
    {
      return existing;
    }

    final TaskCompletionViewModel newModel = new TaskCompletionViewModel( badgeTask, RDate.today() );
    _taskCompletions.add( newModel );
    return newModel;
  }

  public TaskCompletionViewModel removeCompletionRecord( final BadgeTask badgeTask )
  {
    final TaskCompletionViewModel existing = getCompletionRecord( badgeTask );
    if ( null != existing )
    {
      _taskCompletions.remove( existing );
    }
    return existing;
  }

  public ArrayList<TaskCompletionViewModel> getTaskCompletions()
  {
    return _taskCompletions;
  }
}
