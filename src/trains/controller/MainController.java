package trains.controller;

import java.io.IOException;
import java.net.URL;
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
import trains.DAO.IDAO;
import trains.model.Train;
import trains.DAO.DatabaseHandler;

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
    private TextField departField;

    @FXML
    private TextField priceField;

    @FXML
    private Button recommendButton;

    @FXML
    private TextField trainTypeField;

    @FXML
    private TextField travelTimeField;

    @FXML
    private TextField wagonClassField;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    static ObservableList<Train> trains = FXCollections.observableArrayList();
    static String from;
    static String to;
    static  String departTime;
    static String travelTime;
    static String wagonClass;
    static String trainType;
    static  Integer price;
    static boolean isRecommend= true;


    IDAO db = new DatabaseHandler();

    @FXML
    void initialize() {

        recommendButton.setOnAction(event ->{

            from = departField.getText().trim();
            to = arriveField.getText().trim();
            departTime = departTimeField.getText().trim();
            //System.out.println(departTime);
            travelTime = travelTimeField.getText().trim();
          //  System.out.println(travelTime);
            wagonClass = wagonClassField.getText().trim();
            //System.out.println(wagonClass);
            trainType = trainTypeField.getText().trim();
            //System.out.println(trainType);
            price = Integer.getInteger(priceField.getText().trim());
          //  System.out.println(price);
            if(departTime.equals("") && travelTime.equals("") && wagonClass.equals("") && trainType.equals("") &&price==null){
                isRecommend = false;
                System.out.println("1");
                try {
                    trains = db.getTrains(from, to);
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
            else {
                if (!from.equals("") && !to.equals("")) {
                    try {
                        isRecommend = true;
                        System.out.println("2");
                        trains = findTrains(from, to);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                }
             else{
                        //спливаюче вікно
                        alert.setTitle("Заповніть поле");
                        alert.setHeaderText("");
                        alert.setContentText("Введіть маршрут прямування!");
                        alert.show();
                    }
                }

                    //закриваємо поточне вікно
                    recommendButton.getScene().getWindow().hide();

                    //відкриваємо вікно, де буде список потягів
                    FXMLLoader loader = new FXMLLoader();
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
