package project.letshang;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Calendar;

public class searchMeeting extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String placeSelected;
    String timeSelected;
    String themeSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_meeting);
        database db = new database(this,this);
        db.execute("allMeetings");
        placeSelected = "%";
        timeSelected = "%";
        themeSelected = "%";
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        themeSelected = (parent.getItemAtPosition(position)).toString();
        if(position == 0){
            themeSelected = "%";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        themeSelected = "%";
    }

    public void onClickSearch(View view) {
        database db = new database(this,this);
        if(!themeSelected.equals("%") || !timeSelected.equals("%") || !placeSelected.equals("%")) {
            db.execute("searchMeeting",themeSelected,timeSelected,placeSelected);
        }
    }

    @Override
    public void onBackPressed() {
        if(true){
            finish();
            startActivity(new Intent(this,UserMain.class));
        }
        super.onBackPressed();
    }
}
