package scoutmgr.client.view.cell;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import scoutmgr.client.entity.ScoutLevel;

public class ScoutLevelSafeHtmlRenderer
  implements SafeHtmlRenderer<ScoutLevel>
{
  private static ScoutLevelSafeHtmlRenderer instance;

  public static ScoutLevelSafeHtmlRenderer getInstance()
  {
    if ( instance == null )
    {
      instance = new ScoutLevelSafeHtmlRenderer();
    }
    return instance;
  }

  private ScoutLevelSafeHtmlRenderer()
  {
  }

  public SafeHtml render( ScoutLevel object )
  {
    return ( object == null ) ? SafeHtmlUtils.EMPTY_SAFE_HTML : SafeHtmlUtils.fromString( object.getCode() );
  }

  public void render( ScoutLevel object, SafeHtmlBuilder appendable )
  {
    appendable.append( SafeHtmlUtils.fromString( object.getCode() ) );
  }
}
