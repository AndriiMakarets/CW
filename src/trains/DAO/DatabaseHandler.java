package trains.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;
import trains.Configs;
import trains.Const;
import trains.model.TimeChange;
import trains.model.Train;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;


public class DatabaseHandler extends Configs implements IDAO {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }


    //Дістає всі потяги за напрямком
    public ObservableList<Train> getTrains(String from, String to) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM " + Const.TRAIN_TABLE + " WHERE id_train IN ( SELECT id_train FROM " +
                Const.TIMETABLE_TABLE + " WHERE id_station = (SELECT id_station FROM " +
                Const.STATION_TABLE + " WHERE st_name =  ? ))AND id_train IN ( " +
                "SELECT id_train " +
                "FROM " + Const.TIMETABLE_TABLE +
                " WHERE id_station = (" +
                "SELECT id_station " +
                "FROM " + Const.STATION_TABLE +
                " WHERE st_name = ?));";

        ResultSet resultSet = null;
        ObservableList<Train> trains = FXCollections.observableArrayList();

        try {
            PreparedStatement statement = getDbConnection().prepareStatement(sql);
            statement.setString(1, from);
            statement.setString(2, to);
            resultSet = statement.executeQuery();


            while (resultSet.next()) {
                trains.add(new Train(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5)));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return trains;
    }


    //Для потягу і напрямку витягує час відправлення
    public TimeChange getDepartByTrain(@NotNull Train train, String from) throws SQLException {
        String sql = "SELECT depart, change_of_day " +
                "FROM timetable JOIN station USING(id_station) " +
                "WHERE st_name = ? AND id_train = ?";

        ResultSet resultSet = null;
        TimeChange result = new TimeChange();

        try {
            PreparedStatement statement = getDbConnection().prepareStatement(sql);
            statement.setString(1, from);
            statement.setInt(2, train.getId());
            resultSet = statement.executeQuery();
            resultSet.next();

            OffsetDateTime time = resultSet.getObject(1, OffsetDateTime.class);
            System.out.println(time);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert resultSet != null;
        result.setTime(resultSet.getObject(1, OffsetDateTime.class));
        result.setIs_day_changed(resultSet.getBoolean(2));
        return result;

    }

    //Для потягу і напрямку витягує час прибуття
    public TimeChange getArriveByTrain(Train train, String to) throws SQLException {
        String sql = "SELECT arrive, change_of_day " +
                "FROM timetable JOIN station USING(id_station)" +
                "WHERE st_name = ? AND id_train = ?";

        ResultSet resultSet = null;
        TimeChange result = new TimeChange();

        try {
            PreparedStatement statement = getDbConnection().prepareStatement(sql);
            statement.setString(1, to);
            statement.setInt(2, train.getId());
            resultSet = statement.executeQuery();
            resultSet.next();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert resultSet != null;
        result.setTime(resultSet.getObject(1, OffsetDateTime.class));
        result.setIs_day_changed(resultSet.getBoolean(2));
        return result;

    }

    //витягуємо всі вагони потяга
    public ArrayList<String> getWagon(Train train) throws SQLException {
        String sql = "SELECT class_name " +
                "FROM wagon_class " +
                "WHERE id_class IN (SELECT id_class " +
                "FROM train_class " +
                "WHERE id_train = ?)";
        ResultSet resultSet = null;
        ArrayList<String> result = new ArrayList<>();
        try {
            PreparedStatement statement = getDbConnection().prepareStatement(sql);
            statement.setInt(1, train.getId());
            resultSet = statement.executeQuery();
            // resultSet.next();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            assert resultSet != null;
            if (!resultSet.next()) break;
            result.add(resultSet.getString(1));
        }
        return result;
    }

    //витягуємо відстань за напрямком
    public int getDistance(String from, String to) throws SQLException {
        String sql = "SELECT distance_to " +
                "FROM distance " +
                "WHERE name_station_to = ? AND id_station_from  =(SELECT id_station "+
                        "FROM station "+
                        "WHERE st_name = ?)";
        ResultSet resultSet = null;
        //int result;
        try {
            PreparedStatement statement = getDbConnection().prepareStatement(sql);
            statement.setString(1, to);
            statement.setString(2, from);
            resultSet = statement.executeQuery();
             resultSet.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        assert resultSet != null;
        return resultSet.getInt(1);
    }

}