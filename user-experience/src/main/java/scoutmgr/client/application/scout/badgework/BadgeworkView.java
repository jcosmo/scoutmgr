package scoutmgr.client.application.scout.badgework;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLink;
import java.util.ArrayList;
import java.util.Collections;
import scoutmgr.client.entity.BadgeCategory;

public class BadgeworkView
  extends ViewWithUiHandlers<BadgeworkUiHandlers>
  implements BadgeworkPresenter.View
{
  @UiField
  MaterialCollapsible _expandable;

  interface Binder
    extends UiBinder<Widget, BadgeworkView>
  {
  }

  @Inject
  BadgeworkView( final Binder uiBinder )
  {
    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  public void setBadgeworkProgress( final ArrayList<BadgeCategory> badgeCategories )
  {
    Collections.sort( badgeCategories, ( o1, o2 ) -> o1.getRank() - o2.getRank() );
    for ( final BadgeCategory badgeCategory : badgeCategories )
    {
      _expandable.add( createItem( badgeCategory ) );
    }
  }

  private MaterialCollapsibleItem createItem( final BadgeCategory badgeCategory)
  {
    MaterialCollapsibleItem item = new MaterialCollapsibleItem();
    MaterialCollapsibleHeader header = new MaterialCollapsibleHeader();
    MaterialLink link = new MaterialLink();
    link.setText( badgeCategory.getName() );
    header.add( link );
    item.add( header );
    MaterialCollapsibleBody body = new MaterialCollapsibleBody(  );
    body.add(new HTML("this is some content!"));
    item.add(body);
    return item;
  }

  @Override
  public void reset()
  {
    _expandable.clear();
  }
}
