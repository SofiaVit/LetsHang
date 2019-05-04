package project.letshang;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;

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

        else if(query_name.equals("allMeetings")){
            php_name = "allMeetings.php";
            post_data="";
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
        else if(query_name.equals("searchMeeting")){
            php_name = "searchMeeting.php";
            String Theme = params[1];
            String Time = params[2];
            String Place = params[3];
            try {
                post_data = URLEncoder.encode("Theme", "UTF-8") + "=" + URLEncoder.encode(Theme, "UTF-8") + "&"
                        + URLEncoder.encode("Time", "UTF-8") + "=" + URLEncoder.encode(Time, "UTF-8") + "&"
                        + URLEncoder.encode("Place", "UTF-8") + "=" + URLEncoder.encode(Place, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return connect();
        }
        else if(query_name.equals("checkIfMeetingFriend")){
            php_name = "checkIfMeetingFriend.php";
            String Email = params[1];
            String MeetingId = params[2];
            try {
                post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8") + "&"
                        + URLEncoder.encode("MeetingId", "UTF-8") + "=" + URLEncoder.encode(MeetingId, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return connect();
        }
        else if(query_name.equals("joinMeeting")){
            php_name = "joinMeeting.php";
            String Email = params[1];
            String MeetingId = params[2];
            try {
                post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8") + "&"
                        + URLEncoder.encode("MeetingId", "UTF-8") + "=" + URLEncoder.encode(MeetingId, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return connect();
        }
        else if(query_name.equals("leaveMeeting")){
            php_name = "leaveMeeting.php";
            String Email = params[1];
            String MeetingId = params[2];
            try {
                post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8") + "&"
                        + URLEncoder.encode("MeetingId", "UTF-8") + "=" + URLEncoder.encode(MeetingId, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return connect();
        }
        else if (query_name.equals("deleteMeeting")){
            php_name = "deleteMeeting.php";
            String Email = params[1];
            String MeetingId = params[2];
            try {
                post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8") + "&"
                        + URLEncoder.encode("MeetingId", "UTF-8") + "=" + URLEncoder.encode(MeetingId, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return connect();
        }
        else if(query_name.equals("meetingFriends")){
            php_name = "meetingFriends.php";
            String MeetingId = params[1];
            try {
                post_data = URLEncoder.encode("MeetingId", "UTF-8") + "=" + URLEncoder.encode(MeetingId, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return connect();
        }
        else if(query_name.equals("myMessages")){
            php_name = "myMessages.php";
            String Email = params[1];
            try {
                post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return connect();
        }
        else if(query_name.equals("invateFriend") || query_name.equals("sendMessage")){
            String result="error";
            String MessageId = "";
            php_name="messageId.php";
            while (result == "error"){
                Random random = new Random();
                MessageId = Integer.toString(random.nextInt(2147483646));
                try {
                    post_data = URLEncoder.encode("MessageId", "UTF-8") + "=" + URLEncoder.encode(MessageId, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                result = connect();
            }
            if(query_name.equals("invateFriend"))
                php_name = "invateFriend.php";
            else
                php_name = "sendMessage.php";
            try {
                post_data = URLEncoder.encode("MessageId", "UTF-8") + "=" + URLEncoder.encode(MessageId, "UTF-8") + "&"
                        + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&"
                        + URLEncoder.encode("Subject", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&"
                        + URLEncoder.encode("Date", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8") + "&"
                        + URLEncoder.encode("Author", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8") + "&"
                        + URLEncoder.encode("IsRead", "UTF-8") + "=" + URLEncoder.encode(params[5], "UTF-8") + "&"
                        + URLEncoder.encode("Body", "UTF-8") + "=" + URLEncoder.encode(params[6], "UTF-8") + "&"
                        + URLEncoder.encode("Type", "UTF-8") + "=" + URLEncoder.encode(params[7], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return connect();
        }
        else if(query_name.equals("findEmail")) {
            php_name = "findEmail.php";
            try {
                post_data = URLEncoder.encode("UserName", "UTF-8") + "=" + URLEncoder.encode(params[8], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String result = connect();
            if(result.equals("error")){
                return result;
            }
            else{
                params[1] = result;
                result="error";
                String MessageId = "";
                php_name="messageId.php";
                while (result == "error"){
                    Random random = new Random();
                    MessageId = Integer.toString(random.nextInt(2147483646));
                    try {
                        post_data = URLEncoder.encode("MessageId", "UTF-8") + "=" + URLEncoder.encode(MessageId, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    result = connect();
                }
                query_name = "invateFriend";
                php_name = "invateFriend.php";
                try {
                    post_data = URLEncoder.encode("MessageId", "UTF-8") + "=" + URLEncoder.encode(MessageId, "UTF-8") + "&"
                            + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&"
                            + URLEncoder.encode("Subject", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&"
                            + URLEncoder.encode("Date", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8") + "&"
                            + URLEncoder.encode("Author", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8") + "&"
                            + URLEncoder.encode("IsRead", "UTF-8") + "=" + URLEncoder.encode(params[5], "UTF-8") + "&"
                            + URLEncoder.encode("Body", "UTF-8") + "=" + URLEncoder.encode(params[6], "UTF-8") + "&"
                            + URLEncoder.encode("Type", "UTF-8") + "=" + URLEncoder.encode(params[7], "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return connect();
            }
        }
        else if(query_name.equals("addFriend")){
            php_name = "addFriend.php";
            try {
                post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&"
                        + URLEncoder.encode("UserName", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&"
                        + URLEncoder.encode("UserName2", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return connect();
        }
        else if(query_name.equals("deleteMessage")){
            php_name = "deleteMessage.php";
            try {
                post_data = URLEncoder.encode("MessageId", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return connect();
        }
        else if(query_name.equals("userFriends")){
            php_name = "userFriends.php";
            try {
                post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return connect();
        }
        else if (query_name.equals("deleteFriend")){
            php_name = "deleteFriend.php";
            try {
                post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&"
                        + URLEncoder.encode("UserName", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&"
                        + URLEncoder.encode("UserName2", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8");
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
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
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
        else if(query_name.equals("meetingFriends")){
            Intent intent = new Intent(mContext,meetingFriends.class);
            intent.putExtra("friends",result);
            mContext.startActivity(intent);
        }
        else if(query_name.equals("userFriends")){
            Intent intent = new Intent(mContext,UserFriends.class);
            intent.putExtra("friends",result);
            mContext.startActivity(intent);
        }
        else if(query_name.equals("allMeetings")){
            Bundle bundle = new Bundle();
            bundle.putString("MeetingsInfo",result);
            UserMeetingsFragment fragment = new UserMeetingsFragment();
            fragment.setArguments(bundle);
            Activity activity = (searchMeeting)mActivity;
            ((searchMeeting)activity).getSupportFragmentManager().beginTransaction().add(R.id.meetingList, fragment).addToBackStack("back").commit();
        }
        else if (query_name.equals("searchMeeting")){
            if(result.equals("noMeetings")){
                popUp pop = new popUp(mContext,"Message","No meetings found");
            }
            else {
                Bundle bundle = new Bundle();
                bundle.putString("MeetingsInfo", result);
                UserMeetingsFragment fragment = new UserMeetingsFragment();
                fragment.setArguments(bundle);
                Activity activity = (searchMeeting) mActivity;
                ((searchMeeting) activity).getSupportFragmentManager().beginTransaction().replace(R.id.meetingList, fragment).commit();
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
        else if(query_name.equals("checkIfMeetingFriend")){
            if(result.equals("yes")){
                Bundle bundle = new Bundle();
                bundle.putString("Creator", params[3]);
                bundle.putString("afterAge", params[4]);
                bundle.putString("belowAge", params[5]);
                bundle.putString("gender", params[6]);
                bundle.putString("meetingId",params[7]);
                freindMeetingFragment fragment = new freindMeetingFragment();
                fragment.setArguments(bundle);
                Activity activity = (meetingInfo) mActivity;
                ((meetingInfo) activity).getSupportFragmentManager().beginTransaction().add(R.id.buttonsFrame, fragment).addToBackStack("back").commit();
            }
            else if(result.equals("no")){
                Bundle bundle = new Bundle();
                bundle.putString("afterAge", params[4]);
                bundle.putString("belowAge", params[5]);
                bundle.putString("gender", params[6]);
                bundle.putString("meetingId",params[7]);
                notFriendFragment fragment = new notFriendFragment();
                fragment.setArguments(bundle);
                Activity activity = (meetingInfo) mActivity;
                ((meetingInfo) activity).getSupportFragmentManager().beginTransaction().add(R.id.buttonsFrame, fragment).addToBackStack("back").commit();
            }
        }

        else if (query_name.equals("joinMeeting")){
            if(result.equals("ok")){
                Bundle bundle = new Bundle();
                bundle.putString("Creator", "false");
                bundle.putString("afterAge", params[3]);
                bundle.putString("belowAge", params[4]);
                bundle.putString("gender", params[5]);
                bundle.putString("meetingId",params[2]);
                freindMeetingFragment fragment = new freindMeetingFragment();
                fragment.setArguments(bundle);
                Activity activity = (meetingInfo) mActivity;
                ((meetingInfo) activity).getSupportFragmentManager().beginTransaction().replace(R.id.buttonsFrame, fragment).commit();
            }
        }
        else if (query_name.equals("leaveMeeting")){
            if(result.equals("ok")){
                Bundle bundle = new Bundle();
                bundle.putString("afterAge", params[3]);
                bundle.putString("belowAge", params[4]);
                bundle.putString("gender", params[5]);
                bundle.putString("meetingId",params[2]);
                notFriendFragment fragment = new notFriendFragment();
                fragment.setArguments(bundle);
                Activity activity = (meetingInfo) mActivity;
                ((meetingInfo) activity).getSupportFragmentManager().beginTransaction().replace(R.id.buttonsFrame, fragment).commit();
            }
        }
        else if (query_name.equals("deleteMeeting")){
            if(result.equals("ok")){
                mContext.startActivity(new Intent(mContext,UserMain.class));
                ((Activity)mContext).finish();
            }
        }
        else if(query_name.equals("myMessages")){
            if(result.equals("error")){
                popUp pop = new popUp(mContext,"Error","error");
            }
            else{
                Intent intent = new Intent(mContext,MessageBox.class);
                intent.putExtra("messages",result);
                mContext.startActivity(intent);
            }
        }
        else if(query_name.equals("invateFriend")){
            String header = mContext.getResources().getString(R.string.message);
            if(result.equals("Friends")){
                String body = mContext.getResources().getString(R.string.allreadyFriends);
                popUp pop = new popUp(mContext,header,body);
            }
            else if (result.equals("ok")){
                String body = mContext.getResources().getString(R.string.friendSend);
                popUp pop = new popUp(mContext,header,body);
            }
            else if (result.equals("error1")){
                String body = mContext.getResources().getString(R.string.InvitationSent);
                popUp pop = new popUp(mContext,header,body);
            }
            else{
                popUp pop = new popUp(mContext,"error",result);
            }
        }
        else if(query_name.equals("addFriend")){
            if(result.equals("ok")){
                popUp pop = new popUp(mActivity,"friendAdded",result);
                database db = new database(mContext,mActivity);
                db.execute("myMessages",params[1]);
                mActivity.finish();


            }
            else {
                popUp pop = new popUp(mActivity, "error", result);
            }
        }
        else if(query_name.equals("deleteMessage")){
            if(result.equals("ok")){
                popUp pop = new popUp(mActivity,"MessageDeleted",result);
                database db = new database(mContext,mActivity);
                db.execute("myMessages",params[2]);
                mActivity.finish();
            }
            else {
                popUp pop = new popUp(mActivity, "error", result);
            }
        }
        else if(query_name.equals("deleteFriend")){
            if(result.equals("ok")){
                database db = new database(mContext,mActivity);
                db.execute("userFriends",params[1]);
                mActivity.finish();
            }
        }
        else if(query_name.equals("sendMessage")){
            if(result.equals("ok")){
                popUp pop = new popUp(mContext,mContext.getResources().getString(R.string.message),"message sent");
            }
            else {
                popUp pop = new popUp(mContext, mContext.getResources().getString(R.string.message), "not Sent");
            }
        }
        else if(query_name.equals("findEmail")){
            showDialog.showErrorDialog(mContext,mContext.getResources().getString(R.string.userNameNotExist));
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
