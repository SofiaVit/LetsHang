package project.letshang;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class freindMeetingFragment extends Fragment implements View.OnClickListener {

    String creator;
    String meetingId;
    String afterAge;
    String belowAge;
    String gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_freind_meeting, container, false);
        creator = getArguments().getString("Creator");
        meetingId = getArguments().getString("meetingId");
        afterAge = getArguments().getString("afterAge");
        belowAge = getArguments().getString("belowAge");
        gender = getArguments().getString("gender");

        if(creator.equals("true")){
            ((Button)view.findViewById(R.id.leaveButton)).setText(getActivity().getString(R.string.delete));
        }
        ((Button)view.findViewById(R.id.leaveButton)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.membersButton)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.forumButton)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.reportButton)).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.leaveButton:
                if(creator.equals("true")){
                    database db = new database(getActivity(),getActivity());
                    db.execute("deleteMeeting",userInfoFile.getUserEmail(getActivity()),meetingId);
                }
                else{
                    database db = new database(getActivity(),getActivity());
                    db.execute("leaveMeeting",userInfoFile.getUserEmail(getActivity()),meetingId,afterAge,belowAge,gender);
                }
                break;
            case R.id.membersButton:
                database db = new database(getActivity(),getActivity());
                db.execute("meetingFriends",meetingId);
                break;
            case R.id.forumButton:
                break;
            case R.id.reportButton:
                    break;
        }

    }
}
