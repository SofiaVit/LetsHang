package project.letshang;

import android.app.Activity;
import android.content.Context;

import com.facebook.AccessToken;

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

public class UserMeetingsDB {

    static String getUserMeetings(String Email){
        String php_name = "userMeetings.php";
        try {
            String post_data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");
            return connect(php_name, post_data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    static private String connect(String php_name, String post_data){
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
}
