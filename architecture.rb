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

  repository.imit.graph(:Person)
  repository.imit.graph(:People)

  repository.data_module(:Scoutmgr) do |data_module|
    data_module.entity(:Person) do |t|
      t.integer(:ID, :primary_key => true)
      t.string(:FirstName, 255)
      t.string(:LastName, 255)
      t.datetime(:Dob)
      t.string(:RegistrationNumber, 20)

      t.unique_constraint([:FirstName, :LastName, :Dob])
      t.imit.replicate(:Person, :instance)
      t.imit.replicate(:People, :type)

      t.query(:FindAllWhereNameLike, 'jpa.jpql' => 'O.firstName LIKE :Name OR O.lastName LIKE :Name') do |q|
        q.string(:Name, 255)
      end
    end

    data_module.struct(:PersonDTO) do |s|
      s.integer(:ID)
      s.text(:FirstName)
      s.text(:LastName)
      s.datetime(:Dob)
      s.text(:RegistrationNumber)
    end

    data_module.service(:PersonnelService) do |s|
      s.method(:GetPeople) do |m|
        m.returns(:struct, :referenced_struct => :PersonDTO, :collection_type => :sequence)
      end

      s.method(:AddScout) do |m|
        m.string :FirstName, 255
        m.string :LastName, 255
        m.datetime :Dob
        m.string :RegistrationNumber, 20
      end

      s.method(:UpdateScout) do |m|
        m.integer(:IdForUpdate)
        m.string :FirstName, 255
        m.string :LastName, 255
        m.datetime :Dob
        m.string :RegistrationNumber, 20
      end

      s.method(:DeleteScout) do |m|
        m.integer(:ID)
      end
    end
  end
end
