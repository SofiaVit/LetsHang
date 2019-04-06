package project.letshang;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

public class database extends AsyncTask<String,Void,String> {

    private Context mContext;
    private Activity mActivity;
    private AlertDialog alertDialog;
    private String php_name;
    private String post_data;
    private String query_name;
    private String type;
    String[] params;

    database(Context mContext, Activity mActivity){
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        this.params = params;
        query_name = params[0];
        if (query_name.equals("LogIn")) {
            php_name = "login.php";
            String Email = params[1];
            String Password = params[2];
            try {
                post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8") + "&"
                        + URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(Password, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return connect();
        }
        else if(query_name.equals("SignUp")){
            String Email = params[1];
            String Password = params[2];
            String Birthday = params[3];
            String UserName = params[4];
            String Gender = params[5];
            type = params[6];
            if((checkIfDuplicateEmail(Email)).equals("error")){
                return "DuplicateEmail";
            }
            else if((checkIfDuplicateUser(UserName)).equals("error")){
                return "DuplicateUser";
            }
            else {
                php_name = "signup.php";
                try {
                    post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8") + "&"
                            + URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(Password, "UTF-8") + "&"
                            + URLEncoder.encode("Birthday", "UTF-8") + "=" + URLEncoder.encode(Birthday, "UTF-8") + "&"
                            + URLEncoder.encode("UserName", "UTF-8") + "=" + URLEncoder.encode(UserName, "UTF-8") + "&"
                            + URLEncoder.encode("Gender", "UTF-8") + "=" + URLEncoder.encode(Gender, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if(type.equals("facebook")){
                    String result = connect();
                    if(result.equals("ok")){
                        php_name = "facebookLogin.php";
                        try {
                            post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        query_name = "facebookLogin";
                    }
                }
                return connect();
            }
        }
        else if (query_name.equals("facebookLogin")){
            php_name = "facebookLogin.php";
            String Email = params[1];
            try {
                post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return connect();
        }
        else if (query_name.equals("changePassword")){
            php_name = "changePassword.php";
            String Email = params[1];
            String Password = params[2];
            try {
                post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8") + "&"
                        + URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(Password, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return connect();
        }
        else if(query_name.equals("userMeetings")){
            php_name = "userMeetings.php";
            String Email = params[1];
            try {
                post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return connect();
        }
        else if(query_name.equals("newMeeting")){
            String result="error";
            String MeetingId = "";
            php_name="meetingId.php";
            while (result == "error"){
                Random random = new Random();
                MeetingId = Integer.toString(random.nextInt(2147483646));
                try {
                    post_data = URLEncoder.encode("MeetingId", "UTF-8") + "=" + URLEncoder.encode(MeetingId, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                result = connect();
            }
            php_name = "newMeeting.php";
            try {
                if(params[3].equals(""))
                    params[3] = "None";
                if(params[4].equals(""))
                    params[4] = "None";
                if(params[5].equals(""))
                    params[5] = "None";
                post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&"
                        + URLEncoder.encode("MeetingId", "UTF-8") + "=" + URLEncoder.encode(MeetingId, "UTF-8") + "&"
                        + URLEncoder.encode("Creator", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&"
                        + URLEncoder.encode("AfterAge", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8") + "&"
                        + URLEncoder.encode("BelowAge", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8") + "&"
                        + URLEncoder.encode("Gender", "UTF-8") + "=" + URLEncoder.encode(params[5], "UTF-8") + "&"
                        + URLEncoder.encode("Theme", "UTF-8") + "=" + URLEncoder.encode(params[6], "UTF-8") + "&"
                        + URLEncoder.encode("SubTheme", "UTF-8") + "=" + URLEncoder.encode(params[7], "UTF-8") + "&"
                        + URLEncoder.encode("Place", "UTF-8") + "=" + URLEncoder.encode(params[8], "UTF-8") + "&"
                        + URLEncoder.encode("Time", "UTF-8") + "=" + URLEncoder.encode(params[9], "UTF-8") + "&"
                        + URLEncoder.encode("Date", "UTF-8") + "=" + URLEncoder.encode(params[10], "UTF-8") + "&"
                        + URLEncoder.encode("Description", "UTF-8") + "=" + URLEncoder.encode(params[11], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return connect();
        }
        return null;
    }

    private String connect(){
            try {
                URL url = new URL("http://www.sofivi.a2hosted.com/"+php_name);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                int statusCode = httpURLConnection.getResponseCode();

                InputStream inputStream = null;

                if (statusCode >= 200 && statusCode < 400) {
                    // Create an InputStream in order to extract the response object
                    inputStream = httpURLConnection.getInputStream();
                }
                else {
                    inputStream = httpURLConnection.getErrorStream();
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null)
                    result+=line;
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    private String checkIfDuplicateEmail(String Email){
        php_name = "duplicateEmail.php";
        try {
            post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return connect();
    }

    private String checkIfDuplicateUser(String UserName){
        php_name = "duplicateUser.php";
        try {
            post_data = URLEncoder.encode("UserName", "UTF-8") + "=" + URLEncoder.encode(UserName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return connect();
    }

    @Override
    protected void onPreExecute(){
        alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle(R.string.loginStatus);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        if (query_name.equals("LogIn")){
            if (result.equals("error")){
                alertDialog.setMessage(mContext.getResources().getString(R.string.loginError));
                alertDialog.show();

            }
            else if(result.equals("")){
                alertDialog.setMessage("Connection error");
                alertDialog.show();
            }
            else{
                ArrayList<String> info = new ArrayList<String>();
                String[] temp = result.split("\\|");
                for (int i = 0; i < temp.length; i++) {
                    info.add(temp[i]);
                }
                userInfoFile.saveUserInfo(mContext, info);
                mContext.startActivity(new Intent(mContext,UserMain.class));
                ((Activity)mContext).finish();
            }
        }
        else if(query_name.equals("SignUp")){

            if(result.equals("ok")){ ;
                mContext.startActivity(new Intent(mContext,login.class));
            }
            else if(result.equals("DuplicateUser")){
                popUp pop = new popUp(mContext,"Error","User allready exists");
            }
            else if(result.equals("DuplicateEmail")) {
                popUp pop = new popUp(mContext, "Error", "Email allready exists");
            }
            else{
                popUp pop = new popUp(mContext,"not ok",result);
            }
        }
        else if(query_name.equals("facebookLogin")){
            if(result.equals("error")){
                Intent intent = new Intent(mContext,signup.class);
                intent.putExtra("EMAIL",params[1]);
                intent.putExtra("GENDER",params[2]);
                intent.putExtra("BIRTHDAY",params[3]);
                mContext.startActivity(intent);
            }
            else {
                ArrayList<String> info = new ArrayList<String>();
                String[] temp = result.split("\\|");
                for (int i = 0; i < temp.length; i++) {
                    info.add(temp[i]);
                }
                userInfoFile.saveUserInfo(mContext, info);
                mContext.startActivity(new Intent(mContext,UserMain.class));
            }
        }
        else if(query_name.equals("changePassword")){
            if(result.equals("ok")){
                userInfoFile.changeUserPassword(mContext,params[2]);
                popUp pop = new popUp(mContext,mContext.getResources().getString(R.string.update),mContext.getResources().getString(R.string.passwordChanged));
            }
            else if(result.equals("error")){
                popUp pop = new popUp(mContext,"not ok",result);
            }
        }
        else if(query_name.equals("userMeetings")){
            if(result.equals("error")){
                popUp pop = new popUp((mContext),"not ok", result);
            }
            else if(result.equals("noMeetings")){
               Activity activity = (UserMain)mActivity;
               ((UserMain) activity).getSupportFragmentManager().beginTransaction().add(R.id.mainFragment, new NoMeetingsFragment()).addToBackStack("back").commit();
            }
            else{
                Bundle bundle = new Bundle();
                bundle.putString("MeetingsInfo",result);
                UserMeetingsFragment fragment = new UserMeetingsFragment();
                fragment.setArguments(bundle);
                Activity activity = (UserMain)mActivity;
                ((UserMain) activity).getSupportFragmentManager().beginTransaction().add(R.id.mainFragment, fragment).addToBackStack("back").commit();
            }
        }
        else if(query_name.equals("newMeeting")){
            if(result.equals("ok")){
                popUp pop = new popUp((mContext),"ok","Meeting Added");
            }
            else if(result.equals("error")){
                popUp pop = new popUp((mContext),"not ok", result);
            }
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
