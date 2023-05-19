package trains.model;

import org.jetbrains.annotations.NotNull;

public class TrainRecommend implements Comparable<TrainRecommend>{
    private TrainTime trainTime;
    private Double recommendCoef;

    public TrainRecommend(TrainTime trainTime, Double recommendCoef) {
        this.trainTime = trainTime;
        this.recommendCoef = recommendCoef;
    }

    public TrainRecommend() {
    }

    public TrainTime getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(TrainTime trainTime) {
        this.trainTime = trainTime;
    }

    public Double getRecommendCoef() {
        return recommendCoef;
    }

    public void setRecommendCoef(Double recommendCoef) {
        this.recommendCoef = recommendCoef;
    }

    @Override
    public int compareTo(@NotNull TrainRecommend o) {
        if (this.recommendCoef > o.recommendCoef)
            return 1;
        if (this.recommendCoef.equals(o.recommendCoef))
            return  0;
        return -1;
    }
}
