package scoutmgr.server.service.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import scoutmgr.server.data_type.PersonDTO;
import scoutmgr.server.data_type.TaskCompletionDTO;
import scoutmgr.server.data_type.security.TokenDTO;
import scoutmgr.server.entity.BadgeTask;
import scoutmgr.server.entity.Person;
import scoutmgr.server.entity.TaskCompletion;
import scoutmgr.server.entity.dao.BadgeTaskRepository;
import scoutmgr.server.entity.dao.PersonRepository;
import scoutmgr.server.entity.dao.ScoutSectionRepository;
import scoutmgr.server.entity.dao.TaskCompletionRepository;
import scoutmgr.server.service.PersonnelService;

@Stateless( name = PersonnelService.NAME )
public class AuthenticationServiceEJB
  implements AuthenticationService
{
  @Override
  public void logout( @Nonnull final String token )
  {

  }

  @Override
  @Nullable
  public Integer reAuthenticate( @Nonnull final String token )
  {
    return null;
  }

  @Override
  @Nullable
  public TokenDTO authenticate( @Nonnull final HttpServletRequest servlet,
                                @Nonnull final String username,
                                @Nonnull final String password )
  {
    return null;
  }
}
