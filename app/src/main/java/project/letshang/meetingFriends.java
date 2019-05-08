package project.letshang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class meetingFriends extends AppCompatActivity {
    private ListView mListView;
    private ListAdapterFriends adapter;
    private List<FriendForList> friendsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_friends);
        String friends = getIntent().getStringExtra("friends");
        String[] temp = friends.split("\\|\\|");
        friendsList = new ArrayList<>();
        int i = 0;
        int id = 1;
        while(i < temp.length){
            friendsList.add(new FriendForList(id,temp[i],temp[i+1]));
            i=i+3;
            id = id+1;
        }
        mListView = (ListView) findViewById(R.id.meetingFriendsList);
        adapter = new ListAdapterFriends(this,this,friendsList,"Meeting",userInfoFile.getUserName(this),null);
        mListView.setAdapter(adapter);
    }
}
