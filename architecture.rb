Domgen.repository(:Scoutmgr) do |repository|
  repository.enable_facet(:jpa)
  repository.enable_facet(:ejb)
  repository.enable_facet(:jws)
  repository.enable_facet(:pgsql)
  repository.enable_facet(:imit)

  repository.jpa.provider = :eclipselink

  repository.jpa.base_entity_test_name = repository.jpa.abstract_entity_test_name
  repository.ejb.base_service_test_name = repository.ejb.abstract_service_test_name

  repository.xml.base_namespace = 'http://irisonline.com.au'

  repository.imit.graph(:People)
  repository.imit.graph(:Person)

  repository.data_module(:Scoutmgr) do |data_module|
    data_module.entity(:Person) do |t|
      t.integer(:ID, :primary_key => true)
      t.string(:Name, 255)
      t.i_enum(:Status, %w(CANDIDATE COMMENCED COMPLETED), :nullable=>true)
      t.s_enum(:Status2, %w(CANDIDATE COMMENCED COMPLETED))
      t.query(:FindAllWhereNameLike, 'jpa.jpql' => 'O.name LIKE :Name')

      t.unique_constraint([:Name])
      t.imit.replicate(:People, :type)
      t.imit.replicate(:Person, :instance)
    end

    data_module.struct(:PersonDTO) do |s|
      s.integer(:ID)
      s.text(:Name)
    end

    data_module.service(:PersonnelService) do |s|
      s.method(:GetPeople) do |m|
        m.returns(:struct, :referenced_struct => :PersonDTO, :collection_type => :sequence)
      end
    end
  end
end
