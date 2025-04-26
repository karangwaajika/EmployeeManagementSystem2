package com.example.employeemanagementsystem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database<Integer> db = new Database<>();

    @Test
    void addEmployee_withoutAnyError_returnsSuccessMessage() {
        Employee<Integer> employee = new Employee<>(1,
                "ajika", "HR", 2000, 2.5,
                1, true);
        assertEquals("Employee's added successfully !!",
                db.addEmployee(employee.employeeId, employee));
    }

}