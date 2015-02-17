workspace_dir = File.expand_path(File.dirname(__FILE__) + '/..')
$LOAD_PATH.insert(0, "#{workspace_dir}/vendor/plugins/dbt/lib")

require 'dbt'

Dbt::Config.environment = ENV['DB_ENV'] if ENV['DB_ENV']

Dbt.add_database(:default,
                 :imports => {:default => {}}) do |database|
  database.search_dirs = %w(database/generated database)
  database.enable_domgen
  database.version = '1'
end
