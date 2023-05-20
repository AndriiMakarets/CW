package trains.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import trains.model.TrainRecommend;
import trains.model.TrainTime;

import java.util.*;

public class Recommender {

    private static final Map<String, Double> trainType;
    static{
        trainType = new HashMap<>();
        trainType.put("ІС+", 1.0);
        trainType.put("НЕ", 1.0);
        trainType.put("ІС", 2.0);
        trainType.put("РЕ", 3.0);
        trainType.put("НШ", 3.0);
        trainType.put("Р", 4.0);
        trainType.put("П", 5.0);
    }

    private final double TIMECOEF = 0.5;
    private final int COUNT_RECOMMEND = 5;

    //v trainType, departTime, travel time
    private double euclidVectorCount(ArrayList<Double> request, ArrayList<Double>propose){
        double sum =0;

        for(int i = 0; i < request.size(); i++){
            if ((i==0))
            sum+= Math.pow((request.get(i)-propose.get(i)) ,2);
            if ((i==2))
                sum+= Math.pow((request.get(i)-propose.get(i))*(TIMECOEF*0.1) ,2);
            else
            sum+= Math.pow((request.get(i)-propose.get(i))*TIMECOEF ,2);
        }
        return Math.pow(sum, 0.5);
    }

    //public int priceCount(TrainTime train, int distance);





    //main recommendation method
    public ObservableList<TrainTime>  recommend (String trainType, Double departTime, Double travelTime, ObservableList<TrainTime> trainTimes){
        ArrayList<Double> userRequest = new ArrayList<>();
        System.out.println(Recommender.trainType.get(trainType));
        userRequest.add(Recommender.trainType.get(trainType));
        System.out.println(departTime);
        userRequest.add(departTime);
        System.out.println(travelTime);
        userRequest.add(travelTime);
        ObservableList<TrainTime> result = FXCollections.observableArrayList();;
      //  Map<Double, TrainTime> trainWithCoef = new TreeMap<>();
        ArrayList <TrainRecommend> trainsWithCoef= new ArrayList<>();
        for (TrainTime train: trainTimes) {
            ArrayList<Double> trainParams = new ArrayList<>();
            System.out.println(Recommender.trainType.get(train.getTrainClass()));
            trainParams.add(Recommender.trainType.get(train.getTrainClass()));
            System.out.println(TimeConverter.timeToDouble(train.getDepartureTime()));
            trainParams.add(TimeConverter.timeToDouble(train.getDepartureTime()));
            System.out.println(TimeConverter.timeToDouble(train.getDuration()));
            trainParams.add(TimeConverter.timeToDouble(train.getDuration()));
            trainsWithCoef.add(new TrainRecommend(train, euclidVectorCount(userRequest, trainParams)));
        }
        Collections.sort(trainsWithCoef);
        int i =0;
        while (i<COUNT_RECOMMEND&& i< trainsWithCoef.size()){
            result.add(trainsWithCoef.get(i).getTrainTime());
            i++;
        }
        return result;
    }
}
