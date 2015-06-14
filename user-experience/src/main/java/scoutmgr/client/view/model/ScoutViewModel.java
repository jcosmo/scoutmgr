package scoutmgr.client.view.model;

import scoutmgr.client.entity.Person;

public class ScoutViewModel
  extends AbstractViewModel
{
  private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger( ScoutViewModel.class.getName() );

  public ScoutViewModel( final Person person )
  {
    setModelObject( person );
  }

  public String getFirstName()
  {
    LOG.warning( "Getting firstname" );
    if ( null == asModelObject() )
      return "Noone";

    return ((Person)asModelObject()).getFirstName();
  }

  public String getLastName()
  {
    LOG.warning( "Getting lastname" );
    if ( null == asModelObject() )
      return "Noone";
    return ((Person)asModelObject()).getLastName();
  }
}
