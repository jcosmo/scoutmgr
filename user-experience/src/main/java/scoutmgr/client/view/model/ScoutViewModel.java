package scoutmgr.client.view.model;

import java.util.ArrayList;
import org.realityforge.gwt.datatypes.client.date.RDate;
import scoutmgr.client.entity.BadgeTask;
import scoutmgr.client.entity.BadgeTaskGroup;
import scoutmgr.client.entity.Person;
import scoutmgr.client.entity.ScoutLevel;
import scoutmgr.client.entity.TaskCompletion;
import scoutmgr.client.entity.TaskGroupCompletion;

public class ScoutViewModel
  extends AbstractViewModel
{
  private ArrayList<TaskCompletionViewModel> _taskCompletions = new ArrayList<>();
  private ArrayList<TaskGroupCompletionViewModel> _taskGroupCompletions = new ArrayList<>();

  public ScoutViewModel( final Person person )
  {
    super(person, person.getFirstName() + " " + person.getLastName() );
    for ( final TaskCompletion completion : person.getTaskCompletions() )
    {
      _taskCompletions.add( new TaskCompletionViewModel( this, completion ) );
    }
    for ( final TaskGroupCompletion completion : person.getTaskGroupCompletions() )
    {
      _taskGroupCompletions.add( new TaskGroupCompletionViewModel( this, completion ) );
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

  public TaskGroupCompletionViewModel getCompletionRecord( final BadgeTaskGroup badgeTaskGroup )
  {
    for ( final TaskGroupCompletionViewModel completion : _taskGroupCompletions )
    {
      if ( completion.matches( badgeTaskGroup ) )
      {
        return completion;
      }
    }
    return null;
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

}
