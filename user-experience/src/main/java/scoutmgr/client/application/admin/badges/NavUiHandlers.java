package scoutmgr.client.application.admin.badges;

import com.gwtplatform.mvp.client.UiHandlers;
import scoutmgr.client.entity.ScoutLevel;

public interface NavUiHandlers
  extends UiHandlers
{
  void changeScoutLevel( ScoutLevel badgeScoutLevel );
}
