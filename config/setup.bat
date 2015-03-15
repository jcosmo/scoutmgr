rem When you are developing away from DEPI, you can configure the local database
rem using environment variables. It is typical to add a script in config/local.bat
rem that defines the environment variables similar to the following;
rem
rem set USERNAME=jwalker
rem set DB_SERVER_HOST=127.0.0.1
rem set DB_SERVER_PORT=1500
rem

setlocal

set DB_SERVER_HOST=127.0.0.1
set DB_SERVER_PORT=5432

IF EXIST local.bat (
   call local.bat
)

set DB_PROPS_PREFIX=ServerName=%DB_SERVER_HOST%:User=stock-dev:Password=letmein:PortNumber=%DB_SERVER_PORT%:DatabaseName=

set CREATE_DOMAIN=0
IF %CREATE_DOMAIN% == 1 (
  call asadmin stop-domain scoutmgr
  call asadmin delete-domain scoutmgr
  call asadmin create-domain --user admin --nopassword scoutmgr
  call asadmin start-domain scoutmgr
  call asadmin delete-jvm-options -XX\\:MaxPermSize=192m
  call asadmin delete-jvm-options -Xmx512m
  call asadmin create-jvm-options -XX\\:MaxPermSize=400m
  call asadmin create-jvm-options -Xmx1500m
  call asadmin create-jvm-options -Dgwt.codeserver.port=8889
  call asadmin create-jvm-options -Dcom.sun.enterprise.tools.admingui.NO_NETWORK=true
  call asadmin add-library %USERPROFILE%/.m2/repository/org/postgresql/postgresql/9.2-1003-jdbc4/postgresql-9.2-1003-jdbc4.jar
  call asadmin restart-domain scoutmgr
) ELSE (
  call asadmin start-domain scoutmgr
)

call asadmin delete-jdbc-resource jdbc/Scoutmgr
call asadmin delete-jdbc-connection-pool ScoutmgrConnectionPool

echo set DB_PARAM=%DB_PROPS_PREFIX%%USERNAME%_SCOUTMGR_DEV
set DB_PARAM=%DB_PROPS_PREFIX%%USERNAME%_SCOUTMGR_DEV

call asadmin create-jdbc-connection-pool^
  --datasourceclassname org.postgresql.ds.PGSimpleDataSource^
  --restype javax.sql.DataSource^
  --isconnectvalidatereq=true^
  --validationmethod auto-commit^
  --ping true^
  --description "Scoutmgr Connection Pool"^
  --property "%DB_PARAM%" ScoutmgrConnectionPool
call asadmin create-jdbc-resource --connectionpoolid ScoutmgrConnectionPool jdbc/Scoutmgr


call asadmin set-log-levels javax.enterprise.resource.resourceadapter.com.sun.gjc.spi=WARNING
call asadmin set-log-levels javax.enterprise.resource.jta=OFF
call asadmin set configs.config.server-config.transaction-service.automatic-recovery=false

call asadmin stop-domain scoutmgr

:done

endlocal