package scoutmgr.client.application.members;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import java.util.Date;
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
  private static final java.util.logging.Logger LOG =
    java.util.logging.Logger.getLogger( MemberFormPresenter.class.getName() );

  private int _idForEdit;

  @ProxyStandard
  @NameToken( { NameTokens.MEMBER, NameTokens.NEW_MEMBER } )
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
      _idForEdit = Integer.parseInt( idStr );
      setMemberForEdit( _idForEdit );
    }
    else
    {
      _idForEdit = -1;
      getView().reset();
    }
  }

  private void setMemberForEdit( final int id )
  {
    final Person person = _entityRepository.findByID( Person.class, id );
    if ( null != person )
    {
      final ScoutViewModel viewModel = new ScoutViewModel( person );
      getView().setMember( viewModel );
    }
  }

  public void saveMember( final String givenName, final String familyName )
  {
    if ( -1 == _idForEdit )
    {
      _personnelService.addScout( givenName, familyName, new Date(), "c" );
    }
    else {
      _personnelService.updateScout( _idForEdit, givenName, familyName, new Date(), "c" );
    }
    final PlaceRequest newRequest = new PlaceRequest.Builder().nameToken( NameTokens.MEMBERS ).build();
    _placeManager.revealPlace( newRequest );
  }
}