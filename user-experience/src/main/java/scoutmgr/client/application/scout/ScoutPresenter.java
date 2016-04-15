package scoutmgr.client.application.scout;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.entity.Person;
import scoutmgr.client.net.ScoutmgrDataLoaderService;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.service.PersonnelService;
import scoutmgr.client.view.model.ScoutViewModel;

public class ScoutPresenter
  extends Presenter<ScoutPresenter.View, ScoutPresenter.Proxy>
  implements ScoutUiHandlers
{
  private Integer _scoutID;

  @Inject
  private PlaceManager _placeManager;


  @Inject
  private ScoutmgrDataLoaderService _dataloader;

  @Inject
  private EntityRepository _entityRepository;

  @Inject
  private PersonnelService _personnelService;

  @ProxyStandard
  @NameToken( { NameTokens.SCOUT } )
  interface Proxy
    extends ProxyPlace<ScoutPresenter>
  {
  }

  interface View
    extends com.gwtplatform.mvp.client.View, HasUiHandlers<ScoutUiHandlers>
  {
    void setScout( ScoutViewModel viewModel );

    void showLoadingMessage();
  }

  @Inject
  ScoutPresenter(  final EventBus eventBus,
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
      _scoutID = Integer.valueOf( idStr );
      configureForScout( _scoutID );
    }
    else
    {
      _placeManager.revealErrorPlace( "Invalid URL" );
    }
  }

  private void configureForScout( final Integer id )
  {
    final Person person = _entityRepository.findByID( Person.class, id );
    if ( null != person )
    {
      final ScoutViewModel viewModel = new ScoutViewModel( person );
      getView().setScout( viewModel );
    }
    else
    {
      getView().showLoadingMessage();
    }
  }
}
