package scoutmgr.client.view.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import scoutmgr.client.entity.Person;

public class ListItemViewModel
  extends AbstractViewModel
{
  public ListItemViewModel( final Person person )
  {
    setModelObject( person );
    setDisplayString( person.getFirstName() + " " + person.getLastName() );
  }
}
