package scoutmgr.client.application.admin.users;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import javax.inject.Inject;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.view.model.UserViewModel;

@SuppressWarnings( "Convert2Lambda" )
public class UserFormView
  extends ViewWithUiHandlers<UserFormUiHandlers>
  implements UserFormPresenter.View
{
  @UiField
  TextBox _username;
  @UiField
  TextBox _password;
  @UiField
  Anchor _saveButton;
  @UiField
  Anchor _cancelButton;

  @Inject
  EntityRepository _entityRepository;

  interface Binder
    extends UiBinder<Widget, UserFormView>
  {
  }

  @Inject
  UserFormView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  public void reset()
  {
    _username.setValue( "" );
    _password.setValue( "" );
  }

  @Override
  public void setUser( final UserViewModel user )
  {
    _username.setValue( user.getUserName() );
    _password.setValue( "" );
  }

  @UiHandler( "_saveButton" )
  public void onSave( final ClickEvent event )
  {
    event.preventDefault();
    event.stopPropagation();
    getUiHandlers().saveUser( _username.getText(), _password.getText() );
  }
}
