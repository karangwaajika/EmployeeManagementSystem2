package com.example.employeemanagementsystem;

import com.example.employeemanagementsystem.exceptions.InvalidDepartmentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database<Integer> db = new Database<>();

    @BeforeEach
    void setUp() {
        Employee<Integer> employee1 = new Employee<>(Employee.nbrOfEmployees,
                "Patricia", "Finance", 340000, 1.9,
                3, true);
        Employee<Integer> employee2 = new Employee<>(Employee.nbrOfEmployees,
                "Solange Purse", "Operation", 560000, 2.5,
                1, true);
        db.addEmployee(employee1.getEmployeeId(), employee1);
        db.addEmployee(employee2.getEmployeeId(), employee2);
    }

    @Test
    void addEmployee_withoutAnyError_returnsSuccessMessage() {
        Employee<Integer> employee = new Employee<>(Employee.nbrOfEmployees,
                "ajika", "HR", 2000, 2.5,
                1, true);

        assertEquals("Employee's added successfully !!",
                db.addEmployee(employee.employeeId, employee));
    }

    @Test
    void retrieveAnEmployee_whoIsStoredInHashMap_shouldNotReturnNull() {
        Employee<Integer> employee = new Employee<>(Employee.nbrOfEmployees,
                "Caleb", "IT", 800000, 4.5,
                2, true);

        db.addEmployee(employee.getEmployeeId(), employee);
        assertNotNull(db.getAllEmployees().get(0));

    }

    @Test
    void searchForEmployeesByDepartment_whichDoesNotExist_shouldThrowException() {
        Employee<Integer> employee = new Employee<>(Employee.nbrOfEmployees,
                "Janvier", "IT", 800000, 4.5,
                2, true);

        db.addEmployee(employee.getEmployeeId(), employee);
        assertThrows(InvalidDepartmentException.class, () -> {
            db.filterByDepartment("Coders");
        });
    }

    @Test
    void deleteEmployee_usingEmployeeID_shouldReturnTheDeletedID() {
        Employee<Integer> employee = new Employee<>(Employee.nbrOfEmployees,
                "Deborah", "IT", 805000, 4.5,
                4, true);
        db.addEmployee(employee.getEmployeeId(), employee);
        assertEquals(employee.employeeId+1, db.removeEmployee(employee.getEmployeeId()));

    }
    
}