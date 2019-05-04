package project.letshang;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserFriends extends AppCompatActivity {
    private ListView mListView;
    private ListAdapterFriends adapter;
    private List<FriendForList> friendsList;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_friends);
        mContext = this;
        String friends = getIntent().getStringExtra("friends");

        if(friends.equals("noFriends")){
            ((TextView)findViewById(R.id.noFriendsText)).setVisibility(View.VISIBLE);
        }
        else {
            String[] temp = friends.split("\\|\\|");
            friendsList = new ArrayList<>();
            int i = 0;
            int id = 1;
            while (i < temp.length) {
                friendsList.add(new FriendForList(id, temp[i], temp[i + 1]));
                i = i + 3;
                id = id + 1;
            }
            mListView = (ListView) findViewById(R.id.userFriendsList);
            adapter = new ListAdapterFriends(this,this, friendsList, "User", userInfoFile.getUserName(this));
            mListView.setAdapter(adapter);
        }
        ((ImageView)findViewById(R.id.envelopeButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String friendUserName = ((EditText)findViewById(R.id.friendUserName)).getText().toString();
                if(friendUserName.isEmpty()){
                    showDialog.showErrorDialog(mContext,getString(R.string.emptyUserName));
                }
                else{
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date date = new Date();
                    database db = new database(mContext, null);
                    db.execute("findEmail", userInfoFile.getUserEmail(mContext), mContext.getString(R.string.friendInvate), dateFormat.format(date), userInfoFile.getUserName(mContext), "False", mContext.getString(R.string.friendInvate),"FriendInvate",friendUserName);
                }
            }
        });
    }
}
