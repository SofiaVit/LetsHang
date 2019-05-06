package project.letshang;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;

public class notFriendFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_not_friend, container, false);
        final String afterAge = getArguments().getString("afterAge");
        final String belowAge = getArguments().getString("belowAge");
        final String gender = getArguments().getString("gender");
        final String meetingId = getArguments().getString("meetingId");
        ((Button)view.findViewById(R.id.joinButton)).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                boolean flag = true;
                String userBirthday = userInfoFile.getUserBirthday(getActivity());
                String[] temp = userBirthday.split("/");
                LocalDate birthday = LocalDate.of(Integer.parseInt(temp[2]),Integer.parseInt(temp[1]),Integer.parseInt(temp[0]));
                LocalDate now = LocalDate.now();
                int age = Period.between(birthday,now).getYears();
                if(!afterAge.equals("None")){
                    if(age > Integer.parseInt(afterAge)){
                        popUp pop = new popUp(getActivity(),"message","Your age is outside the limit");
                        flag = false;
                    }
                }
                if(!belowAge.equals("None")){
                    if(age < Integer.parseInt(belowAge)){
                        popUp pop = new popUp(getActivity(),"message","Your age is outside the limit");
                        flag = false;
                    }

                }
                if(!gender.equals("None")){
                    if(!userInfoFile.getUserGender(getActivity()).equals(gender)){
                        popUp pop = new popUp(getActivity(),"message","This meeting is only for " + gender);
                        flag = false;
                    }
                }
                if(flag){
                    database db = new database(getActivity(),getActivity());
                    db.execute("joinMeeting",userInfoFile.getUserEmail(getActivity()),meetingId,afterAge,belowAge,gender);
                }
            }
        });
        return view;
    }

}
