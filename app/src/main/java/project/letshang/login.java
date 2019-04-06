package project.letshang;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import java.util.Locale;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_login);
        if(userInfoFile.userConnected(this) == true){
            startActivity(new Intent(this,UserMain.class));
        }

    }

    public void onClickLogin(View view){
        String userEmail = ((EditText)findViewById(R.id.email)).getText().toString();
        String userPassword = ((EditText)findViewById(R.id.password)).getText().toString();
        database user = new database(this,this);
        user.execute("LogIn",userEmail,userPassword);
    }

    public void onClickChangeLanguage(View view){
        showChangeLanguageDialog();
    }

    public void onClickSignUp(View view){
        startActivity(new Intent(this,signup.class));
    }

    private void showChangeLanguageDialog(){
        final String[] languagesList = {"English","עברית","русский"};
        AlertDialog.Builder chooseLanguage = new AlertDialog.Builder(login.this);
        chooseLanguage.setTitle("Choose language");
        chooseLanguage.setSingleChoiceItems(languagesList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0)
                    setLanguage("en",1);
                else if (which == 1)
                    setLanguage("he",1);
                else if (which == 2)
                    setLanguage("ru",1);
                dialog.dismiss();
            }
        });
        AlertDialog mDialog = chooseLanguage.create();
        mDialog.show();
    }

    private void setLanguage(String language,int num){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",language);
        editor.apply();
        if (num==1){
            finish();
            startActivity(getIntent());
        }
    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLanguage(language,0);
    }

    @Override
    public void onBackPressed() {
        if(true){}
        else
            super.onBackPressed();
    }
}
