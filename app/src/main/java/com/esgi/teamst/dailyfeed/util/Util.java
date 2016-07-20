package com.esgi.teamst.dailyfeed.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sylvainvincent on 08/06/16.
 */
public class Util {

    /**
     * Vérifie la conformité de l'email
     * @param email l'adresse mail à vérifier
     * @return vrai si l'email est conforme sinon faux
     */
    public static boolean isEmailAddress(String email){
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = p.matcher(email.toUpperCase());
        return m.matches();
    }

    /**
     * Vérifie si un champs contient seulement des lettres
     * @param text la chaine à verifier
     * @return vrai si c'est conforme sinon faux
     */
    public static boolean isString(String text){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = p.matcher(text);
        return matcher.matches();
    }

    /**
     * Formattage d'un String en Date
     */
    public static Date formatStringToDate (String artDate){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = artDate;
        Date date = null;
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
            System.out.println(formatter.format(date));

        return date;
    }

    /**
     * Comparaison de deux dates
     */
    public static String dateDiff (String artDate){
        //Calendar today = Calendar.getInstance();
        //Calendar articleDate = Calendar.getInstance();
        Date formatArticle = formatStringToDate(artDate);
        //articleDate.setTime(formatArticle);
        /** The date at the end of the last century */
        Date d1 = formatArticle;

        /** Today's date */
        Date today = new Date();

        // Get msec from each, and subtract.
        long diff = today.getTime() - d1.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String dateDiff = "";

        if (diffDays > 0){
            System.out.print(diffDays + " days, ");
            dateDiff = diffDays + "j";
        }
        else if (diffHours > 0) {
            System.out.print(diffHours + " hours, ");
            dateDiff = diffHours + "h";

        }
        else if (diffMinutes > 0) {
            System.out.print(diffMinutes + " minutes");
            dateDiff += " "+diffMinutes + "min";
        }

        Log.d("diffDate" , dateDiff);

        return dateDiff;
    }
}
