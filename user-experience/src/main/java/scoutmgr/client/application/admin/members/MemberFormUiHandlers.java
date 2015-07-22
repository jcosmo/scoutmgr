package scoutmgr.client.application.admin.members;

import com.gwtplatform.mvp.client.UiHandlers;
import org.realityforge.gwt.datatypes.client.date.RDate;

public interface MemberFormUiHandlers
  extends UiHandlers
{
  void saveMember( final String regNumber, String givenName, String familyName, final RDate dob );
}
