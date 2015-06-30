package scoutmgr.client.view.model;

import scoutmgr.client.entity.Person;

public class ScoutViewModel
  extends AbstractViewModel
{
  public ScoutViewModel( final Person person )
  {
    super(person, person.getFirstName() + " " + person.getLastName() );
  }

  public String getFirstName()
  {
    return ((Person)asModelObject()).getFirstName();
  }

  public String getLastName()
  {
    return ((Person)asModelObject()).getLastName();
  }
}
