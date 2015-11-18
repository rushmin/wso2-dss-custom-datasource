package org.wso2.dss.custom.er.ds;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.dataservices.core.DataServiceFault;
import org.wso2.carbon.dataservices.core.custom.datasource.*;
import org.wso2.carbon.dataservices.core.engine.InternalParam;
import org.wso2.dss.custom.er.Employee;
import org.wso2.dss.custom.er.EmployeeRepository;

/**
 * A data source which encapsulates a legacy employer repository.
 */
public class EmployeeDS implements CustomQueryBasedDS {

    private static final Log log = LogFactory.getLog(EmployeeDS.class);
    private EmployeeRepository employeeRepository;

    public void init(Map<String, String> props) throws DataServiceFault {
        employeeRepository = new EmployeeRepository();
        populateRepository();
    }

    public void close() {

    }

    /**
     * Sample query:
     * name,age;id=5
     * name,age,team;age<50
     */
    public QueryResult executeQuery(String query, List<InternalParam> params)
            throws DataServiceFault {

        String[] queryTokens = query.split(";");

        List<String> fields = Arrays.asList(queryTokens[0].split(","));
        String expression = queryTokens[1];

        // Replace the values of placeholders.
        for(InternalParam internalParam : params){
            expression = expression.replace(":" + internalParam.getName(), internalParam.getValue().getValueAsString());
        }

        List<Employee> employees = employeeRepository.query(expression);
        return new EmployeeQueryResult(fields, employees);
    }

    public class EmployeeQueryResult implements QueryResult {

        private List<String> fields;
        private List<Employee> employees;
        private int current = 0;

        public EmployeeQueryResult(List<String> fields, List<Employee> employees) {
            this.fields = fields;
            this.employees = employees;
        }

        public List<DataColumn> getDataColumns() throws DataServiceFault {

            List<DataColumn> result = new ArrayList<DataColumn>();

            for (String field : fields) {
                result.add(new DataColumn(field));
            }

            return result;
        }

        public boolean hasNext() throws DataServiceFault {
            return this.current < employees.size();
        }

        public DataRow next() throws DataServiceFault {

            List<DataColumn> dataColumns = this.getDataColumns();

            Map<String, String> rowData = new HashMap<String, String>();

            Employee employee = employees.get(current);

            for(DataColumn dataColumn : dataColumns){

                String columnName = dataColumn.getName();

                String dataValue = null;
                if("id".equals(columnName)){
                    dataValue = employee.getId();
                }else if("name".equals(columnName)){
                    dataValue = employee.getName();
                }else if("age".equals(columnName)){
                    dataValue = String.valueOf(employee.getAge());
                }else if("team".equals(columnName)){
                    dataValue = employee.getTeam();
                }

                rowData.put(columnName, dataValue);

            }

            this.current++;
            return new FixedDataRow(rowData);
        }

    }

    private void populateRepository() {
        employeeRepository.add(new Employee("E001", "John", 45, "IT"));
        employeeRepository.add(new Employee("E002", "Alan", 42, "IT"));
        employeeRepository.add(new Employee("E003", "Paul", 40, "IT"));
        employeeRepository.add(new Employee("E004", "Anne", 38, "IT"));
        employeeRepository.add(new Employee("E005", "Bill", 36, "IT"));
    }

}
