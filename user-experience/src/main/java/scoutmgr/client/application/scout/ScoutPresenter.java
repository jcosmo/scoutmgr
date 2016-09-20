package scoutmgr.client.application.scout;

import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import javax.inject.Inject;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.application.ApplicationPresenter;
import scoutmgr.client.application.scout.badgework.BadgeworkPresenter;
import scoutmgr.client.entity.Person;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.security.ScoutViewGatekeeper;
import scoutmgr.client.view.model.ScoutViewModel;

public class ScoutPresenter
  extends Presenter<ScoutPresenter.View, ScoutPresenter.Proxy>
  implements ScoutUiHandlers
{

  @Inject
  private BadgeworkPresenter _badgeworkPresenter;

  @Inject
  private PlaceManager _placeManager;

  @Inject
  private EntityRepository _entityRepository;

  @ContentSlot
  public static final GwtEvent.Type<RevealContentHandler<?>> SLOT_BADGEWORK = new GwtEvent.Type<>();

  @ProxyStandard
  @NameToken( { NameTokens.SCOUT } )
  @UseGatekeeper( ScoutViewGatekeeper.class )
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
  ScoutPresenter( final EventBus eventBus,
                  final View view,
                  final Proxy proxy )
  {
    super( eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT );

    getView().setUiHandlers( this );
  }

  protected void onBind()
  {
    super.onBind();
    setInSlot( SLOT_BADGEWORK, _badgeworkPresenter );
  }

  @Override
  public void prepareFromRequest( final PlaceRequest request )
  {
    super.prepareFromRequest( request );
    final String idStr = request.getParameter( "id", null );
    if ( null != idStr )
    {
      final Integer scoutID = Integer.valueOf( idStr );
      getView().showLoadingMessage();
      configureForScout( scoutID );
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
      _badgeworkPresenter.configureForScout( person );
    }
    else
    {
      _badgeworkPresenter.configureForScout( null );
      getView().showLoadingMessage();
    }
  }
}
