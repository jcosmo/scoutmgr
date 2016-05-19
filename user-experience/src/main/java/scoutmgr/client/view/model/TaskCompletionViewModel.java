package scoutmgr.client.view.model;

import org.realityforge.gwt.datatypes.client.date.RDate;
import scoutmgr.client.entity.BadgeTask;
import scoutmgr.client.entity.TaskCompletion;

public class TaskCompletionViewModel
  extends AbstractViewModel
{
  private BadgeTask _badgeTask;
  private RDate _dateCompleted;

  public TaskCompletionViewModel( final TaskCompletion completion )
  {
    this( completion.getBadgeTask(), completion.getDateCompleted() );
  }

  public TaskCompletionViewModel( final BadgeTask badgeTask,
                                  final RDate dateCompleted )
  {
    super( null, "BadgeTask Completion " + badgeTask.getID() );
    _badgeTask = badgeTask;
    _dateCompleted = dateCompleted;
  }

  public boolean matches( final BadgeTask task )
  {
    return _badgeTask.equals( task );
  }

  public RDate getDateCompleted()
  {
    return _dateCompleted;
  }
}
