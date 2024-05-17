module com.example.techstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.techstore to javafx.fxml;
    exports com.example.techstore;
    exports com.example.techstore.Controllers.Admin;
    exports com.example.techstore.Models;
    exports com.example.techstore.Views;
}