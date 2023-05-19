package trains.service;

import trains.DAO.DatabaseHandler;

import java.time.OffsetDateTime;

public class TimeConverter {

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
    }

    public static Double timeToDouble(OffsetDateTime time){
        return (double)time.getHour()+(time.getMinute()/60);
    }

    public static Double timeToDouble(String time){
        double result=0;
        result += Integer.parseInt(String.valueOf(time.charAt(0)))*10;
        result += Integer.parseInt(String.valueOf(time.charAt(1)));
        result += Double.parseDouble(String.valueOf(time.charAt(3)))/6;
        result += Double.parseDouble(String.valueOf(time.charAt(4)))/60;
        return result;
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
