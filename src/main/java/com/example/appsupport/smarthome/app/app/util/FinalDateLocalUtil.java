package com.example.appsupport.smarthome.app.app.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class FinalDateLocalUtil {

    private final static String[] strings = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

    private final static DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static String computeDay(LocalDate now, String pushDay) {
        String day = "";

        LocalDate pushDate = LocalDate.parse(pushDay, dt);
        long daysDiff = ChronoUnit.DAYS.between(pushDate, now);
        if (daysDiff == 0)
            day = "今天";
        else if (daysDiff == 1)
            day = "昨天";
        else if (daysDiff > 1 && daysDiff < 7)
            day = strings[pushDate.getDayOfWeek().getValue() - 1];
        else
            day = pushDate.toString();
        return day;
    }
}
