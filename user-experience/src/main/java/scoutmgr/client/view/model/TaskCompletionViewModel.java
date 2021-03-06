package scoutmgr.client.view.model;

import java.util.Date;
import org.realityforge.gwt.datatypes.client.date.RDate;
import scoutmgr.client.entity.Badge;
import scoutmgr.client.entity.BadgeTask;
import scoutmgr.client.entity.TaskCompletion;

public class TaskCompletionViewModel
  extends AbstractViewModel
{
  private BadgeTask _badgeTask;
  private RDate _dateCompleted;
  private Integer _id;
  private String _recordedBy;
  private RDate _dateRecorded;
  private String _signedBy;
  private RDate _dateSigned;

  public TaskCompletionViewModel( final TaskCompletion completion )
  {
    this( completion.getBadgeTask(), completion.getDateCompleted() );
    _id = completion.getID();
    _dateRecorded = completion.getDateRecorded();
    _recordedBy = completion.getRecordedBy();
    _dateSigned = completion.getDateSigned();
    _signedBy = completion.getSignedBy();
  }

  public TaskCompletionViewModel( final BadgeTask badgeTask,
                                  final RDate dateCompleted )
  {
    super( null, "BadgeTask Completion " + badgeTask.getID() );
    _badgeTask = badgeTask;
    _dateCompleted = dateCompleted;
    _id = null;
  }


  public boolean matches( final BadgeTask task )
  {
    return _badgeTask.equals( task );
  }

  public RDate getDateCompleted()
  {
    return _dateCompleted;
  }

  public boolean matches( final Badge badge )
  {
    return _badgeTask.getBadge().equals( badge );
  }

  public BadgeTask getBadgeTask()
  {
    return _badgeTask;
  }

  public void setDateCompleted( final Date dateCompleted )
  {
    _dateCompleted = RDate.fromDate( dateCompleted );
  }

  public Integer getId()
  {
    return _id;
  }

  public String getRecordedBy()
  {
    return _recordedBy;
  }

  public RDate getDateRecorded()
  {
    return _dateRecorded;
  }

  public String getSignedBy()
  {
    return _signedBy;
  }

  public RDate getDateSigned()
  {
    return _dateSigned;
  }
}
