package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TokenGenerator {

    public static String generateToken(String login) {
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        new TokenDAO().updateTokenInDB(login, token);
        return token;
    }

    public static boolean checkToken(String token) {
        String t = new TokenDAO().getTokenFromDB(token);
        if (t == null) {
            return false;
        }
        return true;
    }

    public static boolean checkTokenTime(String token) throws ParseException {
        String tokenTime = new TokenDAO().getTokenTimeFromDB(token);
        if (tokenTime == null) {
            return false;
        }
        String s = "2017-06-09 16:00";
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((String) tokenTime);
        Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((String) getNow());
        long diff = Math.abs(d1.getTime() - d2.getTime());
        long minDiff = diff / (60 * 1000);
        if (minDiff >= 91) {
            return false;
        }
        return true;
    }

    private static String getNow() {
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(d);
    }
}
