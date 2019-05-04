package project.letshang;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.PrivateKey;
import java.util.ArrayList;

public class MeetingForList implements Parcelable {
    private String subTheme;
    private String place;
    private String date;
    private String time;
    private String gender;
    private String afterAge;
    private String belowAge;
    private String theme;
    private String description;
    private String creator;
    private String meetingId;
    private String email;
    private int id;


    public MeetingForList(int id,String theme,String subTheme,String description, String place, String  date, String  time, String gender, String afterAge, String belowAge, String creator, String email, String meetingId){
        this.subTheme = subTheme;
        this.place = place;
        this.date = date;
        this.time = time;
        this.gender = gender;
        this.afterAge = afterAge;
        this.belowAge = belowAge;
        this.id = id;
        this.theme = theme;
        if(description == "") this.description = "None";
        else this.description = description;
        this.creator = creator;
        this.meetingId = meetingId;
        this.email = email;
    }


    protected MeetingForList(Parcel in) {
        subTheme = in.readString();
        place = in.readString();
        date = in.readString();
        time = in.readString();
        gender = in.readString();
        afterAge = in.readString();
        belowAge = in.readString();
        theme = in.readString();
        description = in.readString();
        creator = in.readString();
        meetingId = in.readString();
        email = in.readString();
    }

    public static final Creator<MeetingForList> CREATOR = new Creator<MeetingForList>() {
        @Override
        public MeetingForList createFromParcel(Parcel in) {
            return new MeetingForList(in);
        }

        @Override
        public MeetingForList[] newArray(int size) {
            return new MeetingForList[size];
        }
    };

    public String getSubTheme() {
        return subTheme;
    }

    public void setSubTheme(String subTheme) {
        this.subTheme = subTheme;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAfterAge() {
        return afterAge;
    }

    public void setAfterAge(String afterAge) {
        this.afterAge = afterAge;
    }

    public String getBelowAge() {
        return belowAge;
    }

    public void setBelowAge(String belowAge) {
        this.belowAge = belowAge;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subTheme);
        dest.writeString(place);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(gender);
        dest.writeString(afterAge);
        dest.writeString(belowAge);
        dest.writeString(theme);
        dest.writeString(description);
        dest.writeString(creator);
        dest.writeString(meetingId);
        dest.writeString(email);
    }
}
