### WSO2 DSS Custom Data Source (Connector) Sample

This project contains a sample custom data source for WSO2 DSS.

The project has two modules

#####employee-repo

A mock legacy database which has a Java API

#####employee-ds

The custom data source implementation which connects to the employee-repo

This data source implementation introduces a query language to query the legacy database.

e.g.
* query : name,age;id=E001
* explanation : give me the name and the age of the employee whose id is E0001

####Requirements

WSO2 DSS 3.2.2


####How to deploy the sample

1) Build the project. (mvn clean install)

Deploy the following two jar files to DSS_HOME/repository/components/lib

* employee-repo/target/org.wso2.dss.custom.employee-repo-1.0.0-SNAPSHOT.jar
* employee-ds/target/org.wso2.dss.custom.employee-ds-1.0.0-SNAPSHOT.jar


2) Copy the data service definition (employee-ds/src/main/resources/employee-data-service.dbs) to DSS_HOME/repository/deployment/server/dataservices/

####How to test

NOTE : This sample comes with pre-loaded sample data.

Above data service has two operations

1) Query employees by id

The following cURL command fetches the employee with the id 'E001'

```
curl -H "Accept: application/json" http://localhost:9763/services/EmployeeDataService.HTTPEndpoint/employer/E001
```


2) Query employees by age

The following cURL command fetches the employee who is below 40

```
curl -H "Accept: application/json" http://localhost:9763/services/EmployeeDataService.HTTPEndpoint/employer/age/40
```
