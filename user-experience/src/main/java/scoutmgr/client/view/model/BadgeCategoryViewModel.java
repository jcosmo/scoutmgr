package scoutmgr.client.view.model;

import scoutmgr.client.entity.ScoutLevel;

public class BadgeCategoryViewModel
  extends AbstractViewModel
{
  private String _name;
  private int _rank;
  private ScoutLevel _level;
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

  public ScoutLevel getLevel()
  {
    return _level;
  }

  public void setLevel( final ScoutLevel level )
  {
    _level = level;
  }
}
