package project.letshang;

import java.util.regex.Pattern;

public class inputChecks {
    public static boolean checkEmail(String email){
       Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
       if((pattern.matcher(email)).matches())
           return true;
       else
           return false;
    }

    public static boolean ilegalChar(String text){
        if(text.contains("%")){
            return true;
        }
        else
            return false;
    }

    public static boolean paswwordCheck(String pass){
        if(pass.length() < 8 || pass.isEmpty())
            return true;
        else
            return false;
    }

}
