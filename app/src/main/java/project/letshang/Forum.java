package project.letshang;

import android.app.Activity;
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

public class Forum extends AppCompatActivity {
    private ListView mListView;
    private ListAdapterForum adapter;
    private List<ForumForList> forumList;
    private Context mContext;
    private Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        final String forum = getIntent().getStringExtra("forum");
        final String meetingId = getIntent().getStringExtra("meetingId");
        this.mActivity = this;
        this.mContext = this;
        if(forum.equals("noMessages")){
            ((TextView)findViewById(R.id.noMessagesText)).setVisibility(View.VISIBLE);
        }
        else {
            String[] temp = forum.split("\\|\\|");
            forumList = new ArrayList<>();
            int i = 0;
            int id = 1;
            while (i < temp.length) {
                forumList.add(new ForumForList(id, temp[i], temp[i + 1], temp[i + 2]));
                i = i + 4;
                id = id + 1;
            }
            mListView = (ListView) findViewById(R.id.messagesList);
            adapter = new ListAdapterForum(this, forumList);
            mListView.setAdapter(adapter);
        }

        ((ImageView)findViewById(R.id.envelopeButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = ((EditText)findViewById(R.id.forumMessageText)).getText().toString();
                database db = new database(mContext,mActivity);
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                db.execute("forumWriteMessage",meetingId,userInfoFile.getUserName(mContext),message,dateFormat.format(date));
            }
        });

    }
}
