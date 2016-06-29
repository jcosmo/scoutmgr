package scoutmgr.client.application.admin.users;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import javax.inject.Inject;
import org.realityforge.replicant.client.EntityRepository;
import scoutmgr.client.place.NameTokens;
import scoutmgr.client.view.model.UserViewModel;

@SuppressWarnings( "Convert2Lambda" )
public class UserFormView
  extends ViewWithUiHandlers<UserFormUiHandlers>
  implements UserFormPresenter.View
{
  @UiField
  MaterialTextBox _username;
  @UiField
  MaterialTextBox _password;
  @UiField
  MaterialButton _saveButton;
  @UiField
  MaterialButton _cancelButton;
  @UiField
  MaterialTextBox _email;
  @UiField
  MaterialTextBox _repeatPassword;

  @Inject
  EntityRepository _entityRepository;

  @Inject
  PlaceManager _placeManager;

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
    _username.setEnabled( true );
    _email.setValue( "" );
    _password.setValue( "" );
    _repeatPassword.setValue( "" );
  }

  @Override
  public void setUser( final UserViewModel user )
  {
    _username.setValue( user.getUserName() );
    _username.setEnabled( false );
    _email.setValue( user.getEmail() );
    _password.setValue( "" );
    _repeatPassword.setValue( "" );
  }

  @UiHandler( "_saveButton" )
  public void onSave( final ClickEvent event )
  {
    event.preventDefault();
    event.stopPropagation();
    final String password = trim( _password.getText() );
    final String repeatPassword = trim( _repeatPassword.getText() );
    if ( !passwordMatch( password, repeatPassword ) )
    {
      MaterialToast.fireToast( "Passwords do not match" );
      return;
    }
    getUiHandlers().saveUser( _username.getText(), _email.getText(), password );
  }

  @UiHandler( "_cancelButton" )
  public void onCancel( final ClickEvent event )
  {
    _placeManager.revealPlace( new PlaceRequest.Builder().nameToken( NameTokens.ADMIN_USERS ).build() );
  }

  private String trim( final String text )
  {
    if ( text == null || text.trim().length() == 0 )
    {
      return null;
    }
    return text.trim();
  }

  private boolean passwordMatch( final String password, final String repeatPassword )
  {
    if ( null == password && null == repeatPassword )
    {
      return true;
    }
    if ( null == password || null == repeatPassword )
    {
      return false;
    }
    return password.equals( repeatPassword );
  }
}
