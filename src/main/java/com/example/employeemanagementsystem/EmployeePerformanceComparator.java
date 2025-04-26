package com.example.employeemanagementsystem;

import com.example.employeemanagementsystem.exceptions.RatingCannotBeNullException;

import java.util.Comparator;

public class EmployeePerformanceComparator<T> implements Comparator<Employee<T>> {
    @Override
    public int compare(Employee<T> o1, Employee<T> o2) {
        if (Double.valueOf(o1.getPerformanceRating()) == null || Double.valueOf(o2.getPerformanceRating()) == null) {
            throw new RatingCannotBeNullException("Rating field cannot be null to sort a list");
        }
        if (o1.getPerformanceRating() < o2.getPerformanceRating()) {
            return 1;
        } else {
            return -1;
        }
    }
}
