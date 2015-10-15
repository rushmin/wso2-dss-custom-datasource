package org.wso2.dss.custom.er;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EmployeeRepositoryTest {


    @Test
    public void testQuery() throws Exception {

        EmployeeRepository employeeRepository = EmployeeRepository.getEmployeeRepository();
        populateRepository(employeeRepository);

        List<Employee> result = employeeRepository.query("age<40");

        int expectedEmployeesCount = 2;
        assertTrue(String.format("There should %d employees for the query", expectedEmployeesCount), result.size() == 2);

        Employee employer = result.get(0);
        assertEquals("Employee name is not the expected one", "Anne", employer.getName());

        employer = result.get(1);
        assertEquals("Employee name is not the expected one", "Bill", employer.getName());


    }

    @Test(expected = UnsupportedOperationException.class)
    public void testQueryForUnsupportedOperations1() throws Exception {

        EmployeeRepository employeeRepository = EmployeeRepository.getEmployeeRepository();
        populateRepository(employeeRepository);

        employeeRepository.query("age=40");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testQueryForUnsupportedOperations2() throws Exception {

        EmployeeRepository employeeRepository = EmployeeRepository.getEmployeeRepository();
        populateRepository(employeeRepository);

        employeeRepository.query("name<20");

    }


    private void populateRepository(EmployeeRepository repository){

        repository.add(new Employee("E001", "John", 45, "IT"));
        repository.add(new Employee("E002", "Alan", 42, "IT"));
        repository.add(new Employee("E003", "Paul", 40, "IT"));
        repository.add(new Employee("E004", "Anne", 38, "IT"));
        repository.add(new Employee("E005", "Bill", 36, "IT"));

    }


}