package project.letshang;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class meetingInfo extends AppCompatActivity {
    MeetingForList meeting;
    String userEmail;
    String meetingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_info);
        if(savedInstanceState != null){
            meeting = savedInstanceState.getParcelable("meeting");
            userEmail = savedInstanceState.getString("userEmail");
            meetingId = savedInstanceState.getString("meetingId");
        }
        else {
            meeting = (MeetingForList) getIntent().getParcelableExtra("Meeting");
            userEmail = userInfoFile.getUserEmail(this);
            meetingId = meeting.getMeetingId();
        }
        String creator = "false";
        if(userEmail.equals(meeting.getEmail()))
            creator = "true";
        database db = new database(this,this);
        db.execute("checkIfMeetingFriend",userEmail,meetingId,creator,meeting.getBelowAge(),meeting.getAfterAge(),meeting.getGender(),meeting.getMeetingId());
        ((TextView)findViewById(R.id.ThemeText)).setText(meeting.getTheme());
        ((TextView)findViewById(R.id.subThemeText)).setText(meeting.getSubTheme());
        ((TextView)findViewById(R.id.timeText)).setText(meeting.getTime());
        ((TextView)findViewById(R.id.dateText)).setText(meeting.getDate());
        ((TextView)findViewById(R.id.descriptionText)).setText(meeting.getDescription());
        ((TextView)findViewById(R.id.genderText)).setText(meeting.getGender());
        String age = meeting.getAfterAge() + "-" + meeting.getBelowAge();
        ((TextView)findViewById(R.id.ageRangeText)).setText(age);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable("meeting",meeting);
        outState.putString("userEmail",userEmail);
        outState.putString("meetingId", meetingId);
    }

}
