require 'redfish_plus'
require 'buildr_plus'
BuildrPlus::FeatureManager.activate_features([:oss, :timerstatus, :docker])

Redfish.domain('scoutmgr') do |d|
  RedfishPlus.add_realm(d, 'scoutmgr_auth', 'com.sun.enterprise.security.auth.realm.jdbc.JDBCRealm',
    'jaas-context' => 'jdbcRealm',
    'datasource-jndi' => 'scoutmgr/jdbc/Scoutmgr',
    'user-table' => '"Security"."tblCredential"',
    'user-name-column' => '"UserName"',
    'password-column' => '"Password"',
    'group-table' => '"Security"."tblCredential"',
    'group-name-column' => '"UserName"',
    'encoding' => 'HEX')
end

BuildrPlus::Redfish.customize_local_domain do |d|
  RedfishPlus.set_default_auth_realm(d, 'scoutmgr_auth')
end

BuildrPlus::Redfish.customize_docker_domain do |d|
  RedfishPlus.set_default_auth_realm(d, 'scoutmgr_auth')
end
