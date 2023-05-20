package trains.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import trains.DAO.DatabaseHandler;
import trains.DAO.IDAO;
import trains.model.TimeChange;
import trains.model.Train;
import trains.model.TrainTime;
import trains.service.Recommender;
import trains.service.TimeConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;


public class ResultController implements Initializable {

    //configure the table
    @FXML
    private TableView<TrainTime> trainTableView;
    @FXML
    private TableColumn<TrainTime, Integer> trainNumberColumn;
    @FXML
    private TableColumn<TrainTime, String> fromColumn;
    @FXML
    private TableColumn<TrainTime, String> toColumn;
    @FXML
    private TableColumn<TrainTime, String> trainClassColumn;
    @FXML
    private TableColumn<TrainTime, String> departColumn;
    @FXML
    private TableColumn<TrainTime, String> arriveColumn;
    @FXML
    private TableColumn<TrainTime, String> durationColumn;
    @FXML
    private TableColumn<TrainTime, Integer> priceColumn;
    @FXML
    private TableColumn<TrainTime, String> wagonColumn;


    @FXML
    private Button buttonBack;

    IDAO db = new DatabaseHandler();
    Recommender recommender = new Recommender();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set up the columns in the table
        trainNumberColumn.setCellValueFactory(new PropertyValueFactory<TrainTime, Integer>("number"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<TrainTime, String>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<TrainTime, String>("to"));
        trainClassColumn.setCellValueFactory(new PropertyValueFactory<TrainTime, String>("trainClass"));
        departColumn.setCellValueFactory(new PropertyValueFactory<TrainTime, String>("departureTime"));
        arriveColumn.setCellValueFactory(new PropertyValueFactory<TrainTime, String>("arriveTime"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<TrainTime, String>("duration"));

        //додаємо час відправлення до потягів
        ObservableList<Train> trains = MainController.trains;
        ObservableList<TrainTime> trainTimes  = FXCollections.observableArrayList();
        for (Train currentTrain : trains) {
            System.out.println(currentTrain);
            try {
                //час+чи була зміна доби
                TimeChange d = db.getDepartByTrain(currentTrain, MainController.from);
                TimeChange a = db.getArriveByTrain(currentTrain, MainController.to);
                //час відправлення/прибуття
                OffsetDateTime depart = d.getTime();
                OffsetDateTime arrive = a.getTime();
                String duration = TimeConverter.timeDiff(depart, arrive);
                trainTimes.add(new TrainTime(TimeConverter.toString(depart), TimeConverter.toString(arrive), duration,
                        currentTrain.getId(), currentTrain.numberProperty(), currentTrain.fromProperty(),
                        currentTrain.toProperty(), currentTrain.trainClassProperty()));
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }

        }
        //load  data
        if(MainController.isRecommend) {
            System.out.println("4");
            trainTableView.setItems(recommender.recommend(MainController.trainType, TimeConverter.timeToDouble(MainController.departTime),
                    TimeConverter.timeToDouble(MainController.travelTime), trainTimes));
        }else{
            System.out.println("3");
            trainTableView.setItems(trainTimes);
        }

    buttonBack.setOnAction(event ->{
        //закриваємо поточне вікно
        buttonBack.getScene().getWindow().hide();

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
}
