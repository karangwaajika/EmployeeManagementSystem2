package com.example.employeemanagementsystem;

import java.util.*;

public class Testing {
    public static void main(String[] args) {
        Database<Integer> db = new Database<>(); // initialize database

        Employee<Integer> employee1 = new Employee<>(1,
                "ajika", "HR", 2000, 2.5,
                1, true);
        Employee<Integer> employee2 = new Employee<>(2,
                "joel", "Finance", 1000, 3,
                2, true);
        Employee<Integer> employee3 = new Employee<>(3,
                "Joella Nshaka", "HR", 1100000, 4.5,
                3, true);

        // insert employee to a list
        ArrayList<Employee<Integer>> ls = new ArrayList<>();
        ls.add(employee1);
        ls.add(employee2);
        ls.add(employee3);

        // sort by years of experience descending order using comparable
        Collections.sort(ls);

        // display employees from list
        ls.forEach((employee) -> {
            System.out.printf("%d| name:%s, dep: %s, years: %d \n", employee.employeeId, employee.getName(),
                    employee.getDepartment(), employee.getYearsOfExperience());
        });
        System.out.println("############ END TASK 1 #####################");

// ###################################  END TASK 1 ###############################################

        // insert employee to the database
        db.addEmployee(employee1.employeeId, employee1);
        db.addEmployee(employee2.employeeId, employee2);
        db.addEmployee(employee3.employeeId, employee3);

        // get all the employees from the database
        System.out.println(db.getAllEmployees());

        // update a single attribute
        db.updateEmployeeDetails(1, "yearsOfExperience", 4);
        // update more attributes at once
        HashMap<String, Object> attributesToUpdate = new HashMap<>();
        attributesToUpdate.put("name", "ajika");
        attributesToUpdate.put("department", "IT");
        attributesToUpdate.put("perfornmanceRating", 4.7);

        for (Map.Entry<String, Object> entry : attributesToUpdate.entrySet()) {
            int employeeId = 1;
            db.updateEmployeeDetails(employeeId, entry.getKey(), entry.getValue());
        }


        System.out.println(db.removeEmployee(4)); // remove employee

        System.out.println(db.getAllEmployees());
        ls.forEach((employee) -> {
            System.out.printf("%d| name:%s, dep: %s, years: %d \n", employee.employeeId, employee.getName(),
                    employee.getDepartment(), employee.getYearsOfExperience());
        });
        System.out.println("############ END TASK 2 #####################");

// ###################################  END TASK 2 ###############################################

        //filter by department
        System.out.println("###### search by department #####");
        if (db.filterByDepartment("Finance").toArray().length == 0) {
            System.out.println("nothing");
        } else {
            db.filterByDepartment("Finance")
                    .forEach(n -> System.out.println(n.getName()));
        }

        System.out.println("##### search by name #####");
        //filter by name as auto complete
        if (db.filterByName("j").toArray().length == 0) {
            System.out.println("nothing found");
        } else {
            db.filterByName("j")
                    .forEach(n -> System.out.println(n.getName()));
        }

        System.out.println("##### minimum rating ######");
        //retrieve minimum rating
        if (db.searchMinimumRating(3).toArray().length == 0) {
            System.out.println("nothing found");
        } else {
            db.searchMinimumRating(3)
                    .forEach(n -> System.out.println(n.getName()));
        }

        System.out.println("####### range salary #########");
        if (db.searchRangeSalary(400, 1000).toArray().length == 0) {
            System.out.println("nothing found");
        } else {
            // using an iterator to traverse element
            List<Employee<Integer>> l = db.searchRangeSalary(400, 1000);
            Iterator<Employee<Integer>> itr = l.iterator();
            while (itr.hasNext()) {
                Employee<Integer> employee = itr.next();
                System.out.println(employee.getName());
            }
        }

        System.out.println("############ END TASK 3 #####################");
        // ###################################  END TASK 3 ###############################################
        System.out.println("sort by years of experience");
        ArrayList<Employee<Integer>> a = db.getAllEmployees();
        Collections.sort(db.getAllEmployees());
        Iterator<Employee<Integer>> itr = a.iterator();
        while (itr.hasNext()) {
            Employee<Integer> employee = itr.next();
            System.out.printf("%d| name:%s, dep: %s, years: %d \n", employee.employeeId, employee.getName(),
                    employee.getDepartment(), employee.getYearsOfExperience());
        }

        System.out.println("sort by salary");
        ArrayList<Employee<Integer>> employeesList = db.getAllEmployees();
        Collections.sort(employeesList, new EmployeeSalaryComparator<Integer>());
        Iterator<Employee<Integer>> iter = employeesList.iterator();
        while (iter.hasNext()) {
            Employee<Integer> employee = iter.next();
            System.out.printf("%d| name:%s, dep: %s, years: %d, salary: %f\n", employee.employeeId, employee.getName(),
                    employee.getDepartment(), employee.getYearsOfExperience(), employee.getSalary());
        }

        System.out.println("sort by rating performance");
        ArrayList<Employee<Integer>> dbList = db.getAllEmployees();
        Collections.sort(dbList, new EmployeePerformanceComparator<Integer>());
        Iterator<Employee<Integer>> t = dbList.iterator();
        while (t.hasNext()) {
            Employee<Integer> employee = t.next();
            System.out.printf("%d| name:%s, dep: %s, years: %d, salary: %f, rating: %f\n", employee.employeeId, employee.getName(),
                    employee.getDepartment(), employee.getYearsOfExperience()
                    , employee.getSalary(), employee.getPerformanceRating());
        }

        System.out.println("############ END TASK 4 #####################");
        // ###################################  END TASK 4 ###############################################

        System.out.println("give a salary raise");
        ArrayList<Employee<Integer>> empls = db.giveSalaryRaise(3);
        Iterator<Employee<Integer>> te = empls.iterator();
        while (te.hasNext()) {
            Employee<Integer> employee = te.next();
            System.out.printf("%d| name:%s, dep: %s, years: %d, salary: %f, rating: %f\n", employee.employeeId, employee.getName(),
                    employee.getDepartment(), employee.getYearsOfExperience()
                    , employee.getSalary(), employee.getPerformanceRating());
        }

        System.out.println("give top five highest paid");
        List<Employee<Integer>> empList = db.retrieveTopFiveHighestPaid();
        Iterator<Employee<Integer>> iterate = empList.iterator();
        while (iterate.hasNext()) {
            Employee<Integer> employee = iterate.next();
            System.out.printf("%d| name:%s, dep: %s, years: %d, salary: %f, rating: %f\n", employee.employeeId, employee.getName(),
                    employee.getDepartment(), employee.getYearsOfExperience()
                    , employee.getSalary(), employee.getPerformanceRating());
        }

        System.out.println("average salary in each department");

        HashMap<String, ArrayList<Double>> departmentToSalaries = db.getAverageDepartmentSalary();
        char pipe = '|';
        String underscore = "_";
        String header = "Department | Amount     |";
        System.out.printf("%s\n", underscore.repeat(header.length()));
        System.out.println(header);
        System.out.printf("%s\n", underscore.repeat(header.length()));
        for (Map.Entry<String, ArrayList<Double>> entry : departmentToSalaries.entrySet()) {
            double avg = entry.getValue().get(0) / entry.getValue().get(1);
            String avgString = String.format("%.2f", avg);
            String department = entry.getKey();
            int space = 11 - department.length();
            System.out.printf("%s %" + space + "c %-10s %c \n", department, pipe, avgString, pipe);
        }
        System.out.printf("%s\n", underscore.repeat(header.length()));

        System.out.println("############ END TASK 5 #####################");
        // ###################################  END TASK 5 ###############################################
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
