package scoutmgr.client.application.scout.badgework;

import com.gwtplatform.mvp.client.UiHandlers;
import scoutmgr.client.view.model.ScoutViewModel;

public interface BadgeworkProgressUiHandlers
  extends UiHandlers

{
  void onCancel();
  void onSave( ScoutViewModel model );
}
