package scoutmgr.client.view.model;

import org.realityforge.gwt.datatypes.client.date.RDate;
import scoutmgr.client.entity.BadgeTask;
import scoutmgr.client.entity.TaskCompletion;

public class TaskCompletionViewModel
  extends AbstractViewModel
{
  ScoutViewModel _scout;

  public TaskCompletionViewModel( final ScoutViewModel scout, TaskCompletion completion )
  {
    super( completion, "Completion " + completion.getID() );
    _scout = scout;
  }

  private TaskCompletion tc()
  {
    return (TaskCompletion) asModelObject();
  }

  public boolean matches( final BadgeTask task )
  {
    return tc().getBadgeTask().equals( task );
  }

  public RDate getDateCompleted()
  {
    return tc().getDateCompleted();
  }
}
