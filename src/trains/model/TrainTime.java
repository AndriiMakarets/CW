package trains.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.OffsetDateTime;
import java.util.Date;

public class TrainTime {
    private String departureTime;
    private String arriveTime;
    private String duration;

    private int id;
    private SimpleIntegerProperty number;
    private SimpleStringProperty from;
    private SimpleStringProperty to;
    private SimpleStringProperty trainClass;

    public TrainTime(String departureTime, String arriveTime, String duration, int id, SimpleIntegerProperty number,
                     SimpleStringProperty from, SimpleStringProperty to, SimpleStringProperty trainClass) {
        this.departureTime = departureTime;
        this.arriveTime = arriveTime;
        this.duration = duration;
        this.id = id;
        this.number = number;
        this.from = from;
        this.to = to;
        this.trainClass = trainClass;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number.get();
    }

    public SimpleIntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public String getFrom() {
        return from.get();
    }

    public SimpleStringProperty fromProperty() {
        return from;
    }

    public void setFrom(String from) {
        this.from.set(from);
    }

    public String getTo() {
        return to.get();
    }

    public SimpleStringProperty toProperty() {
        return to;
    }

    public void setTo(String to) {
        this.to.set(to);
    }

    public String getTrainClass() {
        return trainClass.get();
    }

    public SimpleStringProperty trainClassProperty() {
        return trainClass;
    }

    public void setTrainClass(String trainClass) {
        this.trainClass.set(trainClass);
    }


    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
