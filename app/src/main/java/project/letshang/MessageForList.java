package project.letshang;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageForList implements Parcelable{
    private String MessageId;
    private String Email;
    private String Subject;
    private String Date;
    private String Author;
    private String IsRead;
    private String Body;
    private String Type;
    private int id;

    public MessageForList(int id,String Email, String Subject, String Date, String Author, String IsRead, String Body, String Type, String MessageId){
        this.Email = Email;
        this.Subject = Subject;
        this.Date = Date;
        this.Author = Author;
        this.IsRead = IsRead;
        this.Body = Body;
        this.id = id;
        this.Type = Type;
        this.MessageId = MessageId;
    }

    protected MessageForList(Parcel in) {
        this.Email = in.readString();
        this.Subject = in.readString();
        this.Date = in.readString();
        this.Author = in.readString();
        this.IsRead = in.readString();
        this.Body = in.readString();
        this.Type = in.readString();
        this.MessageId = in.readString();
    }

    public static final Creator<MessageForList> CREATOR = new Creator<MessageForList>() {
        @Override
        public MessageForList createFromParcel(Parcel in) {
            return new MessageForList(in);
        }

        @Override
        public MessageForList[] newArray(int size) {
            return new MessageForList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Email);
        dest.writeString(Subject);
        dest.writeString(Date);
        dest.writeString(Author);
        dest.writeString(IsRead);
        dest.writeString(Body);
        dest.writeString(Type);
        dest.writeString(MessageId);
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getIsRead() {
        return IsRead;
    }

    public void setIsRead(String isRead) {
        IsRead = isRead;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }
}
