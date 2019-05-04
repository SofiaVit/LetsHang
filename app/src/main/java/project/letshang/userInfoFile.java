package project.letshang;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class userInfoFile {


    public static void saveUserInfo(Context mContext, ArrayList<String> info){
        File file = new File(mContext.getFilesDir(),"userInfoFile.txt");
        if(!(file.exists())) {
            try {
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                int i = 0;
                for (String item : info) {
                    bufferedWriter.write(item);
                    if((info.size())-1 != info.indexOf(item)) {
                        bufferedWriter.newLine();
                    }
                }
                bufferedWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static ArrayList<String> getUserInfo(Context mContext){
        ArrayList<String> info = new ArrayList<String>();
        try{
            FileInputStream fileInputStream = mContext.openFileInput("userInfoFile.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null)
                info.add(line);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return info;
    }

    public static String getUserEmail(Context mContext){
        ArrayList <String> info = getUserInfo(mContext);
        return info.get(0);
    }

    public static String getUserGender(Context mContext){
        ArrayList <String> info = getUserInfo(mContext);
        if(info.size() == 5)
            return info.get(4);
        return info.get(3);
    }

    public static String getUserBirthday(Context mContext){
        ArrayList <String> info = getUserInfo(mContext);
        if(info.size() == 5)
            return info.get(2);
        return info.get(1);
    }

    public static String getUserName(Context mContext){
        ArrayList <String> info = getUserInfo(mContext);
        if(info.size() == 5)
            return info.get(3);
        return info.get(2);
    }

    public static void changeUserPassword(Context mContext,String newPassword){
        ArrayList<String> info = getUserInfo(mContext);
        info.set(1,newPassword);
        deleteFile(mContext);
        saveUserInfo(mContext,info);
    }

    public static void deleteFile(Context mContext){
        File file = new File(mContext.getFilesDir(),"userInfoFile.txt");
        if(file.exists()){
            file.delete();
        }
    }

    public static boolean userConnected(Context mContext){
        File file = new File(mContext.getFilesDir(),"userInfoFile.txt");
        if (file.exists())
            return true;
        else
            return false;
    }

}
