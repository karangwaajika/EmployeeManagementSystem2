package com.example.employeemanagementsystem;

import java.util.Comparator;

public class EmployeeSalaryComparator<T> implements Comparator<Employee<T>> {

    @Override
    public int compare(Employee<T> o1, Employee<T> o2) {
        if (o1.getSalary() < o2.getSalary()) {
            return 1;
        } else {
            return -1;
        }
    }
}
