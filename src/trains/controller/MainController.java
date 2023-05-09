package trains.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import trains.model.Train;
import trains.DatabaseHandler;

import javax.swing.*;

public class MainController  {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField arriveField;

    @FXML
    private TextField departTimeField;

    @FXML
    private TextField departeField;

    @FXML
    private TextField priceField;

    @FXML
    private Button recommendButton;

    @FXML
    private TextField trainClassField;

    @FXML
    private TextField travelTimeField;

    @FXML
    private TextField wagonTypeField;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    static ObservableList<Train> trains = FXCollections.observableArrayList();
    static String from;
    static String to;
    static String departTime;
    static String travelTime;
    static String wagonType;
    static Integer price = 0;

    DatabaseHandler db = new DatabaseHandler();

    @FXML
    void initialize() {
       // assert arriveField != null : "fx:id=\"arriveField\" was not injected: check your FXML file 'sample.fxml'.";
        //assert departeField != null : "fx:id=\"departeField\" was not injected: check your FXML file 'sample.fxml'.";
        //assert recommendButton != null : "fx:id=\"recommendButtom\" was not injected: check your FXML file 'sample.fxml'.";

        recommendButton.setOnAction(event ->{
            from = departeField.getText().trim();
            to = arriveField.getText().trim();
            departTime = departeField.getText().trim();
            travelTime = departeField.getText().trim();
            wagonType = departeField.getText().trim();
            price = Integer.getInteger(priceField.getText().trim());
            System.out.println(wagonType);

            if(!from.equals("")&& !to.equals("")){
                try {
                   trains = findTrains(from, to);
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }

            //закриваємо поточне вікно
            recommendButton.getScene().getWindow().hide();


            //відкриваємо вікно, де буде список потягів
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation(getClass().getResource("/trains/result.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            //витягнемо список потягів з бд
            System.out.println();

            }else
            //спливаюче вікно
            alert.setTitle("Заповніть поле");
            alert.setHeaderText("");
            alert.setContentText("Введіть маршрут прямування!");
            alert.show();

        });

    }
        //Повертає список потягів між станціями
        private ObservableList<Train> findTrains(String from, String to) throws SQLException, ClassNotFoundException {

        //витягуємо потяги за вказаними станціями
            trains= db.getTrains(from, to);
       for(Train train : trains){
           System.out.println(train);
       }
       return trains;
    }

}
