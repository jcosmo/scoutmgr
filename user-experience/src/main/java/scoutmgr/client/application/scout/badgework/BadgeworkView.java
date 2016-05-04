package scoutmgr.client.application.scout.badgework;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardAction;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardReveal;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialCollectionSecondary;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import scoutmgr.client.entity.Badge;
import scoutmgr.client.entity.BadgeCategory;
import scoutmgr.client.entity.BadgeTask;
import scoutmgr.client.entity.BadgeTaskGroup;
import scoutmgr.client.entity.Person;
import scoutmgr.client.entity.TaskCompletion;
import scoutmgr.client.entity.TaskGroupCompletion;
import scoutmgr.client.resource.ScoutmgrResourceBundle;

public class BadgeworkView
  extends ViewWithUiHandlers<BadgeworkUiHandlers>
  implements BadgeworkPresenter.View, ClickHandler
{
  public static final String INCOMPLETE_ICON_COLOUR = "grey lighten-3";
  public static final String COMPLETE_ICON_COLOUR = "green";
  @UiField
  ScoutmgrResourceBundle _bundle;
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
  public void setBadgeworkProgress( final ArrayList<BadgeCategory> badgeCategories, final Person scout )
  {
    Collections.sort( badgeCategories, ( o1, o2 ) -> o1.getRank() - o2.getRank() );
    for ( final BadgeCategory badgeCategory : badgeCategories )
    {
      _expandable.add( createBadgeCategoryView( badgeCategory, scout ) );
    }
  }

  private MaterialCollapsibleItem createBadgeCategoryView( final BadgeCategory badgeCategory, final Person scout )
  {
    final MaterialCollapsibleItem item = new MaterialCollapsibleItem();
    final MaterialCollapsibleHeader header = new MaterialCollapsibleHeader();
    final MaterialLink link = new MaterialLink();
    link.setText( badgeCategory.getName() );
    header.add( link );
    item.add( header );
    final MaterialCollapsibleBody body = new MaterialCollapsibleBody();
    body.add( createBadgesView( badgeCategory, scout ) );
    item.add( body );
    return item;
  }

  private Widget createBadgesView( final BadgeCategory badgeCategory, final Person scout )
  {
    final MaterialRow row = new MaterialRow();
    for ( final Badge badge : badgeCategory.getBadges() )
    {
      final MaterialColumn column = new MaterialColumn();
      column.setGrid( "s12 m4 l3" );
      final MaterialCard card = new MaterialCard();

      final MaterialCardContent summaryCardContent = new MaterialCardContent();
      summaryCardContent.addStyleName( _bundle.scoutmgr().badgeCardSummary() );
      final MaterialCardTitle summaryTitle = new MaterialCardTitle();
      summaryTitle.setText( badge.getName() );
      summaryTitle.setIconType( IconType.MORE_VERT );
      summaryTitle.setIconPosition( IconPosition.RIGHT );
      summaryCardContent.add( summaryTitle );
      final HTML summaryDescription = new HTML( badge.getDescription().replaceAll( "\\n", "<br />" ) );
      summaryDescription.addStyleName( _bundle.scoutmgr().badgeCardDescription() );
      summaryCardContent.add( summaryDescription );
      card.add( summaryCardContent );
      final HTML summaryProgress = new HTML( "<progress max='100' value='80'/>" );
      summaryProgress.addStyleName( TextAlign.CENTER.getCssName() );
      summaryCardContent.add( summaryProgress );

      final MaterialCardReveal reveal = new MaterialCardReveal();
      reveal.addStyleName( _bundle.scoutmgr().badgeCardDetail() );
      final MaterialCardTitle cardTitle = new MaterialCardTitle();

      cardTitle.setText( badge.getName() );
      cardTitle.setIconType( IconType.CLOSE );
      cardTitle.setIconPosition( IconPosition.RIGHT );
      reveal.add( cardTitle );
      final HTML description = new HTML( badge.getDescription().replaceAll( "\\n", "<br />" ) );
      description.addStyleName( _bundle.scoutmgr().badgeCardDescription() );
      reveal.add( description );

      final MaterialCollection badgeTaskCollection = new MaterialCollection();
      final List<BadgeTaskGroup> badgeTaskGroups = badge.getBadgeTaskGroups();
      int x = 1;
      for ( final BadgeTaskGroup badgeTaskGroup : badgeTaskGroups )
      {
        final MaterialCollectionItem item = new MaterialCollectionItem();
        item.addStyleName( _bundle.scoutmgr().badgeTaskGroup() );
        final MaterialLabel taskGroupLabel = new MaterialLabel( "" + x + ":  " + badgeTaskGroup.getDescription() );
        item.add( taskGroupLabel );
        badgeTaskCollection.add( item );

        if ( badgeTaskGroup.getBadgeTasks().isEmpty() )
        {
          final MaterialCollectionSecondary secondary = new MaterialCollectionSecondary();
          final MaterialIcon icon = new MaterialIcon( IconType.VERIFIED_USER );
          icon.setIconSize( IconSize.SMALL );
          if ( hasCompleted( badgeTaskGroup, scout ) )
          {
            icon.setIconColor( COMPLETE_ICON_COLOUR );
          }
          else
          {
            icon.setIconColor( INCOMPLETE_ICON_COLOUR );
          }
          secondary.add( icon );
          item.add( secondary );
        }
        else
        {
          char y = 'a';
          for ( final BadgeTask badgeTask : badgeTaskGroup.getBadgeTasks() )
          {
            final MaterialCollectionItem subItem = new MaterialCollectionItem();
            subItem.addStyleName( _bundle.scoutmgr().badgeTask() );
            final MaterialLabel child = new MaterialLabel( y + ":  " + badgeTask.getDescription() );
            subItem.add( child );

            final MaterialCollectionSecondary secondary = new MaterialCollectionSecondary();
            final MaterialIcon icon = new MaterialIcon( IconType.VERIFIED_USER );
            icon.setIconSize( IconSize.SMALL );
            if ( hasCompleted( badgeTask, scout ) )
            {
              icon.setIconColor( COMPLETE_ICON_COLOUR );
            }
            else
            {
              icon.setIconColor( INCOMPLETE_ICON_COLOUR );
            }
            secondary.add( icon );
            subItem.add( secondary );
            badgeTaskCollection.add( subItem );
            y++;
          }
        }
        x++;
      }
      reveal.add( badgeTaskCollection );

      card.add( reveal );

      final MaterialCardAction actions = new MaterialCardAction();
      final MaterialLink progressLink = new MaterialLink( "Record Progress" );
      progressLink.setDataAttribute( "badge", String.valueOf( badge.getID() ) );
      progressLink.addClickHandler( this );
      actions.add( progressLink );
      card.add( actions );
      column.add( card );
      row.add( column );
    }
    return row;
  }

  private boolean hasCompleted( final BadgeTaskGroup badgeTaskGroup, final Person scout )
  {
    for ( final TaskGroupCompletion completion : scout.getTaskGroupCompletions() )
    {
      if ( completion.getBadgeTaskGroup().getID().equals( badgeTaskGroup.getID() ) )
      {
        return true;
      }
    }
    return false;
  }

  private boolean hasCompleted( final BadgeTask badgeTask, final Person scout )
  {
    for ( final TaskCompletion completion : scout.getTaskCompletions() )
    {
      if ( completion.getBadgeTask().getID().equals( badgeTask.getID() ) )
      {
        return true;
      }
    }
    return false;
  }

  @Override
  public void reset()
  {
    _expandable.clear();
  }

  @Override
  public void onClick( final ClickEvent event )
  {
    final String badgeID = ( (MaterialLink) event.getSource() ).getDataAttribute( "badge" );
    getUiHandlers().requestRecordProgress( Integer.valueOf( badgeID ) );
  }

  @Override
  public void setInSlot( final Object slot, final IsWidget content )
  {
    super.setInSlot( slot, content );
    if ( BadgeworkPresenter.POPUP_PROGRESS_PANEL_SLOT.equals( slot ) )
    {
      RootPanel.get().add( content );
    }
  }
}
