package com.example.employeemanagementsystem;

public class Employee<T> implements Comparable<Employee<T>> {
    T employeeId;
    private String name;
    private String department;
    private double salary;
    private double performanceRating;
    private int yearsOfExperience;
    private boolean isActive;
    static int nbrOfEmployees = 1;

    Employee(T employeeId, String name, String department,
             double salary, double performanceRating, int yearsOfExperience,
             boolean isActive) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.performanceRating = performanceRating;
        this.yearsOfExperience = yearsOfExperience;
        this.isActive = isActive;
        nbrOfEmployees ++;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public T getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(T employeeId) {
        this.employeeId = employeeId;
    }

    public double getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(double performanceRating) {
        this.performanceRating = performanceRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setIsActive(boolean active) {
        this.isActive = active;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    @Override
    public int compareTo(Employee<T> o) {
        if (this.getYearsOfExperience() < o.getYearsOfExperience()) {
            return 1;
        } else {
            return -1;
        }
    }
}
