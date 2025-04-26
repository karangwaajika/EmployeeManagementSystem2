package com.example.employeemanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Pattern;

public class Controller {
    // input field
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> departmentComboBox;
    @FXML
    private TextField salaryField;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField experienceField;
    @FXML
    private CheckBox isActiveCheckBox;

    // table fields
    @FXML
    private TableView<Employee<Integer>> employeeTable;
    @FXML
    private TableColumn<Employee<Integer>, String> nameColumn;
    @FXML
    private TableColumn<Employee<Integer>, String> departmentColumn;
    @FXML
    private TableColumn<Employee<Integer>, Double> salaryColumn;
    @FXML
    private TableColumn<Employee<Integer>, String> ratingColumn;
    @FXML
    private TableColumn<Employee<Integer>, Integer> experienceColumn;
    @FXML
    private TableColumn<Employee<Integer>, Boolean> activeColumn;

    // action fields
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> departmentFilterComboBox;
    @FXML
    private ComboBox<String> sortComboBox;
    @FXML
    private Button deleteButton;


    private final Database<Integer> db = new Database<>(); // initialize database
    private final ObservableList<Employee<Integer>> employeeList = FXCollections
            .observableArrayList(db.getAllEmployees());


    @FXML
    private void handleSubmit() {
        try {
            String name = nameField.getText();
            String department = departmentComboBox.getValue();
            double salary = Double.parseDouble(salaryField.getText());
            double rating = Double.parseDouble(ratingField.getText());
            int experience = Integer.parseInt(experienceField.getText());
            boolean isActive = isActiveCheckBox.isSelected();
            int nbrOfEmployees = Employee.nbrOfEmployees;

            if (department == null || department.isBlank()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Please select a department.");
                return;
            }
            if (!department.matches("[a-zA-Z\\s]+")) {
                showAlert(Alert.AlertType.ERROR, "Invalid Department",
                        "Department must only contain letters and spaces.");
                return;
            }
            if (!(Pattern.matches("[0-9]+", salaryField.getText()))) {
                showAlert(Alert.AlertType.ERROR, "Invalid Salary",
                        "Please provide a valid salary.");
                return;
            }
            if (!(Pattern.matches("[0-9]+", experienceField.getText()))) {
                showAlert(Alert.AlertType.ERROR, "Invalid Experience",
                        "Please Provide a valid years of experience.");
                return;
            }
            if (!(Pattern.matches("[1-5.]+", ratingField.getText()))) {
                showAlert(Alert.AlertType.ERROR, "Invalid Experience",
                        "Please Provide a rating number.");
                return;
            }
            if (rating <= 0 || rating > 5) {
                showAlert(Alert.AlertType.ERROR, "Invalid Rating",
                        "Rating cannot be less than 0 or greater than 5.");
                return;
            }

            if (!(Pattern.matches("[a-zA-Z ]+", nameField.getText()))) {
                showAlert(Alert.AlertType.ERROR, "Invalid Name",
                        "Name must only contain letters and spaces.");
                return;
            }


            // add employee to Database(Hashmap)
            Employee<Integer> employee = new Employee<>(nbrOfEmployees, name,
                    department, salary, rating, experience, isActive);
            db.addEmployee(nbrOfEmployees, employee);

            employeeList.setAll(db.getAllEmployees()); // to make table auto-refresh

            showAlert(Alert.AlertType.CONFIRMATION, "Success",
                    "✅ Employee added Successfully: " + employee.getName());
            System.out.println("✅ Employee added: " + employee.getName());
            clearForm();

        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error",
                    "⚠️ Error: " + e.getMessage());
            return;
        }
    }

    private void clearForm() {
        nameField.clear();
        departmentComboBox.getSelectionModel().clearSelection();
        salaryField.clear();
        ratingField.clear();
        experienceField.clear();
        isActiveCheckBox.setSelected(false);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("performanceRating"));
        experienceColumn.setCellValueFactory(new PropertyValueFactory<>("yearsOfExperience"));
        activeColumn.setCellValueFactory(new PropertyValueFactory<>("isActive"));

        employeeTable.setItems(employeeList); // refresh table for new record

        // search
        searchField.textProperty().addListener((obs,
                                                oldVal, newVal) -> applySearchByName());
        // filter
        departmentFilterComboBox.setValue("Filter All"); // Default
        departmentFilterComboBox.setOnAction(e -> applyFilterByDepartment());

        // sorting
        sortComboBox.setValue("Sort by");
        sortComboBox.setOnAction(e -> applySortBy());

        //deleting employee
        deleteButton.disableProperty().bind(
                employeeTable.getSelectionModel().selectedItemProperty().isNull()
        );
    }

    public void applySearchByName() {
        String searchText = searchField.getText();
        ObservableList<Employee<Integer>> employeeList = FXCollections
                .observableArrayList(db.filterByName(searchText));

        employeeTable.setItems(employeeList); // update table
    }

    public void applyFilterByDepartment() {
        String departmentFilter = departmentFilterComboBox.getValue();
        if (departmentFilter.equals("Filter All")) {
            ObservableList<Employee<Integer>> employeeList = FXCollections
                    .observableArrayList(db.getAllEmployees());
            employeeTable.setItems(employeeList);
        } else {
            ObservableList<Employee<Integer>> employeeList = FXCollections
                    .observableArrayList(db.filterByDepartment(departmentFilter));
            employeeTable.setItems(employeeList);
        }
    }

    public void applySortBy() {
        String sortOption = sortComboBox.getValue();
        switch (sortOption) {
            case "Sort by":
                ObservableList<Employee<Integer>> employeeList = FXCollections
                        .observableArrayList(db.getAllEmployees());
                employeeTable.setItems(employeeList);
                break;
            case "Salary":
                // sort using comparator
                ArrayList<Employee<Integer>> employees = db.getAllEmployees();
                Collections.sort(employees, new EmployeeSalaryComparator<>());
                ObservableList<Employee<Integer>> employeesList = FXCollections
                        .observableArrayList(employees);
                employeeTable.setItems(employeesList);
                break;
            case "YearsOfExperience":
                // sort using comparable
                ArrayList<Employee<Integer>> list = db.getAllEmployees();
                Collections.sort(list);
                ObservableList<Employee<Integer>> listEmployee = FXCollections
                        .observableArrayList(list);
                employeeTable.setItems(listEmployee);
                break;
            default:
                // sort using comparator
                ArrayList<Employee<Integer>> emploList = db.getAllEmployees();
                Collections.sort(emploList, new EmployeePerformanceComparator<>());
                ObservableList<Employee<Integer>> allEmployees = FXCollections
                        .observableArrayList(emploList);
                employeeTable.setItems(allEmployees);

        }
    }

    @FXML
    private void handleDeleteEmployee() {
        Employee<Integer> selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

        if (selectedEmployee == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an employee to delete.");
            return;
        }

        // Confirm before deleting
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this employee?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            db.removeEmployee(selectedEmployee.employeeId);
            ArrayList<Employee<Integer>> emploList = db.getAllEmployees();
            ObservableList<Employee<Integer>> allEmployees = FXCollections
                    .observableArrayList(emploList);
            employeeTable.setItems(allEmployees);
        }
    }


}