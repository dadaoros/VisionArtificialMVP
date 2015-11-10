package com.mugen.visionartificial.Util;

/**
 * Created by root on 21/03/15.
 */
public class Util {

    public static String getDayOfWeek(int day){
        switch (day) {
            case 1: return "Monday";
            case 2:return "Tuesday";
            case 3:return "Wednesday";
            case 4:return "Thursday";
            case 5:return "Friday";
            case 6:return "Saturday";
            default:return "Sunday";
        }
    }
}
