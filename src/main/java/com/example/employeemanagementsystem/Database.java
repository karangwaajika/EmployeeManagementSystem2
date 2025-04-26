package com.example.employeemanagementsystem;

import java.util.*;

public class Database<T> {
    private final HashMap<T, Employee<T>> employees = new HashMap<>();

    public String addEmployee(T employeeId, Employee<T> employee) {
        employees.put(employeeId, employee);
        return "Employee's added successfully !!";
    }

    public ArrayList<Employee<T>> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    public String removeEmployee(T employeeId) {
        if (employees.containsKey(employeeId)) {
            employees.remove(employeeId);
            return "Employee's deleted successfully !!";
        }
        return "Employee Id doesn't exist";

    }

    public void updateEmployeeDetails(T employeeId, String field, Object newValue) {

        Employee<T> employee = employees.get(employeeId);
        if (newValue instanceof Integer && field.equals("yearsOfExperience")) {
            employee.setYearsOfExperience((Integer) newValue);
        } else if (newValue instanceof Double) {
            if (field.equals("salary")) {
                employee.setSalary((Double) newValue);
            } else {
                employee.setPerformanceRating((Double) newValue);
            }
        } else if (newValue instanceof String) {
            if (field.equals("department")) {
                employee.setDepartment((String) newValue);
            } else {
                employee.setName((String) newValue);
            }
        } else {
            if (newValue instanceof Boolean) {
                employee.setIsActive((Boolean) newValue);
            }
        }
        System.out.println("Updated successfully ");
    }

    public List<Employee<T>> filterByDepartment(String field) {
        return employees.values().stream()
                .filter(n -> n.getDepartment().equals(field)).toList();
    }

    public List<Employee<T>> filterByName(String name) {
        return employees.values().stream()
                .filter(n -> n.getName().contains(name)).toList();
    }

    public List<Employee<T>> searchMinimumRating(double rating) {
        return employees.values().stream()
                .filter(n -> n.getPerformanceRating() >= rating).toList();
    }

    public List<Employee<T>> searchRangeSalary(double salary1, double salary2) {
        return employees.values().stream()
                .filter(n -> salary1 <= n.getSalary() && n.getSalary() <= salary2)
                .toList();
    }

    public ArrayList<Employee<T>> giveSalaryRaise(double minPerformanceRating) {
        double raiseAmount = 500.0;
        ArrayList<Employee<T>> ls = new ArrayList<>(employees.values());
        Iterator<Employee<T>> iter = ls.iterator();
        while (iter.hasNext()) {
            Employee<T> employee = iter.next();
            double salary = employee.getSalary();
            double ratingPerformance = employee.getPerformanceRating();

            // for employees with a min rating, update salary
            if (ratingPerformance >= minPerformanceRating) {
                employee.setSalary(salary + raiseAmount);
            }
        }
        return ls;

    }

    public List<Employee<T>> retrieveTopFiveHighestPaid() {
        ArrayList<Employee<T>> employeeList = new ArrayList<>(employees.values());
        // first, sort employee salary from high to low
        Comparator<Employee<T>> comparator = new Comparator<>() {
            @Override
            public int compare(Employee<T> o1, Employee<T> o2) {
                if (o1.getSalary() < o2.getSalary()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };
        Collections.sort(employeeList, comparator);

        // retrieve only five employees from the sorted employees
        if (employeeList.size() >= 5) {
            return employeeList.subList(0, 4);
        }
        return employeeList;

    }

    public HashMap<String, ArrayList<Double>> getAverageDepartmentSalary() {
        /*{
            "HR": [2000, 1],
            "IT": [34000, 3]
          }
        above is the goal to get average in each department. value[0] is total amount and
        value[1] is the total number of employees*/

        HashMap<String, ArrayList<Double>> departmentToSalaries = new HashMap<>();

        List<Employee<T>> employeeList = new ArrayList<>(employees.values())
                .stream().toList();
        Iterator<Employee<T>> iterator = employeeList.iterator();
        while (iterator.hasNext()) {
            Employee<T> employee = iterator.next();
            String department = employee.getDepartment();
            // if department name exist already in hashmap
            if (departmentToSalaries.containsKey(department)) {
                ArrayList<Double> salaryList = departmentToSalaries.get(department); // value array

                double salary = salaryList.get(0) + employee.getSalary(); // old salary + new one
                double nbrOfEmployees = salaryList.get(1) + 1; // old + new nbr of employees

                salaryList.add(0, salary);
                salaryList.add(1, nbrOfEmployees);

                departmentToSalaries.put(department, salaryList); // update the hashMap
            } else {
                // for a new department
                ArrayList<Double> salaryList = new ArrayList<>(); // an array to store amount and nbr of employees

                salaryList.add(employee.getSalary()); //store in index 0
                double nbrOfEmployees = 1;
                salaryList.add(nbrOfEmployees); // store in index 1

                departmentToSalaries.put(department, salaryList);
            }

        }
        return departmentToSalaries;

    }
}
