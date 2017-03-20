Domgen.repository(:Scoutmgr) do |repository|
  repository.enable_facets([:jpa, :ejb, :imit, :pgsql, :appcache, :gwt_cache_filter, :timerstatus])

  repository.jpa.provider = :eclipselink

  repository.gwt.enable_entrypoints = false

  repository.imit.graph(:Metadata)
  repository.imit.graph(:Person, :require_type_graphs => [:Metadata])
  repository.imit.graph(:People, :require_type_graphs => [:Metadata])
  repository.imit.graph(:Users, :require_type_graphs => [:Metadata])
  repository.imit.graph(:User)

  repository.data_module(:Scoutmgr) do |data_module|
    data_module.imit.short_test_code = 'sc'
    data_module.jpa.short_test_code = 'sc'
    data_module.gwt.short_test_code = 'sc'

    data_module.struct(:PeopleFilterDTO) do |s|
      s.integer(:UserID)
      s.imit.filter_for_graph(:People, :immutable => false)
    end

    data_module.entity(:ScoutSection) do |t|
      t.integer(:ID, :primary_key => true)
      t.string(:Code, 255)
      t.integer(:Rank)
      t.imit.replicate(:Metadata, :type)
      t.query(:FindByCode)
      t.query(:GetByCode)
    end

    data_module.entity(:Person) do |t|
      t.integer(:ID, :primary_key => true)
      t.string(:FirstName, 255)
      t.string(:LastName, 255)
      t.date(:Dob)
      t.string(:RegistrationNumber, 20)
      t.reference(:ScoutSection)

      t.unique_constraint([:FirstName, :LastName, :Dob])
      t.imit.replicate(:Person, :instance)
      t.imit.replicate(:People, :type)
      t.imit.replicate(:Users, :type)

      t.query(:FindAllWhereNameLike, 'jpa.jpql' => 'O.firstName LIKE :Name OR O.lastName LIKE :Name') do |q|
        q.string(:Name, 255)
      end
    end

    data_module.entity(:PersonGroup) do |t|
      t.integer(:ID, :primary_key => true)
      t.string(:Name, 255)
    end

    data_module.entity(:PersonGroupMembership) do |t|
      t.integer(:ID, :primary_key => true)
      t.reference(:Person)
      t.reference(:PersonGroupMembership)
    end

