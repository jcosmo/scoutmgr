package scoutmgr.client.application.scout.badgework;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardAction;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import java.util.ArrayList;
import java.util.Collections;
import scoutmgr.client.entity.Badge;
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
      _expandable.add( createBadgeCategoryView( badgeCategory ) );
    }
  }

  private MaterialCollapsibleItem createBadgeCategoryView( final BadgeCategory badgeCategory )
  {
    final MaterialCollapsibleItem item = new MaterialCollapsibleItem();
    final MaterialCollapsibleHeader header = new MaterialCollapsibleHeader();
    final MaterialLink link = new MaterialLink();
    link.setText( badgeCategory.getName() );
    header.add( link );
    item.add( header );
    final MaterialCollapsibleBody body = new MaterialCollapsibleBody();
    body.add( createBadgesView( badgeCategory ) );
    item.add( body );
    return item;
  }

  private Widget createBadgesView( final BadgeCategory badgeCategory )
  {
    final MaterialRow row = new MaterialRow();
    for ( final Badge badge : badgeCategory.getBadges() )
    {
      final MaterialColumn column = new MaterialColumn();
      column.setGrid( "s12 m4 l3" );
      final MaterialCard card = new MaterialCard();
      final MaterialCardContent cardContent = new MaterialCardContent();
      final MaterialCardTitle cardTitle = new MaterialCardTitle();
      cardTitle.setText( badge.getName() );
      cardTitle.setIconType( IconType.POLYMER );
      cardTitle.setIconPosition( IconPosition.RIGHT );
      cardContent.add( cardTitle );
      cardContent.add( new HTML("Blurb about this badge!"));
      final MaterialCardAction actions = new MaterialCardAction();
      final MaterialLink progressLink = new MaterialLink( "Record Progress" );
      actions.add( progressLink );
      cardContent.add( actions );
      card.add( cardContent );
      column.add(card);
      row.add( column );
    }
    return row;
  }

  @Override
  public void reset()
  {
    _expandable.clear();
  }
}
