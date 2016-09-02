package com.vanity.mobilevanity.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Tacademy on 2016-09-02.
 */
public class DateCalculator {
    private final static String dateFormat = "yyyy-MM-dd'T'kk:mm:ss.SSSZ";
    private SimpleDateFormat form;

    public DateCalculator() {
        form = new SimpleDateFormat(dateFormat);
    }

    public String calculateUseby(String regDate, int term) {
        Calendar addedDate = StrToCal(regDate);
        addedDate.add(Calendar.DATE, term);
        return CalToStr(addedDate);
    }

    public int calculateDDay(String regDate, int term) {
        String usebyDate = calculateUseby(regDate, term);
        return getGap(usebyDate);
    }

    public boolean isNew(String regDate) {
        return getGap(regDate) <= 3;
    }

    public Calendar StrToCal(String dateStr) {
        Calendar result = Calendar.getInstance();

        try {
            result.setTime(form.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String CalToStr(Calendar cal) {
        return form.format(cal.getTime());
    }

    public int getGap(String date) {
        long now = Calendar.getInstance().getTimeInMillis();
        long standard = StrToCal(date).getTimeInMillis();
        int result = (int)((now - standard) / (1000 * 60 * 60 * 24));

        if (result < 0)
            return result * (-1);

        return result;
    }
}
