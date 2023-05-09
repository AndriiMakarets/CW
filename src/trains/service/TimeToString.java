package trains.service;

import trains.DatabaseHandler;

import java.time.OffsetDateTime;

public class TimeToString {

    DatabaseHandler db = new DatabaseHandler();

    //OffsetDateTime -> String
    public static String toString(OffsetDateTime time){
        String result = "";
        if (time.getHour()<10) result +="0"+time.getHour() +":";
        else result+= time.getHour() +":";
        if (time.getMinute()<10) result +="0"+time.getMinute();
        else result+= time.getMinute();
        return result;
    }


    public static String  timeDiff(OffsetDateTime depart, OffsetDateTime arrive){
       // if(! isDayChangedD){
            if(arrive.getMinute()>=depart.getMinute())
            return numberTimeToString(arrive.getHour() - depart.getHour(), arrive.getMinute()-depart.getMinute());
        return numberTimeToString(arrive.getHour() - depart.getHour()-1, 60+arrive.getMinute()-depart.getMinute());
        //} else{
          //  if(arrive.getMinute()>=depart.getMinute())
          //  return numberTimeToString(24-depart.getHour() + arrive.getHour(), depart.getMinute()-arrive.getMinute());
       // return  numberTimeToString(24-depart.getHour() + arrive.getHour()-1, 60+depart.getMinute()-arrive.getMinute());
        //}
    }


    private static String numberTimeToString(Integer hour, Integer minute){
        String result = "";
        if (hour <10) result +="0"+hour +":";
        else result+= hour +":";
        if (minute<10) result +="0"+minute;
        else result+= minute;
        return result;
    }

}
