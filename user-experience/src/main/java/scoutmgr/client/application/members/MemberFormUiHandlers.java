package scoutmgr.client.application.members;

import com.gwtplatform.mvp.client.UiHandlers;

public interface MemberFormUiHandlers
  extends UiHandlers
{
  void saveMember( String givenName, String familyName );
}
