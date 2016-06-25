package scoutmgr.client.application.admin.users;

import com.gwtplatform.mvp.client.UiHandlers;

public interface UserFormUiHandlers
  extends UiHandlers
{
  void saveUser( final String userName,
                 final String email,
                 final String password );
}
