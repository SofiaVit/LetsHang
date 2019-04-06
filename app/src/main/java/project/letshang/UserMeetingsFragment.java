package project.letshang;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
        String[] temp = meetingsList.split("\\|\\|");
        meetings = new ArrayList<>();
        int i = 0;
        int id = 1;
        while(i < temp.length){
            meetings.add(new MeetingForList(id,temp[i+1],temp[i+3],temp[i+4],temp[i+5],temp[i+8],temp[i+6],temp[i+7]));
            i=i+11;
            id = id+1;
        }
        mListView = (ListView)view.findViewById(R.id.myMeetings);
        adapter = new ListAdapter(getActivity().getApplicationContext(),meetings);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popUp pop = new popUp(getContext(),"click","clicked");
            }
        });
        return view;
    }

}