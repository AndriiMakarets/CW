package trains.DAO;

import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;
import trains.model.TimeChange;
import trains.model.Train;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDAO {

    Connection getDbConnection() throws ClassNotFoundException, SQLException;
    ObservableList<Train> getTrains(String from, String to) throws SQLException, ClassNotFoundException;
    TimeChange getDepartByTrain(@NotNull Train train, String from) throws SQLException;
    TimeChange getArriveByTrain(Train train, String to) throws SQLException;

}
