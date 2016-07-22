package scoutmgr.server.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Typed;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional( Transactional.TxType.REQUIRED )
@Typed( SessionContext.class )
public class SessionContextImpl
  extends AbstractSessionContextImpl
  implements SessionContext
{
}
