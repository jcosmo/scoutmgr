# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with this
# work for additional information regarding copyright ownership.  The ASF
# licenses this file to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
# License for the specific language governing permissions and limitations under
# the License.

raise 'Patch applied in this version of Buildr' unless Buildr::VERSION == '1.4.21'

module Buildr
  module IntellijIdea
    class IdeaProject

      def mssql_dialect_mapping
        sql_dialect_mappings(buildr_project.base_dir => 'TSQL')
      end

      def postgres_dialect_mapping
        sql_dialect_mappings(buildr_project.base_dir => 'PostgreSQL')
      end

      def sql_dialect_mappings(mappings)
        add_component('SqlDialectMappings') do |component|
          mappings.each_pair do |path, dialect|
            file_path = file_path(path).gsub(/\/.$/,'')

            puts "#{file_path} => #{dialect}"
            component.file :url => file_path, :dialect => dialect
          end
        end
      end
    end
  end
end