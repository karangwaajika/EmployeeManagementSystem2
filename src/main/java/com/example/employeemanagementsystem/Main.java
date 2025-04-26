package com.example.employeemanagementsystem;

import com.example.employeemanagementsystem.exceptions.*;

import java.util.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Database<Integer> db = new Database<>(); // initialize database

        Employee<Integer> employee1 = new Employee<>(Employee.nbrOfEmployees,
                "ajika", "HR", 2000, 2.5,
                1, true);
        Employee<Integer> employee2 = new Employee<>(Employee.nbrOfEmployees,
                "Joel", "Finance", 1000, 5.0,
                2, true);
        Employee<Integer> employee3 = new Employee<>(Employee.nbrOfEmployees,
                "Gandhi Nshaka", "HR", 1100000, 4.6,
                3, true);

        // insert employee
        try {
            db.addEmployee(employee1.getEmployeeId(), employee1);
            db.addEmployee(employee2.getEmployeeId(), employee2);
            db.addEmployee(employee3.getEmployeeId(), employee3);
        } catch (InvalidSalaryException | RatingOutOfRangeException | InvalidRatingException |
                 InvalidYearsOfExperienceException | EmptyDepartmentException | EmptyNameException e) {
            logger.log(Level.ERROR, e.getMessage());
        }

        // Delete employee
        try {
            db.removeEmployee(5);
        } catch (EmployeeNotFoundException enfe) {
            logger.log(Level.ERROR, enfe.getMessage());
        }

        // Filter by department
        try {
            db.filterByDepartment("HR");
        } catch (InvalidDepartmentException ide) {
            logger.log(Level.ERROR, ide.getMessage());
        }

//        ################### Report ##########################

        System.out.println("Print Report: ");
        char pipes = '|';
        String underscores = "_";
        String headers = "Name       | Department | Salary     | Rating | Year of Experience |";
        System.out.printf("%s\n", underscores.repeat(headers.length()));
        System.out.println(headers);
        System.out.printf("%s\n", underscores.repeat(headers.length()));
        ArrayList<Employee<Integer>> emploList = db.getAllEmployees();
        int colSpan = 11;
        for (Employee<Integer> employee : emploList) {
            String salary = String.format("%.2f", employee.getSalary());
            String rating = String.format("%.2f", employee.getPerformanceRating());
            String name = employee.getName();
            String department = employee.getDepartment();

            if (name.length() >= colSpan) {
                // Manipulate name to be the firstname and first letter of lastname ex: Ajika K.
                String firstName = name.split(" ")[0];
                char firstLetterOfLastName = name.split(" ")[1].toUpperCase().charAt(0);
                name = firstName.concat(" " + firstLetterOfLastName + ".");
            }
            if (employee.getDepartment().length() >= colSpan) {
                department = department.split(" ")[0];
            }
            int nameSpace = colSpan - name.length();
            int departmentSpace = colSpan - department.length();

            System.out.printf("%s %" + nameSpace + "c %s %" + departmentSpace +
                            "c %-10s %c %-6s %c %-18d %c \n",
                    name, pipes, department, pipes, salary, pipes, rating, pipes,
                    employee.getYearsOfExperience(), pipes);

        }
        System.out.printf("%s\n", underscores.repeat(headers.length()));


    }
}
