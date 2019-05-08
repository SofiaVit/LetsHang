package project.letshang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class UserMeetingsFragment extends Fragment {
    private ListView mListView;
    private ListAdapter adapter;
    private List<MeetingForList> meetings;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_meetings, container, false);
        String meetingsList = getArguments().getString("MeetingsInfo");
        if(meetingsList.equals("noMeetings")){
            ((TextView)view.findViewById(R.id.noMeetingsText)).setVisibility(View.VISIBLE);
        }
        else {
            String[] temp = meetingsList.split("\\|\\|");
            meetings = new ArrayList<>();
            int i = 0;
            int id = 1;
            while (i < temp.length) {
                meetings.add(new MeetingForList(id, temp[i], temp[i + 1], temp[i + 2], temp[i + 3], temp[i + 4], temp[i + 5], temp[i + 8], temp[i + 6], temp[i + 7], temp[i + 9], temp[i + 10], temp[i + 11]));
                i = i + 13;
                id = id + 1;
            }
            mListView = (ListView) view.findViewById(R.id.myMeetings);
            adapter = new ListAdapter(getActivity().getApplicationContext(), meetings);
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), meetingInfo.class);
                    MeetingForList meeting = meetings.get(position);
                    intent.putExtra("Meeting", meeting);
                    startActivity(intent);
                }
            });
        }
        ((CardView)view.findViewById(R.id.newMeeting)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),newMeeting.class));
            }
        });
        return view;
    }

}