# TODO   audit history/activity stream


    data_module.entity(:BadgeCategory) do |t|
      t.integer(:ID, :primary_key => true)
      t.reference(:ScoutSection)
      t.string(:Name, 255)
      t.integer(:Rank)

      t.unique_constraint([:ScoutSection, :Name])

      t.imit.replicate(:Metadata, :type)
    end

    data_module.entity(:Badge) do |t|
      t.integer(:ID, :primary_key => true)
      t.string(:Name, 255)
      t.string(:Description, 1024)
      t.integer(:Rank)

      t.reference(:BadgeCategory, 'inverse.traversable' => true)

      t.imit.replicate(:Metadata, :type)
    end

    data_module.entity(:BadgeTask) do |t|
      t.integer(:ID, :primary_key => true)
      t.reference(:Badge, 'inverse.traversable' => true)
      t.reference(:BadgeTask, :name => :Parent, 'inverse.traversable' => true, :nullable => true)
      t.string(:Description, 255)
      t.integer(:Rank)

      t.imit.replicate(:Metadata, :type)
    end

    data_module.entity(:TaskCompletion) do |t|
      t.integer(:ID, :primary_key => true)
      t.reference(:Person, 'inverse.traversable' => true)
      t.reference(:BadgeTask)
      t.date(:DateCompleted)

      t.unique_constraint([:Person, :BadgeTask])
    end

    data_module.struct(:PersonDTO) do |s|
      s.integer(:ID)
      s.text(:ScoutSection)
      s.text(:FirstName)
      s.text(:LastName)
      s.date(:Dob)
      s.text(:RegistrationNumber)
    end

    data_module.struct(:TaskCompletionDTO) do |s|
      s.integer(:BadgeTaskID)
      s.date(:DateCompleted)
    end

    data_module.service(:PersonnelService) do |s|
      s.method(:GetPeople) do |m|
        m.returns(:struct, :referenced_struct => :PersonDTO, :collection_type => :sequence)
      end

      s.method(:AddScout) do |m|
        m.string :ScoutSection, 255
        m.string :FirstName, 255
        m.string :LastName, 255
        m.date :Dob
        m.string :RegistrationNumber, 20
      end

      s.method(:UpdateScout) do |m|
        m.integer(:IdForUpdate)
        m.string :ScoutSection, 255
        m.string :FirstName, 255
        m.string :LastName, 255
        m.date :Dob
        m.string :RegistrationNumber, 20
      end

      s.method(:UpdateCompletion) do |m|
        m.integer(:PersonID)
        m.integer(:BadgeID)
        m.struct(:TaskCompletionDTO, :TaskCompletionDTO, :collection_type => :sequence)
      end

      s.method(:DeleteScout) do |m|
        m.integer(:ID)
      end
    end

    data_module.message(:MetadataLoaded)

    data_module.service(:DataSubscriptionService) do |s|
      s.method(:SubscribeForUser) do |m|
        m.text(:SessionID)
        m.integer(:UserID)
        m.exception(:BadSession)
      end
      s.method(:UnsubscribeFromUser) do |m|
        m.text(:SessionID)
        m.integer(:UserID)
        m.exception(:BadSession)
      end
    end
  end

  repository.data_module(:Security) do |data_module|
    data_module.imit.short_test_code = 'se'
    data_module.jpa.short_test_code = 'se'
    data_module.gwt.short_test_code = 'se'

    data_module.entity(:User) do |t|
      t.integer(:ID, :primary_key => true)
      t.string(:UserName, 255)
      t.string(:Email, 255, :nullable => true)
      t.boolean(:Active)
      t.reference('Scoutmgr.Person', :nullable => true) do |a|
        a.imit.graph_link(:User, :Person)
      end

      t.imit.replicate(:Users, :type)
      t.imit.replicate(:User, :instance)

      t.query(:FindByUserName)
    end

    data_module.entity(:Credential) do |t|
      t.integer(:ID, :primary_key => true)
      t.string(:UserName, 50)
      t.reference(:User,
                  :immutable => true,
                  'inverse.traversable' => true,
                  'inverse.multiplicity' => :zero_or_one,
                  'inverse.imit.exclude_edges' => [:User])
      t.string(:Password, 128)
      t.string(:Salt, 128)

      t.query(:DeleteUserCredentials, 'jpa.jpql' => 'O.user = :User')
    end

    data_module.entity(:Permission) do |t|
      t.integer(:ID, :primary_key => true)
      # Site admin can administer whole site
      # GlobalView can view all members
      # UserAdmin can administer users, assign to members
      # MemberAdmin can administer members, assign to groups
      # GroupLeader can view/signoff members of the group
      # SectionLeader can view/signoff members of the section
      t.s_enum(:Type, %w(SITE_ADMIN GLOBAL_VIEW USER_ADMIN MEMBER_ADMIN GROUP_LEADER SECTION_LEADER))

      t.reference(:User,
                  :immutable => true,
                  'inverse.traversable' => true)
      t.reference('Scoutmgr.PersonGroup', :nullable => true)
      t.reference('Scoutmgr.ScoutSection', :nullable => true)
    end

    data_module.entity(:Session) do |t|
      t.integer(:ID, :primary_key => true)
      t.reference(:User, :immutable => true)
      t.datetime(:CreatedAt, :immutable => true)
      t.datetime(:UpdatedAt)
      t.string(:Token, 50, :immutable => true)

      t.query(:FindByUser)
      t.query(:FindByUserName)
      t.query(:FindByToken)

      t.query(:DeleteIdleSessions, 'jpa.jpql' => 'O.updatedAt < :UpdatedAt')
      t.query(:DeleteUserSessions, 'jpa.jpql' => 'O.user = :User')

      t.sql.index([:UpdatedAt])
    end

    data_module.struct(:TokenDTO) do |s|
      s.integer(:UserID)
      s.text(:Token)
    end

    data_module.message(:UserLoaded) do |m|
      m.reference(:User)
    end

    data_module.message(:UserLoggedOut)

    data_module.service(:AuthenticationService) do |s|
      # NOTE: Any methods added to this class are not protected by security mechanisms
      # Thus you should disable the gwt facet if you do not want them available across
      # the web
      s.method(:Logout) do |m|
        m.text(:Token)
      end

      s.method(:ReAuthenticate) do |m|
        m.text(:Token)
        m.returns(:integer, :nullable => true) do |a|
          a.description('Return the ID of resource authenticated')
        end
      end

      s.method(:Authenticate) do |m|
        m.parameter(:Servlet, 'javax.servlet.http.HttpServletRequest', 'gwt_rpc.environment_key' => 'request')
        m.text(:Username)
        m.text(:Password)
        m.returns(:struct, :referenced_struct => :TokenDTO, :nullable => true)
      end
    end

    data_module.service(:UserService) do |s|
      s.method(:AddUser) do |m|
        m.text(:UserName)
        m.text(:Email)
        m.text(:Password)
        m.integer(:Scout, :nullable => true)
        m.returns(:reference, :referenced_entity => :User)
        m.exception(:DuplicateUserName)
      end

      s.method(:UpdateUser) do |m|
        m.integer(:IdForUpdate)
        m.text(:Email)
        m.text(:Password, :nullable => true)
        m.integer(:Scout, :nullable => true)
        m.exception(:DuplicateUserName)
      end

      s.method(:DeleteUser) do |m|
        m.integer(:ID)
      end
    end
  end

  repository.data_modules.each do |data_module|
    data_module.services.each do |service|
      service.disable_facet(:jaxrs) if service.jaxrs?
    end
  end
end
