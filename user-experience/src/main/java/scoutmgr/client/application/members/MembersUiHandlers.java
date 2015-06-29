package scoutmgr.client.application.members;

import com.gwtplatform.mvp.client.UiHandlers;
import scoutmgr.client.entity.Person;

public interface MembersUiHandlers
  extends UiHandlers
{
  void addScout();
  void editScout( Person person );
  void deleteScout( Person person );
}
