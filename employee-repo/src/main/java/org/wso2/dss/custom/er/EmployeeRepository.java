package org.wso2.dss.custom.er;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeRepository {

    private List<Employee> employees;

    public EmployeeRepository() {
        employees = new ArrayList<Employee>();
    }

    public void add(Employee employee){
        employees.add(employee);
    }

    public List<Employee> query(String query){

        List<Employee> results = new ArrayList<Employee>();

        Pattern conditionPattern = Pattern.compile("([a-zA-z]*)([=<>])([0-9a-zA-z]*)");
        Matcher matcher = conditionPattern.matcher(query);

        if(matcher.matches()){
            String conditionVariable = matcher.group(1);
            String conditionOperator = matcher.group(2);
            String conditionValue = matcher.group(3);

            // Only supports age<:x and id=:x type expressions.
            if("age".equals(conditionVariable) && "<".equals(conditionOperator)){
                for(Employee employee : employees){
                    if(employee.getAge() <  Integer.parseInt(conditionValue)){
                        results.add(employee);
                    }
                }
            }else if("id".equals(conditionVariable) && "=".equals(conditionOperator)){
                for(Employee employee : employees){
                    if(employee.getId().equals(conditionValue)){
                        results.add(employee);
                        break;
                    }
                }
            }else{
                throw new UnsupportedOperationException(String.format("%s, %s combination is not supported", conditionVariable, conditionOperator));
            }

        }

        return results;

    }

    public void populate(String data) {

        if(data != null && !data.isEmpty()){
            String[] records = data.split(";");

            for(String record : records){
                String[] fields = record.split(",");
                add(new Employee(fields[0], fields[1], Integer.parseInt(fields[2]), fields[3]));
            }
        }

    }

    public void populateSampleData() {
        populate("E001,John,45,IT;E002,Alan,42,FINANCE;E003,Paul,40,HR;E004,Anne,38,ADMIN;E005,Bill,36,IT");
    }

    public void clear() {
        employees.clear();
    }
}
