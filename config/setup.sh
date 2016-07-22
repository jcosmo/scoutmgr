#!/bin/bash

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
if [ -f "${CURRENT_DIR}/local.sh" ]; then
  echo "Running local customization script '${CURRENT_DIR}/local.sh'."
  . "${CURRENT_DIR}/local.sh"
fi

DB_SERVER_HOST=${DB_SERVER_HOST:-127.0.0.1}
DB_SERVER_PORT=${DB_SERVER_PORT:-5432}

DB_PROPS_PREFIX="ServerName=${DB_SERVER_HOST}:User=stock-dev:Password=letmein:PortNumber=${DB_SERVER_PORT}:DatabaseName="

CREATED_DOMAIN=false
STOP_DOMAIN=false

R=`(asadmin list-domains | grep -q 'scoutmgr ') && echo yes`
if [ "$R" != 'yes' ]; then
  asadmin create-domain --user admin --nopassword --template ${GLASSFISH_HOME}/glassfish/common/templates/gf/payara-domain.jar scoutmgr
  CREATED_DOMAIN=true
fi

R=`(asadmin list-domains | grep -q 'scoutmgr running') && echo yes`
if [ "$R" != 'yes' ]; then
  STOP_DOMAIN=true
  asadmin start-domain scoutmgr
  if [ "$CREATED_DOMAIN" == 'true' ]; then
    asadmin delete-jvm-options -XX\\:MaxPermSize=192m
    asadmin delete-jvm-options -Xmx512m
    asadmin create-jvm-options -XX\\:MaxPermSize=400m
    asadmin create-jvm-options -Xmx1500m
    asadmin create-jvm-options -Dcom.sun.enterprise.tools.admingui.NO_NETWORK=true
    asadmin create-jvm-options -Dgwt.codeserver.port=8889
    asadmin restart-domain scoutmgr
  fi
fi

R=`(asadmin list-libraries | grep -q postgresql-9.2-1003.jdbc4.jar) && echo yes`
if [ "$R" != 'yes' ]; then
  asadmin add-library ~/.m2/repository/org/postgresql/postgresql/9.2-1003-jdbc4/postgresql-9.2-1003-jdbc4.jar
  asadmin restart-domain scoutmgr
fi

asadmin delete-jdbc-resource jdbc/Scoutmgr
asadmin delete-jdbc-resource scoutmgr/jdbc/Scoutmgr
asadmin delete-jdbc-connection-pool ScoutmgrConnectionPool

asadmin create-jdbc-connection-pool\
  --datasourceclassname org.postgresql.ds.PGSimpleDataSource\
  --restype javax.sql.DataSource\
  --isconnectvalidatereq=true\
  --validationmethod auto-commit\
  --ping true\
  --description "Scoutmgr Connection Pool"\
  --property "${DB_PROPS_PREFIX}${USER}_SCOUTMGR_DEV" ScoutmgrConnectionPool
asadmin create-jdbc-resource --connectionpoolid ScoutmgrConnectionPool scoutmgr/jdbc/Scoutmgr

asadmin set-log-levels javax.enterprise.resource.resourceadapter.com.sun.gjc.spi=WARNING
asadmin set-log-levels javax.enterprise.resource.jta=OFF

# Disable transaction recovery as can stall startup due to a bug in GlassFish 4.1
asadmin set configs.config.server-config.transaction-service.automatic-recovery=false

asadmin delete-auth-realm scoutmgr_auth
asadmin create-auth-realm --classname com.sun.enterprise.security.auth.realm.jdbc.JDBCRealm --property jaas-context=jdbcRealm:datasource-jndi=scoutmgr/jdbc/Scoutmgr:user-table=\\\"Security\\\".\\\"tblCredential\\\":user-name-column=\\\"UserName\\\":password-column=\\\"Password\\\":group-table=\\\"Security\\\".\\\"tblCredential\\\":group-name-column=\\\"UserName\\\":encoding=HEX:digestrealm-password-enc-algorithm=SHA-256 scoutmgr_auth

if [ "$STOP_DOMAIN" == 'true' ]; then
  asadmin stop-domain scoutmgr
fi

