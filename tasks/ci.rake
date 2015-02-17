def check_env_key(key, description)
  value = ENV[key]
  unless value
    $stderr.puts("Environment variable #{key} must be specified. #{key} should #{description}")
  end
  value
end

task 'ci:setup' do
  ENV['TEST'] = 'all'
  Dbt::Config.environment = 'test'
  Dbt::Config.config_filename = 'config/ci-database.yml'
  Dbt.repository.load_configuration_data

  jdbc_url = Dbt.configuration_for_key(:default).build_jdbc_url(:credentials_inline => true)
  Buildr.project('myproject:server').test.options[:properties].merge!('test.db.url' => jdbc_url)
  Buildr.project('myproject:integration-tests').test.options[:properties].merge!('test.db.url' => jdbc_url)

  repositories.release_to[:url] = check_env_key('UPLOAD_REPO', 'specify the url to the maven repository')
  repositories.release_to[:username] = check_env_key('UPLOAD_USER', 'specify the user to use to upload to the maven repository')
  repositories.release_to[:password] = check_env_key('UPLOAD_PASSWORD', 'specify the password to use to upload to the maven repository')
end

task 'ci:package' => %w(ci:setup clean domgen:all dbt:create test package dbt:drop myproject:soap-client:upload myproject:soap-qa-support:upload myproject:server:upload)

task 'ci:commit' => %w(ci:setup clean domgen:all myproject:checkstyle:html myproject:pmd:rule:xml myproject:findbugs:xml myproject:jdepend:xml)
