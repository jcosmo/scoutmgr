package scoutmgr.client.view.model;

import scoutmgr.client.entity.ScoutSection;

public class BadgeCategoryViewModel
  extends AbstractViewModel
{
  private String _name;
  private int _rank;
  private ScoutSection _section;
  public BadgeCategoryViewModel( final String name, final Object object )
  {
    super(object, name );
  }

  public String getName()
  {
    return _name;
  }

  public void setName( String name )
  {
    _name = name;
  }

  public int getRank()
  {
    return _rank;
  }

  public void setRank( final int rank )
  {
    _rank = rank;
  }

  public ScoutSection getSection()
  {
    return _section;
  }

  public void setSection( final ScoutSection section )
  {
    _section = section;
  }
}
