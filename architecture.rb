Domgen.repository(:Scoutmgr) do |repository|
  repository.enable_facet(:jpa)
  repository.enable_facet(:ejb)
  repository.enable_facet(:jws)
  repository.enable_facet(:pgsql)
  repository.enable_facet(:imit)

  repository.jpa.provider = :eclipselink

  repository.jpa.base_entity_test_name = repository.jpa.abstract_entity_test_name
  repository.ejb.base_service_test_name = repository.ejb.abstract_service_test_name

  repository.xml.base_namespace = 'http://tharsis-gate.org'

  repository.imit.graph(:Metadata)
  repository.imit.graph(:Person)
  repository.imit.graph(:People)

  repository.data_module(:Scoutmgr) do |data_module|

    data_module.entity(:ScoutLevel) do |t|
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
      t.reference(:ScoutLevel)

      t.unique_constraint([:FirstName, :LastName, :Dob])
      t.imit.replicate(:Person, :instance)
      t.imit.replicate(:People, :type)

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

    data_module.entity(:User) do |t|
      t.integer(:ID, :primary_key => true)
      t.string(:UserName, 255)
      t.string(:Password, 255)
      t.string(:Salt, 255)
      t.boolean(:Active)
    end

#    user
#    opt link to scout
#    email
#    permission
#      global view
#      admin members and groups
#      admin users, assign groups
#      PL/sixer for group
#      leader for section

#    audit history/activity stream


           data_module.entity(:BadgeCategory) do |t|
      t.integer(:ID, :primary_key => true)
      t.reference(:ScoutLevel)
      t.string(:Name, 255)
      t.integer(:Rank)

      t.unique_constraint([:ScoutLevel, :Name])

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

    data_module.entity(:BadgeTaskGroup) do |t|
      t.integer(:ID, :primary_key => true)
      t.reference(:Badge, 'inverse.traversable' => true)
      t.string(:Description, 255)
      t.integer(:Rank)

      t.imit.replicate(:Metadata, :type)
    end

    data_module.entity(:BadgeTask) do |t|
      t.integer(:ID, :primary_key => true)
      t.reference(:BadgeTaskGroup, 'inverse.traversable' => true)
      t.string(:Description, 255)
      t.integer(:Rank)

      t.imit.replicate(:Metadata, :type)
    end

    data_module.entity(:TaskGroupCompletion) do |t|
      t.integer(:ID, :primary_key => true)
      t.reference(:Person, 'inverse.traversable' => true)
      t.reference(:BadgeTaskGroup)

      t.unique_constraint([:Person, :BadgeTaskGroup])
    end

    data_module.entity(:TaskCompletion) do |t|
      t.integer(:ID, :primary_key => true)
      t.reference(:Person, 'inverse.traversable' => true)
      t.reference(:BadgeTask)

      t.unique_constraint([:Person, :BadgeTask])
    end

    data_module.struct(:PersonDTO) do |s|
      s.integer(:ID)
      s.text(:ScoutLevel)
      s.text(:FirstName)
      s.text(:LastName)
      s.date(:Dob)
      s.text(:RegistrationNumber)
    end

    data_module.service(:PersonnelService) do |s|
      s.method(:GetPeople) do |m|
        m.returns(:struct, :referenced_struct => :PersonDTO, :collection_type => :sequence)
      end

      s.method(:AddScout) do |m|
        m.string :ScoutLevel, 255
        m.string :FirstName, 255
        m.string :LastName, 255
        m.date :Dob
        m.string :RegistrationNumber, 20
      end

      s.method(:UpdateScout) do |m|
        m.integer(:IdForUpdate)
        m.string :ScoutLevel, 255
        m.string :FirstName, 255
        m.string :LastName, 255
        m.date :Dob
        m.string :RegistrationNumber, 20
      end

      s.method(:DeleteScout) do |m|
        m.integer(:ID)
      end
    end

    data_module.message(:MetadataLoaded)
  end
end
