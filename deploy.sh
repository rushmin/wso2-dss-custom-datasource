mvn clean install
cp employee-repo/target/org.wso2.dss.custom.employee-repo-1.0.0-SNAPSHOT.jar ../../../wso2dss-3.2.2/repository/components/lib/
cp employee-ds/target/org.wso2.dss.custom.employee-ds-1.0.0-SNAPSHOT.jar ../../../wso2dss-3.2.2/repository/components/lib/
cp employee-ds/src/main/resources/employee-data-service.dbs ../../../wso2dss-3.2.2/repository/deployment/server/dataservices/

