package project.letshang;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;


public class newMeeting extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    String placeSelected;
    String themeSelected;
    String gender;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);
        Spinner spinner = (Spinner)findViewById(R.id.themeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.themesChoose,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        final Context context = this;
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyAyT-mNFecACyZjyCHNCsW_4_B6aFUmVcw");
        }
        AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment)getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                placeSelected = place.getName();
            }

            @Override
            public void onError(@NonNull Status status) {
                popUp pop = new popUp(context,"Error",status.getStatusMessage());
            }
        });
        checkBox = (CheckBox)findViewById(R.id.genderCheck);
        gender = userInfoFile.getUserGender(this);
        String check = getString(R.string.MeetingOnlyFor) + gender;
        checkBox.setText(check);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        themeSelected = (parent.getItemAtPosition(position)).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onClickChooseTime(View view) {
        final TextView timeChooser = (TextView) findViewById(R.id.timeChooser);
        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeChooser.setText(hourOfDay + ":" + minute);
            }
        },hour,minute,true);
        timePickerDialog.show();
    }

    public void onClickChooseDate(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(),"date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        ((TextView)findViewById(R.id.dateChooser)).setText(currentDateString);
    }

    public void onClickCreateNewMeeting(View view) {
        String subThemeSelected = ((EditText)findViewById(R.id.subTheme)).getText().toString();
        String timeSelected = ((TextView)findViewById(R.id.timeChooser)).getText().toString();
        String description = ((EditText)findViewById(R.id.description)).getText().toString();
        String date = ((TextView)findViewById(R.id.dateChooser)).getText().toString();
        String Email = userInfoFile.getUserEmail(this);
        String belowAge = ((EditText)findViewById(R.id.belowAge)).getText().toString();
        String afterAge = ((EditText)findViewById(R.id.belowAge)).getText().toString();
        String userName = userInfoFile.getUserName(this);
        if(!(checkBox.isChecked()))
            gender = "";
        database db = new database(this,this);
        db.execute("newMeeting",Email,userName,afterAge,belowAge,gender,themeSelected,subThemeSelected,placeSelected,timeSelected,date,description);
    }
}
