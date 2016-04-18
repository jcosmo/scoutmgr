require 'buildr/pmd'
require 'buildr/gwt'
require 'buildr/checkstyle'
require 'buildr/single_intermediate_layout'
require 'buildr/findbugs'
require 'buildr/jdepend'
require 'buildr/git_auto_version'
require 'buildr/jacoco'
require 'buildr/top_level_generate_dir'

GUICE_DEPS = [:google_guice, :google_guice_assistedinject, :aopalliance]
GIN_DEPS = GUICE_DEPS + [:gwt_gin, :javax_inject]
APPCACHE_DEPS = [:gwt_appcache_client, :gwt_appcache_linker, :gwt_appcache_server]
GWTP_DEPS = [:gwtp_mvp, :gwtp_mvp_shared, :gwtp_clients_common, :gwtp_velocity, :gwtp_velocity_deps]
GWT_DEPS = [:gwt_user, :gwt_property_source, :gwt_datatypes, :gwt_webpoller, :gwt_lognice] + GIN_DEPS + APPCACHE_DEPS + GWTP_DEPS
ANNOTATION_DEPS = [:javax_jsr305, :findbugs_annotations]
COMMON_PROVIDED_DEPS = ANNOTATION_DEPS + [:javax_javaee]
JACKSON_DEPS = [:jackson_core, :jackson_mapper]
GUICE_TEST_JARS = GUICE_DEPS + [:guiceyloops]
TEST_DEPS = [:glassfish_embedded, :postgresql, :mockito] + GUICE_TEST_JARS
PACKAGED_DEPS = [:gwt_datatypes, :replicant, :gwt_servlet, :simple_session_filter, :field_filter, :gwt_appcache_server, :gwt_cache_filter] + JACKSON_DEPS

