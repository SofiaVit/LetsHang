package project.letshang;

import android.os.Parcel;
import android.os.Parcelable;

public class FriendForList implements Parcelable {
    private String userName;
    private String email;
    private int id;

    public FriendForList(int id,String email, String userName){
        this.userName = userName;
        this.email = email;
        this.id = id;
    }

    protected FriendForList(Parcel in) {
        userName = in.readString();
        email = in.readString();
    }

    public static final Creator<FriendForList> CREATOR = new Creator<FriendForList>() {
        @Override
        public FriendForList createFromParcel(Parcel in) {
            return new FriendForList(in);
        }

        @Override
        public FriendForList[] newArray(int size) {
            return new FriendForList[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(email);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
