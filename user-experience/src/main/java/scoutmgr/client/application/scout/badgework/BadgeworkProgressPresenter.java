package scoutmgr.client.application.scout.badgework;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import scoutmgr.client.data_type.TaskCompletionUpdateDTO;
import scoutmgr.client.data_type.TaskCompletionUpdateDTOFactory;
import scoutmgr.client.entity.Badge;
import scoutmgr.client.entity.Person;
import scoutmgr.client.entity.security.User;
import scoutmgr.client.ioc.FrontendContext;
import scoutmgr.client.security.PermissionUtil;
import scoutmgr.client.service.PersonnelService;
import scoutmgr.client.view.model.ScoutViewModel;

public class BadgeworkProgressPresenter
  extends PresenterWidget<BadgeworkProgressPresenter.View>
  implements BadgeworkProgressUiHandlers
{
  private final PersonnelService _personnelService;
  private final FrontendContext _frontendContext;
  private Person _scout;
  private Badge _badge;

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<BadgeworkProgressUiHandlers>
  {
    void configure( ScoutViewModel scout, Badge badge, final boolean canComplete, final boolean canSign );

    void close();
  }

  @Inject
  BadgeworkProgressPresenter( final EventBus eventBus,
                              final PersonnelService personnelService,
                              final FrontendContext frontendContext,
                              final View view )
  {
    super( eventBus, view );
    _frontendContext = frontendContext;
    _personnelService = personnelService;
    getView().setUiHandlers( this );
  }

  public void configure( final Person scout, final Badge badge )
  {
    _scout = scout;
    _badge = badge;
    final User user = _frontendContext.getUser();
    getView().configure( new ScoutViewModel( scout ), badge,
                         PermissionUtil.canCompleteBadgework( user, scout ),
                         PermissionUtil.canSignBadgework( user, scout ) );
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
                                        completionsForBadge( model ) );
    getView().close();
  }

  private List<TaskCompletionUpdateDTO> completionsForBadge( final ScoutViewModel model )
  {
    return model.getTaskCompletions().
      stream().
      filter( taskCompletionViewModel -> taskCompletionViewModel.matches( _badge ) ).
      map( taskCompletionViewModel ->
             TaskCompletionUpdateDTOFactory.create( taskCompletionViewModel.getBadgeTask().getID(),
                                                    taskCompletionViewModel.getDateCompleted(),
                                                    false,
                                                    false ) ).
      collect( Collectors.toCollection( ArrayList::new ) );
  }
}