desc 'Scoutmgr: Website for the management of progress for a scout group'
define 'scoutmgr' do
  project.group = 'scoutmgr'
  compile.options.source = '1.8'
  compile.options.target = '1.8'
  compile.options.lint = 'all'

  project.version = ENV['PRODUCT_VERSION'] if ENV['PRODUCT_VERSION']

  define 'shared' do
    compile.using :javac

    package(:jar)
    package(:sources)

    iml.add_gwt_facet({'scoutmgr.ScoutmgrConstants' => false},
                      :settings => {:compilerMaxHeapSize => '1024'},
                      :gwt_dev_artifact => :gwt_dev)
  end

  define 'model' do
    Domgen::Build.define_generate_task([:ee_data_types, :jaxb_marshalling_tests, :xml_xsd_resources, :jpa, :jpa_ejb_dao, :imit_server_entity_listener])

    compile.using :javac
    compile.with COMMON_PROVIDED_DEPS,
                 JACKSON_DEPS,
                 :gwt_datatypes,
                 project('shared').package(:jar),
                 project('shared').compile.dependencies

    test.using :testng

    package(:jar)
    package(:sources)

    iml.add_jpa_facet
    iml.add_ejb_facet
  end

  define 'model-qa-support' do
    Domgen::Build.define_generate_task([:jpa_main_qa, :jpa_main_qa_external, :ejb_main_qa_external])

    compile.with project('model').package(:jar),
                 project('model').compile.dependencies,
                 :testng,
                 :mockito,
                 GUICE_TEST_JARS

    test.options[:properties].merge!('test.db.url' => Dbt.configuration_for_key(:default).build_jdbc_url(:credentials_inline => true))

    package(:jar)
    package(:sources)
  end

  define 'gwt' do
    domgen_dir = Domgen::Build.define_generate_task([:gwt_client_jso, :gwt_client_callback, :gwt_rpc_shared, :gwt_rpc_client_service, :imit_shared, :imit_client_service, :imit_client_entity])

    compile.using :javac
    compile.with ANNOTATION_DEPS,
                 GWT_DEPS,
                 :replicant,
                 project('shared').package(:jar),
                 project('shared').compile.dependencies

    test.using :testng
    test.with :mockito

    package(:jar).tap do |jar|
      jar.include("#{domgen_dir.target_dir.to_s}/main/java/scoutmgr")
      jar.include("#{_(:source, :main, :java)}/scoutmgr")
    end
    package(:sources)

    check package(:jar), 'should contain generated source files' do
      it.should contain('scoutmgr/shared/net/ScoutmgrReplicationGraph.class')
      it.should contain('scoutmgr/shared/net/ScoutmgrReplicationGraph.java')
    end

    iml.add_gwt_facet({'scoutmgr.ScoutmgrCommon' => false},
                      :settings => {:compilerMaxHeapSize => '1024'},
                      :gwt_dev_artifact => :gwt_dev)
  end

  define 'gwt-qa-support' do
    Domgen::Build.define_generate_task([:imit_client_main_qa, :gwt_rpc_module])

    compile.using :javac
    compile.with project('gwt').package(:jar), project('gwt').compile.dependencies, :testng, :mockito, GUICE_TEST_JARS

    package :jar
    package :sources
  end

  define 'server' do
    Domgen::Build.define_generate_task([:ee_exceptions, :ee_messages, :imit_server_qa, :ejb_service_facades, :ejb_test_qa, :ejb_test_qa_aggregate, :ejb_test_service_test, :jws_server, :ejb_glassfish_config_assets, :gwt_rpc_shared, :gwt_rpc_server, :imit_shared, :imit_server_service, :imit_server_entity_replication, :jpa_dao_test, :jpa_test_qa_aggregate, :jws_wsdl_assets])

    compile.with project('model').package(:jar),
                 project('model').compile.dependencies,
                 COMMON_PROVIDED_DEPS,
                 JACKSON_DEPS,
                 :glassfish_embedded,
                 PACKAGED_DEPS

    test.with project('model-qa-support').package(:jar),
              GUICE_TEST_JARS,
              TEST_DEPS
    test.using :testng

    test.options[:properties].merge!('test.db.url' => Dbt.configuration_for_key(:default).build_jdbc_url(:credentials_inline => true))

    package(:war).tap do |war|
      war.libs.clear
      war.libs << artifacts(PACKAGED_DEPS)
      war.libs << project('model').package(:jar)
    end

    iml.add_ejb_facet
    webroots = {}
    webroots[_(:source, :main, :webapp)] = '/'
    webroots[_(:source, :main, :webapp_local)] = '/'
    assets.paths.each { |path| webroots[path.to_s] = '/' unless path.to_s =~ /generated\/gwt\// }
    iml.add_web_facet(:webroots => webroots)
  end

  define 'user-experience' do
    Domgen::Build.define_generate_task([:gwt_client_event])

    compile.with project('gwt').package(:jar),
                 project('gwt').compile.dependencies

    gwt(['scoutmgr.ScoutmgrProd'],
        :java_args => %w(-Xms512M -Xmx1024M),
        :target_project => 'server',
        :style => 'DETAILED')  # Detailed vs Obfuscated, as Obfuscated is broken in 2.8.0-beta1

    test.with project('gwt-qa-support'),
              :mockito,
              :guiceyloops
    test.using :testng

    iml.add_gwt_facet({'scoutmgr.ScoutmgrDev' => false,
                       'scoutmgr.Scoutmgr' => false,
                       'scoutmgr.ScoutmgrProd' => false},
                      :settings => {:compilerMaxHeapSize => '1024'},
                      :gwt_dev_artifact => :gwt_dev)
  end

  desc 'DB Archive'
  define 'db' do
    project.no_iml
    Dbt.define_database_package(:default)
  end

  ipr.add_component_from_artifact(:idea_codestyle)
  ipr.postgres_dialect_mapping

  Dbt::Buildr.add_idea_data_sources_from_configuration_file

  ipr.add_exploded_war_artifact(project,
                                :dependencies => [project('server'), project('model'), PACKAGED_DEPS],
                                :war_module_names => %w(server),
                                :ejb_module_names => %w(model server),
                                :jpa_module_names => %w(model))

  ipr.add_glassfish_configuration(project, :server_name => 'Payara 4.1.1.154', :exploded => [project.name])

  ipr.add_gwt_configuration(project('user-experience'),
                            :gwt_module => 'scoutmgr.ScoutmgrDev',
                            :vm_parameters => '-Xmx3G',
                            :shell_parameters => "-port 8888 -codeServerPort 8889 -bindAddress 0.0.0.0 -war #{_(:artifacts, project.name)}/",
                            :launch_page => 'http://127.0.0.1:8080/scoutmgr')

  ipr.add_default_testng_configuration(:jvm_args => "-ea -Xmx2024M -XX:MaxPermSize=364m -Dtest.db.url=#{Dbt.jdbc_url_with_credentials(:default, 'test')} -Dwar.dir=#{File.dirname(project('server').package(:war).to_s)} -Dembedded.glassfish.artifacts=#{[artifact(:glassfish_embedded).to_spec, artifact(:postgresql).to_spec].join(',')}")
end
