package scoutmgr.client.ioc;

import org.realityforge.replicant.client.net.gwt.BaseFrontendContext;
import scoutmgr.client.entity.Person;
import scoutmgr.client.entity.security.User;

public interface FrontendContext
  extends BaseFrontendContext
{
  User getUser();

  Person getPerson();
}
