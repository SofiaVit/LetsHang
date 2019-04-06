package project.letshang;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class userAccount extends AppCompatActivity {
    String password;
    String userName;
    String email;
    ArrayList<String> info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        info = userInfoFile.getUserInfo(this);
        if(info.size()==5){
            email = info.get(0);
            ((TextView)findViewById(R.id.userEmail)).setText(email);
            password = info.get(1);
            ((TextView)findViewById(R.id.userBirthday)).setText(info.get(2));
            userName = info.get(3);
            ((TextView)findViewById(R.id.userName)).setText(userName);
            ((TextView)findViewById(R.id.userGender)).setText(info.get(4));
        }
        else{
            email = info.get(0);
            ((TextView)findViewById(R.id.userEmail)).setText(email);
            ((TextView)findViewById(R.id.userBirthday)).setText(info.get(1));
            userName = info.get(2);
            ((TextView)findViewById(R.id.userName)).setText(userName);
            ((TextView)findViewById(R.id.userGender)).setText(info.get(3));
            ((LinearLayout)findViewById(R.id.passwordLayout)).setVisibility(View.GONE);
        }
    }

    public void onClickChangePassword(View view) {
        final Context context = this;
        final Activity activity = this;
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_change_password);
        Button ok_button = (Button)dialog.findViewById(R.id.ok_button);
        ok_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                info = userInfoFile.getUserInfo(context);
                password = info.get(1);
                String newPassword = ((EditText)dialog.findViewById(R.id.newPassword)).getText().toString();
                if(!(password.equals(((EditText)dialog.findViewById(R.id.oldPassword)).getText().toString()))){
                    dialog.dismiss();
                    popUp pop = new popUp(context,context.getResources().getString(R.string.errorTitle),context.getResources().getString(R.string.currentPasswordError));
                }
                else if(!(newPassword.equals(((EditText)dialog.findViewById(R.id.repeatNewPassword)).getText().toString()))){
                    dialog.dismiss(); popUp pop = new popUp(context,context.getResources().getString(R.string.errorTitle),context.getResources().getString(R.string.newPasswordError));
                }
                else{
                    database db = new database(context,activity);
                    db.execute("changePassword",email,newPassword);
                    dialog.dismiss();
                }

            }
        });
        Button cancelButton = (Button)dialog.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
