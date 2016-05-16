package scoutmgr.client.view.model;

import javax.annotation.Nonnull;
import org.realityforge.gwt.datatypes.client.date.RDate;
import scoutmgr.client.entity.BadgeTask;
import scoutmgr.client.entity.BadgeTaskGroup;
import scoutmgr.client.entity.TaskCompletion;
import scoutmgr.client.entity.TaskGroupCompletion;

public class TaskCompletionViewModel
  extends AbstractViewModel
{
  private BadgeTask _badgeTask;
  private BadgeTaskGroup _badgeTaskGroup;
  private RDate _dateCompleted;

  public TaskCompletionViewModel( final TaskCompletion completion )
  {
    this( completion.getBadgeTask(), completion.getDateCompleted() );
  }

  public TaskCompletionViewModel( final TaskGroupCompletion completion )
  {
    this( completion.getBadgeTaskGroup(), completion.getDateCompleted() );
  }

  public TaskCompletionViewModel( final BadgeTask badgeTask,
                                  final RDate dateCompleted )
  {
    super( null, "BadgeTask Completion " + badgeTask.getID() );
    _badgeTask = badgeTask;
    _dateCompleted = dateCompleted;
  }

  public TaskCompletionViewModel( final BadgeTaskGroup badgeTaskGroup,
                                  final RDate dateCompleted )
  {
    super( null, "BadgeTaskGroup Completion " + badgeTaskGroup.getID() );
    _badgeTaskGroup = badgeTaskGroup;
    _dateCompleted = dateCompleted;
  }

  public boolean matches( final BadgeTask task )
  {
    return _badgeTask.equals( task );
  }
  public boolean matches( final BadgeTaskGroup task )
  {
    return _badgeTaskGroup.equals( task );
  }

  public RDate getDateCompleted()
  {
    return _dateCompleted;
  }
}
