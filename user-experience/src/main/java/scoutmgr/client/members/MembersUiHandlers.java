package scoutmgr.client.members;

import com.gwtplatform.mvp.client.UiHandlers;
import scoutmgr.client.entity.Person;

public interface MembersUiHandlers
  extends UiHandlers
{
  void addScout();
  void editScout( Person person );
}
