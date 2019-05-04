package project.letshang;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapterMessages extends BaseAdapter {
    private Context context;
    private List<MessageForList> list;
    private View view;

    public ListAdapterMessages(Context context, List<MessageForList> list){
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
        view = View.inflate(context,R.layout.list_row_messages,null);
        ((TextView)view.findViewById(R.id.authorText)).setText(list.get(position).getAuthor());
        ((TextView)view.findViewById(R.id.dateText)).setText(list.get(position).getDate());
        ((TextView)view.findViewById(R.id.subjectText)).setText(list.get(position).getSubject());
        String isRead = list.get(position).getIsRead();
        if(isRead.equals("True")) {
            ((TextView) view.findViewById(R.id.isReadText)).setVisibility(View.INVISIBLE);
        }
        return view;
    }
}
