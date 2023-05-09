package trains.model;
import javafx.beans.property.*;

import java.util.Date;

public class Train {
    private int id;
    private SimpleIntegerProperty number;
    private SimpleStringProperty from;
    private SimpleStringProperty to;
    private SimpleStringProperty trainClass;




    public Train(int id,int number, String from, String to, String trainClass) {
        this.id = id;
        this.number = new SimpleIntegerProperty(number);
        this.from = new SimpleStringProperty(from);
        this.to = new SimpleStringProperty(to);
        this.trainClass = new SimpleStringProperty(trainClass);
    }

    public Train() {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", number=" + number +
                ", from=" + from +
                ", to=" + to +
                ", trainClass=" + trainClass +
                '}';
    }
}
