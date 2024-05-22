package com.example.techstore;

import com.example.techstore.Controllers.Admin.DashboardController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/DashboardView.fxml"));
        Parent root = loader.load();

        // Получение контроллера Dashboard и вызов метода для загрузки продуктов
        DashboardController dashboardController = loader.getController();
        dashboardController.loadProductsView();

        primaryStage.setTitle("TechStore Admin");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true); // Открываем на весь экран
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
