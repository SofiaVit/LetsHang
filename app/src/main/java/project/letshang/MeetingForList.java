package project.letshang;

import java.security.PrivateKey;
import java.util.ArrayList;

public class MeetingForList {
    private String subTheme;
    private String place;
    private String date;
    private String time;
    private String gender;
    private String afterAge;
    private String belowAge;
    private int id;


    public MeetingForList(int id,String subTheme, String place, String  date, String  time, String gender, String afterAge, String belowAge){
        this.subTheme = subTheme;
        this.place = place;
        this.date = date;
        this.time = time;
        this.gender = gender;
        this.afterAge = afterAge;
        this.belowAge = belowAge;
        this.id = id;
    }


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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
