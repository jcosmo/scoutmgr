package scoutmgr.client.resource;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

// Turn of checkstyle as the names match the images names
//CHECKSTYLE OFF: MethodName
public interface ScoutmgrResourceBundle
  extends ClientBundle
{
  interface ScoutmgrCss
    extends CssResource
  {
    String footer();

    String footerText();

    String footerContainer();

    String modal();

    String modalBackdrop();

    String content();

    String navbar();

    String menubar();

    String activeNav();

    String badgeCardDescription();

    String badgeCardSummary();

    String badgeCardDetail();

    String badgeTaskGroup();

    String badgeTask();

    String badgeworkProgressDialog();

    String checkboxDone();

    String titleColumn();

    String badgeTaskCategoryRow();

    String badgeTaskRow();

    String whenColumn();

    String buttonBar();
  }

  @Source( "scoutmgr.main.gss" )
  ScoutmgrCss scoutmgr();
}
