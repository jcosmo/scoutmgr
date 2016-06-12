package scoutmgr.client.view.cell;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import scoutmgr.client.entity.ScoutSection;

public class ScoutSectionSafeHtmlRenderer
  implements SafeHtmlRenderer<ScoutSection>
{
  private static ScoutSectionSafeHtmlRenderer instance;

  public static ScoutSectionSafeHtmlRenderer getInstance()
  {
    if ( instance == null )
    {
      instance = new ScoutSectionSafeHtmlRenderer();
    }
    return instance;
  }

  private ScoutSectionSafeHtmlRenderer()
  {
  }

  public SafeHtml render( ScoutSection object )
  {
    return ( object == null ) ? SafeHtmlUtils.EMPTY_SAFE_HTML : SafeHtmlUtils.fromString( object.getCode() );
  }

  public void render( ScoutSection object, SafeHtmlBuilder appendable )
  {
    appendable.append( SafeHtmlUtils.fromString( object.getCode() ) );
  }
}
