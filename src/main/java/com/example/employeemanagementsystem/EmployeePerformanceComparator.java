package com.example.employeemanagementsystem;

import java.util.Comparator;

public class EmployeePerformanceComparator<T> implements Comparator<Employee<T>> {
    @Override
    public int compare(Employee<T> o1, Employee<T> o2) {
        if (o1.getPerformanceRating() < o2.getPerformanceRating()) {
            return 1;
        } else {
            return -1;
        }
    }
}
