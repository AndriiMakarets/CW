package trains.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import trains.DatabaseHandler;
import trains.model.Train;
import trains.model.TrainTime;
import trains.service.TimeToString;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


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

    DatabaseHandler db = new DatabaseHandler();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set up the columns in the table
        trainNumberColumn.setCellValueFactory(new PropertyValueFactory<TrainTime, Integer>("number"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<TrainTime, String>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<TrainTime, String>("to"));
        trainClassColumn.setCellValueFactory(new PropertyValueFactory<TrainTime, String>("trainClass"));
        departColumn.setCellValueFactory(new PropertyValueFactory<TrainTime, String>("departureTime"));
        arriveColumn.setCellValueFactory(new PropertyValueFactory<TrainTime, String>("arriveTime"));

        //додаємо час відправлення до потягів
        ObservableList<Train> trains = MainController.trains;
        ObservableList<TrainTime> trainTimes  = FXCollections.observableArrayList();
        for (int i=0; i< trains.size(); i++){
            Train currentTrain = trains.get(i);
            System.out.println(currentTrain);
            try {
            String depart = TimeToString.toString( db.getDepartByTrain(currentTrain, MainController.from).getTime());
            String arrive = TimeToString.toString( db.getArriveByTrain(currentTrain, MainController.to).getTime());
                trainTimes.add(new TrainTime( depart, arrive, currentTrain.getId(), currentTrain.numberProperty(),
                        currentTrain.fromProperty(), currentTrain.toProperty(), currentTrain.trainClassProperty()));
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }

        }
        //load  data
        trainTableView.setItems(trainTimes);


       // for (Train train :trains){
           // try {
               //System.out.println( db.departByTrain(train, MainController.from));
           // } catch (SQLException throwable) {
          //      throwable.printStackTrace();
           // }
       // }
    }
}
