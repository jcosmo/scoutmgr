workspace_dir = File.expand_path(File.dirname(__FILE__) + '/..')
$LOAD_PATH.unshift(File.expand_path("#{workspace_dir}/vendor/plugins/domgen/lib"))

require 'domgen'

Domgen::Build.define_load_task

Domgen::Build.define_generate_task([:mssql], :key => :sql, :target_dir => 'database/generated')
