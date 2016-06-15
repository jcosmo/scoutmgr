package scoutmgr.client.application.admin.members;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import javax.inject.Inject;
import org.realityforge.gwt.datatypes.client.date.RDate;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.entity.Person;
import scoutmgr.client.net.ScoutmgrDataLoaderService;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.service.PersonnelService;
import scoutmgr.client.view.model.ScoutViewModel;

public class MemberFormPresenter
  extends Presenter<MemberFormPresenter.View, MemberFormPresenter.Proxy>
  implements MemberFormUiHandlers
{
  private Integer _idForEdit;

  @ProxyStandard
  @NameToken( { NameTokens.ADMIN_SCOUT, NameTokens.ADMIN_NEW_SCOUT } )
  interface Proxy
    extends ProxyPlace<MemberFormPresenter>
  {
  }

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<MemberFormUiHandlers>
  {
    void reset();

    void setMember( ScoutViewModel viewModel );
  }

  @Inject
  private PlaceManager _placeManager;

  @Inject
  private ScoutmgrDataLoaderService _dataloader;

  @Inject
  private EntityRepository _entityRepository;

  @Inject
  private PersonnelService _personnelService;

  @Inject
  MemberFormPresenter( final EventBus eventBus,
                       final View view,
                       final Proxy proxy )
  {
    super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT );
    getView().setUiHandlers( this );
  }

  @Override
  public void prepareFromRequest( final PlaceRequest request )
  {
    super.prepareFromRequest( request );
    final String idStr = request.getParameter( "id", null );
    if ( null != idStr )
    {
      _idForEdit = Integer.valueOf( idStr );
      setMemberForEdit( _idForEdit );
    }
    else
    {
      _idForEdit = null;
      getView().reset();
    }
  }

  private void setMemberForEdit( final Integer id )
  {
    final Person person = _entityRepository.findByID( Person.class, id );
    if ( null != person )
    {
      final ScoutViewModel viewModel = new ScoutViewModel( person );
      getView().setMember( viewModel );
    }
  }

  public void saveMember( final String scoutSection,
                          final String regNumber,
                          final String givenName,
                          final String familyName,
                          final RDate dob )
  {
    if ( null == _idForEdit )
    {
      _personnelService.addScout( scoutSection, givenName, familyName, dob, regNumber );
    }
    else
    {
      _personnelService.updateScout( _idForEdit.intValue(), scoutSection, givenName, familyName, dob, regNumber );
    }
    final PlaceRequest newRequest = new PlaceRequest.Builder().nameToken( NameTokens.ADMIN_SCOUTS ).build();
    _placeManager.revealPlace( newRequest );
  }
}
