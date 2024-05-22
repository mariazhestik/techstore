package com.example.techstore.Controllers.Admin.employees;

import com.example.techstore.Models.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewEmployeeController {

    @FXML
    private Label employeeIdLabel;
    @FXML
    private Label nameLabel;

    public void setEmployee(Employee employee) {
        employeeIdLabel.setText(String.valueOf(employee.getEmployeeId()));
        nameLabel.setText(employee.getName());
    }
}
