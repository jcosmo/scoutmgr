package scoutmgr.client.application.admin.badges;

import com.gwtplatform.mvp.client.UiHandlers;
import scoutmgr.client.data_type.BadgeScoutLevel;

public interface NavUiHandlers
  extends UiHandlers
{
  void changeScoutLevel( BadgeScoutLevel badgeScoutLevel );
}
