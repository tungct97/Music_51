package com.framgia.music_51.screen;

import java.util.concurrent.TimeUnit;

public class DateTimeUtils {
    private static final String STRING_FORMAT = "%d : %02d";

    public static String convertMinisecond(long milisec) {
        long minute = TimeUnit.MILLISECONDS.toMinutes(milisec);
        long second = TimeUnit.MILLISECONDS.toSeconds(milisec);
        long time = TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milisec));
        return String.format(STRING_FORMAT, minute, second - time);
    }
}
