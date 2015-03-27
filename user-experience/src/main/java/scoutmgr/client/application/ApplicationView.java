package scoutmgr.client.application;

import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ProvidesKey;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import java.util.Collection;
import scoutmgr.client.view.model.AbstractViewModel;
import scoutmgr.client.view.model.ListItemViewModel;

public class ApplicationView
  extends ViewWithUiHandlers<ApplicationUiHandlers>
  implements ApplicationPresenter.View
{
  @UiField( provided = true )
  ValueListBox<ListItemViewModel> _list;

  interface Binder
    extends UiBinder<Widget, ApplicationView>
  {
  }

  @Inject
  ApplicationView( Binder uiBinder )
  {
    _list = new ValueListBox<>(
      new AbstractRenderer<ListItemViewModel>()
      {
        @Override
        public String render( final ListItemViewModel object )
        {
          return object.getDisplayString();
        }
      },
      new ProvidesKey<ListItemViewModel>()
      {
        @Override
        public Object getKey( final ListItemViewModel item )
        {
          return item.getDisplayString();
        }
      } );

    initWidget( uiBinder.createAndBindUi( this ) );
  }

  @Override
  public void resetAndFocus()
  {
  }

  @Override
  public void setItems( final Collection<ListItemViewModel> items )
  {
    _list.setAcceptableValues( items );
  }
}
