package scoutmgr.client.application.admin.members;

import com.gwtplatform.mvp.client.UiHandlers;
import scoutmgr.client.entity.Person;

public interface MembersUiHandlers
  extends UiHandlers
{
  void addScout();

  void editScout( Person person );

  void requestDeleteScout( Person person );
}
