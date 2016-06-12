package scoutmgr.client.view.cell;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import scoutmgr.client.entity.ScoutSection;

public class ScoutSectionCell
  extends AbstractSafeHtmlCell<ScoutSection>
{
  public ScoutSectionCell()
  {
    super( ScoutSectionSafeHtmlRenderer.getInstance() );
  }

  @Override
  public void render( Context context, SafeHtml value, SafeHtmlBuilder sb )
  {
    if ( value != null )
    {
      sb.append( value );
    }
  }
}
