module com.example.techstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.techstore to javafx.fxml;
    opens com.example.techstore.Controllers to javafx.fxml;
    opens com.example.techstore.Controllers.Admin to javafx.fxml;
    opens com.example.techstore.Controllers.Admin.products to javafx.fxml;
    opens com.example.techstore.Views to javafx.fxml;
    opens com.example.techstore.Controllers.Admin.orders to javafx.fxml;
    opens com.example.techstore.Controllers.Admin.customers to javafx.fxml;

    exports com.example.techstore;
    exports com.example.techstore.Controllers.Admin;
    exports com.example.techstore.Models;
    exports com.example.techstore.Views;
    exports com.example.techstore.Controllers.Admin.customers;
}