package scoutmgr.client.application.admin.badges;

import com.gwtplatform.mvp.client.UiHandlers;
import scoutmgr.client.entity.ScoutSection;

public interface NavUiHandlers
  extends UiHandlers
{
  void changeScoutSection( ScoutSection badgeScoutSection );
}
