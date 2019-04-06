package project.letshang;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.login.Login;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<MeetingForList> list;

    public ListAdapter(Context context, List<MeetingForList> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context,R.layout.list_row,null);
        ((TextView)view.findViewById(R.id.subThemeText)).setText(list.get(position).getSubTheme());
        ((TextView)view.findViewById(R.id.timeText)).setText(list.get(position).getTime());
        ((TextView)view.findViewById(R.id.placeText)).setText(list.get(position).getPlace());
        ((TextView)view.findViewById(R.id.dateText)).setText(list.get(position).getDate());
        String afterAge = list.get(position).getAfterAge();
        String belowAge = list.get(position).getBelowAge();
        TextView betweenAge = (TextView)view.findViewById(R.id.betweenAgeText);
        if(!(afterAge.equals("None")) || !(belowAge.equals("None"))) {
            if (afterAge.equals("None"))
                afterAge = "0";
            if (belowAge.equals("None"))
                belowAge = "0";
            String between = afterAge + "-" + belowAge;
            betweenAge.setText(between);
        }
        String gender = list.get(position).getGender();
        TextView genderText = (TextView)view.findViewById(R.id.GenderText);
        if(!(gender.equals("None")))
            genderText.setText(list.get(position).getGender());
        view.setTag(list.get(position).getId());
        return view;
    }
}
