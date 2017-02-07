package com.example.elisara.mymind;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ellu on 1.2.2017.
 */

public class DateConverter {

    public String convertStringToDate(String dateString) throws ParseException {
        Locale finnish = new Locale("en", "FI");
        DateFormat originalFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", finnish);
        DateFormat targetFormat = new SimpleDateFormat("MMM d, yyyy");
        Date date = originalFormat.parse(dateString);
        String formattedDate = targetFormat.format(date);  // 20120821
        return formattedDate;
    }

    public String convertStringToDate2(String dateString) throws ParseException {
        Locale finnish = new Locale("en", "FI");
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", finnish);
        DateFormat targetFormat = new SimpleDateFormat("MMM d, yyyy");
        Date date = originalFormat.parse(dateString);
        String formattedDate = targetFormat.format(date);  // 20120821
        return formattedDate;
    }

}
