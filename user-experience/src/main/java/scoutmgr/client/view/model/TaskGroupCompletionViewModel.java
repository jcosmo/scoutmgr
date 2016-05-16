package scoutmgr.client.view.model;

import org.realityforge.gwt.datatypes.client.date.RDate;
import scoutmgr.client.entity.BadgeTaskGroup;
import scoutmgr.client.entity.TaskGroupCompletion;

public class TaskGroupCompletionViewModel
  extends AbstractViewModel
{

  private final BadgeTaskGroup _badgeTaskGroup;
  private final RDate _dateCompleted;

  public TaskGroupCompletionViewModel( final TaskGroupCompletion completion )
  {
    super( completion, "Completion " + completion.getID() );
    _badgeTaskGroup = completion.getBadgeTaskGroup();
    _dateCompleted = completion.getDateCompleted();
  }

  public RDate getDateCompleted()
  {
    return _dateCompleted;
  }

  public boolean matches( final BadgeTaskGroup badgeTaskGroup )
  {
    return _badgeTaskGroup.equals( badgeTaskGroup );
  }
}
