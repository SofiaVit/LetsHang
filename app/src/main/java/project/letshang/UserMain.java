package project.letshang;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        String email = userInfoFile.getUserEmail(this);
        database db = new database(this,this);
        db.execute("userMeetings",email);
    }

    @Override
    public void onBackPressed() {
        if(true){}
        else
            super.onBackPressed();
    }


}
