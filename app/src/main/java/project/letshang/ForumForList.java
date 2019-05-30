package project.letshang;

import android.os.Parcel;
import android.os.Parcelable;


public class ForumForList implements Parcelable {

    private String UserName;
    private String Message;
    private String Date;
    private int id;

    public ForumForList(int id, String UserName, String Message, String Date){
        this.id = id;
        this.Message = Message;
        this.Date = Date;
        this.UserName = UserName;
    }




    protected ForumForList(Parcel in) {
        this.UserName = in.readString();
        this.Message = in.readString();
        this.Date = in.readString();
    }

    public static final Creator<ForumForList> CREATOR = new Creator<ForumForList>() {
        @Override
        public ForumForList createFromParcel(Parcel in) {
            return new ForumForList(in);
        }

        @Override
        public ForumForList[] newArray(int size) {
            return new ForumForList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UserName);
        dest.writeString(Message);
        dest.writeString(Date);
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
