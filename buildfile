require 'buildr_plus'
BuildrPlus::FeatureManager.activate_features([:oss, :replicant])
BuildrPlus::Java.version = 8
require 'buildr_plus/projects/java_multimodule'

GWT_MATERIAL_DEPS = [:gwt_material_design, :gwt_material_design_addins,  :gwt_material_design_themes]
GWTP_DEPS = [:gwtp_mvp, :gwtp_mvp_shared, :gwtp_clients_common, :gwtp_velocity, :gwtp_velocity_deps]
PACKAGED_DEPS = [:commons_codec]

BuildrPlus::Roles.project('scoutmgr') do
  project.comment = 'Scoutmgr: Website for the management of progress for a scout group'
  project.group = 'scoutmgr'
end

BuildrPlus::Roles.project('user-experience') do
  project.compile.with GWT_MATERIAL_DEPS, GWTP_DEPS
end

BuildrPlus::Roles.project('gwt') do
  project.compile.with GWT_MATERIAL_DEPS, GWTP_DEPS
end

require 'buildr_plus/activate'
