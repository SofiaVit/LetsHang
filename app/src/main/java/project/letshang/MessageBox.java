package project.letshang;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageBox extends AppCompatActivity {
    private ListView mListView;
    private ListAdapterMessages adapter;
    private List<MessageForList> messagesList;
    private Context mContext;
    private Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meesage_box);
        this.mContext = this;
        this.mActivity = this;
        final String messages = getIntent().getStringExtra("messages");
        if(messages.equals("Empty")){
            ((TextView)findViewById(R.id.noMessages)).setVisibility(View.VISIBLE);
        }
        else {
            String[] temp = messages.split("\\|\\|");
            messagesList = new ArrayList<>();
            int i = 0;
            int id = 1;
            while (i < temp.length) {
                if(temp[i+6].equals("True")){
                    String body = getResources().getString(R.string.friendInvateBody) + " " + temp[i+4];
                    messagesList.add(new MessageForList(id, temp[i],getResources().getString(R.string.friendInvate), temp[i + 2], temp[i + 3], temp[i + 4], body , temp[i+6], temp[i+7]));
                }
                else if(temp[i+7].equals("MeetingInvite")){
                    messagesList.add(new MessageForList(id, temp[i],getResources().getString(R.string.MeetingInvitation),temp[i + 2], temp[i + 3], temp[i + 4], temp[i + 5], temp[i+6], temp[i+7] ));
                }
                else
                    messagesList.add(new MessageForList(id, temp[i], temp[i + 1], temp[i + 2], temp[i + 3], temp[i + 4], temp[i + 5], temp[i+6],temp[i+7]));
                i = i + 9;
                id = id + 1;
            }
            mListView = (ListView) findViewById(R.id.messagesList);
            adapter = new ListAdapterMessages(this.getBaseContext(), messagesList);
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    if(messagesList.get(position).getType().equals("FriendInvate")){
                        final Dialog dialog = new Dialog(mActivity);
                        dialog.setContentView(R.layout.dialog_yes_no);
                        String Author = messagesList.get(position).getAuthor();
                        String alertText = getResources().getString(R.string.Add) + " " + Author + " " + getResources().getString(R.string.toFriends);
                        ((TextView)dialog.findViewById(R.id.alertText)).setText(alertText);
                        Button yesButton = (Button)dialog.findViewById(R.id.yesButton);
                        yesButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                database db = new database(mContext,mActivity);
                                String Email = messagesList.get(position).getEmail();
                                String Author = messagesList.get(position).getAuthor();
                                db.execute("addFriend",Email,Author,userInfoFile.getUserName(mContext));
                                dialog.dismiss();
                            }
                        });
                        Button noButton = (Button)dialog.findViewById(R.id.noButton);
                        noButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                database db = new database(mContext,mActivity);
                                db.execute("deleteMessage",messagesList.get(position).getMessageId(),messagesList.get(position).getEmail());
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                    }
                    else if(messagesList.get(position).getType().equals("Message")){
                        showDialog.showReadMessageDialog(mContext,mActivity,messagesList.get(position).getSubject(),messagesList.get(position).getBody(),messagesList.get(position).getAuthor(),messagesList.get(position).getMessageId());
                    }
                    else if(messagesList.get(position).getType().equals("MeetingInvite")){
                        database db = new database(mContext,mActivity);
                        db.execute("findMeeting",messagesList.get(position).getBody());
                        //Body is the id of message||SubTheme . Show dialog three buttons: cancel, delete, go to meeting.
                        //Body: Friend (Name ) invited you to meeting: "SubTheme";
                        //if go to meeting- get meeting info based on id and put into MeetingForList.
                        //Send to meetingInfo.class
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if(true){
            startActivity(new Intent(this,UserMain.class));
        }
        else
            super.onBackPressed();
    }


}
