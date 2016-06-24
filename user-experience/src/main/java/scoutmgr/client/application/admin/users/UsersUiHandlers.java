package scoutmgr.client.application.admin.users;

import com.gwtplatform.mvp.client.UiHandlers;
import scoutmgr.client.entity.security.User;

public interface UsersUiHandlers
  extends UiHandlers
{
  void addUser();

  void viewUser( User user );

  void editUser( User user );

  void requestDeleteUser( User user );
}
