task 'dev:test' do
  Dbt::Config.environment = 'test'
  Dbt::Config.config_filename = File.expand_path("#{workspace_dir}/config/database.yml")
  Dbt.repository.load_configuration_data

  jdbc_url = Dbt.configuration_for_key(:default).build_jdbc_url(:credentials_inline => true)
  Buildr.project('scoutmgr.server').test.options[:properties].merge!('test.db.url' => jdbc_url)

  Rake::Task['test'].invoke
end