package trains.model;

import java.time.OffsetDateTime;

public class TimeChange {
    private OffsetDateTime time;
    private  boolean is_day_changed;

    public TimeChange(OffsetDateTime time, boolean is_day_changed) {
        this.time = time;
        this.is_day_changed = is_day_changed;
    }

    public TimeChange() {
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public void setTime(OffsetDateTime time) {
        this.time = time;
    }

    public boolean isIs_day_changed() {
        return is_day_changed;
    }

    public void setIs_day_changed(boolean is_day_changed) {
        this.is_day_changed = is_day_changed;
    }
}
