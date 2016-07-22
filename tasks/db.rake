require 'dbt'

Dbt::Config.environment = ENV['DB_ENV'] if ENV['DB_ENV']
Dbt::Config.driver = 'postgres'

Dbt.add_database(:default,
                 :imports => {:default => {}},
                 :datasets => [:dev]
                 ) do |database|
  database.search_dirs = %w(database/generated database)
  database.enable_domgen
  database.version = '1'
end
