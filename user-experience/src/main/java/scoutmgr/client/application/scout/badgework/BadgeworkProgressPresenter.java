package scoutmgr.client.application.scout.badgework;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import org.realityforge.gwt.datatypes.client.date.RDate;
import scoutmgr.client.data_type.TaskCompletionDTO;
import scoutmgr.client.data_type.TaskCompletionDTOFactory;
import scoutmgr.client.entity.Badge;
import scoutmgr.client.entity.Person;
import scoutmgr.client.entity.TaskCompletion;
import scoutmgr.client.service.PersonnelService;
import scoutmgr.client.view.model.ScoutViewModel;
import scoutmgr.client.view.model.TaskCompletionViewModel;

public class BadgeworkProgressPresenter
  extends PresenterWidget<BadgeworkProgressPresenter.View>
  implements BadgeworkProgressUiHandlers
{
  @com.google.inject.Inject
  private PersonnelService _personnelService;
  private Person _scout;
  private Badge _badge;


  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<BadgeworkProgressUiHandlers>
  {
    void configure( ScoutViewModel scout, Badge badge );

    void close();
  }

  @Inject
  BadgeworkProgressPresenter( final EventBus eventBus,
                              final View view )
  {
    super( eventBus, view );
    getView().setUiHandlers( this );
  }

  public void configure( final Person scout, final Badge badge )
  {
    _scout = scout;
    _badge = badge;
    getView().configure( new ScoutViewModel( scout ), badge );
  }

  @Override
  public void onCancel()
  {
    getView().close();
  }

  @Override
  public void onSave( final ScoutViewModel model )
  {
    _personnelService.updateCompletion( _scout.getID(), _badge.getID(),
                                        completionsForBadge( model ));
    getView().close();
  }

  private List<TaskCompletionDTO> completionsForBadge( final ScoutViewModel model )
  {
    final ArrayList<TaskCompletionDTO> converted = new ArrayList<>();
    for ( final TaskCompletionViewModel taskCompletionViewModel : model.getTaskCompletions() )
    {
      if ( taskCompletionViewModel.matches( _badge ) )
      {
        converted.add( TaskCompletionDTOFactory.create( taskCompletionViewModel.getBadgeTask().getID(),
                                                        taskCompletionViewModel.getDateCompleted() ) );
      }
    }
    return converted;
  }
}
