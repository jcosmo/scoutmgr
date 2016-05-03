package scoutmgr.client.application.scout.badgework;

import com.gwtplatform.mvp.client.UiHandlers;

public interface BadgeworkUiHandlers
  extends UiHandlers
{
  void requestRecordProgress( int badgeID );
}
