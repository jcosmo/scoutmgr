package scoutmgr.client.view.model;

import org.realityforge.gwt.datatypes.client.date.RDate;
import scoutmgr.client.entity.BadgeTaskGroup;
import scoutmgr.client.entity.TaskGroupCompletion;

public class TaskGroupCompletionViewModel
  extends AbstractViewModel
{
  ScoutViewModel _scout;

  public TaskGroupCompletionViewModel( final ScoutViewModel scout, TaskGroupCompletion completion )
  {
    super( completion, "Completion " + completion.getID() );
    _scout = scout;
  }

  private TaskGroupCompletion tgc()
  {
    return (TaskGroupCompletion) asModelObject();
  }

  public RDate getDateCompleted()
  {
    return tgc().getDateCompleted();
  }

  public boolean matches( final BadgeTaskGroup badgeTaskGroup )
  {
    return tgc().getBadgeTaskGroup().equals( badgeTaskGroup );
  }
}
