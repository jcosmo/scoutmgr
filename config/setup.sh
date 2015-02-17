#!/bin/bash

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
if [ -f "${CURRENT_DIR}/local.sh" ]; then
  echo "Running local customization script '${CURRENT_DIR}/local.sh'."
  . "${CURRENT_DIR}/local.sh"
fi

DB_SERVER_HOST=${DB_SERVER_HOST:-127.0.0.1}
DB_SERVER_PORT=${DB_SERVER_PORT:-5432}

DB_PROPS_PREFIX="ServerName=${DB_SERVER_HOST}:User=stock-dev:Password=letmein:PortNumber=${DB_SERVER_PORT:DatabaseName="

CREATED_DOMAIN=false
STOP_DOMAIN=false

R=`(asadmin list-domains | grep -q 'myproject ') && echo yes`
if [ "$R" != 'yes' ]; then
  asadmin create-domain --user admin --nopassword myproject
  CREATED_DOMAIN=true
fi

R=`(asadmin list-domains | grep -q 'myproject running') && echo yes`
if [ "$R" != 'yes' ]; then
  STOP_DOMAIN=true
  asadmin start-domain myproject
  if [ "$CREATED_DOMAIN" == 'true' ]; then
    asadmin delete-jvm-options -XX\\:MaxPermSize=192m
    asadmin delete-jvm-options -Xmx512m
    asadmin create-jvm-options -XX\\:MaxPermSize=400m
    asadmin create-jvm-options -Xmx1500m
    asadmin create-jvm-options -Dcom.sun.enterprise.tools.admingui.NO_NETWORK=true
    asadmin restart-domain myproject
  fi
fi

R=`(asadmin list-libraries | grep -q postgresql-9.1-901.jdbc4.jar) && echo yes`
if [ "$R" != 'yes' ]; then
  asadmin add-library ~/.m2/repository/postgresql/postgresql/9.1-901.jdbc4/postgresql-9.1-901.jdbc4.jar
  asadmin restart-domain arena
fi

asadmin delete-jdbc-resource jdbc/Myproject
asadmin delete-jdbc-connection-pool MyprojectConnectionPool

asadmin create-jdbc-connection-pool\
  --datasourceclassname org.postgresql.ds.PGSimpleDataSource\
  --restype javax.sql.DataSource\
  --isconnectvalidatereq=true\
  --validationmethod auto-commit\
  --ping true\
  --description "Myproject Connection Pool"\
  --property "${DB_PROPS_PREFIX}${USER}_MYPROJECT_DEV" MyprojectConnectionPool
asadmin create-jdbc-resource --connectionpoolid MyprojectConnectionPool jdbc/Myproject

asadmin set-log-levels javax.enterprise.resource.resourceadapter.com.sun.gjc.spi=WARNING
asadmin set-log-levels javax.enterprise.resource.jta=OFF

# Disable transaction recovery as can stall startup due to a bug in GlassFish 4.1
asadmin set configs.config.server-config.transaction-service.automatic-recovery=false

if [ "$STOP_DOMAIN" == 'true' ]; then
  asadmin stop-domain myproject
fi
