package project.letshang;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class signup extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    boolean facebook = false;
    String gender = "Gender";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_signup);
        ((EditText)findViewById(R.id.userBirthdayText)).setInputType(InputType.TYPE_NULL);
        if(bundle != null){
            facebook=true;
            ((EditText)findViewById(R.id.passwordText)).setVisibility(View.GONE);
            ((EditText) findViewById(R.id.emailText)).setText(bundle.getString("EMAIL"));
            ((EditText) findViewById(R.id.userBirthdayText)).setText(bundle.getString("BIRTHDAY"));
        }
        AppCompatSpinner spinner = (AppCompatSpinner) findViewById(R.id.userGenderSpinner);
        spinner.setOnItemSelectedListener(this);
    }

    public void onClickSignUp(View view){
        String userName = ((EditText)findViewById(R.id.userNameText)).getText().toString();
        String birthday = ((EditText)findViewById(R.id.userBirthdayText)).getText().toString();
        String email = ((EditText)findViewById(R.id.emailText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();
        if(facebook==false && (userName.equals("") || email.equals("") || birthday.equals("") || password.equals("") || gender.equals("Gender"))){
            showDialog.showErrorDialog(this,getString(R.string.nullStringError));
        }
        else if(facebook==true && (userName.equals("") || email.equals("") || birthday.equals("") || gender.equals("Gender"))){
            showDialog.showErrorDialog(this,getString(R.string.nullStringError));
        }
        else if (!(inputChecks.checkEmail(email))){
            showDialog.showErrorDialog(this,getString(R.string.emailError));
        }
        else if(facebook == false){
            if(inputChecks.paswwordCheck(password)) {
                showDialog.showErrorDialog(this, getString(R.string.passError));
            }
        }
        else{
            database db = new database(this,this);
            if(facebook==true)
                db.execute("SignUp",email,password,birthday,userName,gender,"facebook");
            else
                db.execute("SignUp",email,password,birthday,userName,gender,"");
        }

    }

    public void onClickPickDate(View view){
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(),"date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateString = df.format(c.getTime());
        ((EditText)findViewById(R.id.userBirthdayText)).setText(currentDateString);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender = (parent.getItemAtPosition(position)).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        if(true){
            finish();
            startActivity(new Intent(this,login.class));
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
