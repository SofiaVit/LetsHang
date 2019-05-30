package project.letshang;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapterForum extends BaseAdapter {
    private Context context;
    private List<ForumForList> list;
    private View view;

    public ListAdapterForum (Context context, List<ForumForList> list){
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
        view = View.inflate(context,R.layout.list_row_forum,null);
        ((TextView)view.findViewById(R.id.authorText)).setText(list.get(position).getUserName());
        ((TextView)view.findViewById(R.id.dateText)).setText(list.get(position).getDate());
        ((TextView)view.findViewById(R.id.messageText)).setText(list.get(position).getMessage());
        return view;
    }
}
