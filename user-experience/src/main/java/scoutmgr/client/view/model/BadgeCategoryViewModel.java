package scoutmgr.client.view.model;

import scoutmgr.client.data_type.BadgeCategoryScoutLevel;

public class BadgeCategoryViewModel
  extends AbstractViewModel
{
  private String _name;
  private int _rank;
  private BadgeCategoryScoutLevel _level;
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

  public BadgeCategoryScoutLevel getLevel()
  {
    return _level;
  }

  public void setLevel( final BadgeCategoryScoutLevel level )
  {
    _level = level;
  }
}
