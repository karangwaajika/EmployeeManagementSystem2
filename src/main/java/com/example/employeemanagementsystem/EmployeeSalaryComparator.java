package com.example.employeemanagementsystem;

import com.example.employeemanagementsystem.exceptions.SalaryCannotBeNullException;

import java.util.Comparator;

public class EmployeeSalaryComparator<T> implements Comparator<Employee<T>> {

    @Override
    public int compare(Employee<T> o1, Employee<T> o2) {
        if (Double.valueOf(o1.getSalary()) == null || Double.valueOf(o2.getSalary()) == null) {
            throw new SalaryCannotBeNullException("you cannot sort when salary is null");
        }
        if (o1.getSalary() < o2.getSalary()) {
            return 1;
        } else {
            return -1;
        }
    }
}
