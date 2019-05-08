package project.letshang;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class inviteFriendMeeting extends AppCompatActivity {
    private ListView mListView;
    private ListAdapterFriends adapter;
    private List<FriendForList> friendsList;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend_meeting);
    }
}
