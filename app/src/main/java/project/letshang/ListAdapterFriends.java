package project.letshang;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ListAdapterFriends extends BaseAdapter {
    private Context context;
    private Activity activity;
    private List<FriendForList> list;
    private View view;
    private String FriendsType;
    private String userName;

    public ListAdapterFriends(Context context,Activity activity, List<FriendForList> list, String FriendsType, String userName){
        this.context = context;
        this.list = list;
        this.FriendsType = FriendsType;
        this.userName = userName;
        this.activity = activity;
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
    public View getView(final int position, final View convertView, ViewGroup parent) {
        view = View.inflate(context,R.layout.list_row_friends,null);
        String userName = list.get(position).getUserName();
        ((TextView)view.findViewById(R.id.userNameText)).setText(userName);
        view.setTag(list.get(position).getId());
        if(FriendsType.equals("Meeting")) {
            ImageView addFrindImage = (ImageView)view.findViewById(R.id.addFriendImage);
            if(this.userName.equals(userName)){
                addFrindImage.setVisibility(View.INVISIBLE);
            }
            else {
                ((ImageView) view.findViewById(R.id.addFriendImage)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        Date date = new Date();
                        database db = new database(context, null);
                        db.execute("invateFriend", list.get(position).getEmail(), context.getString(R.string.friendInvate), dateFormat.format(date), userInfoFile.getUserName(context), "False", context.getString(R.string.friendInvate),"FriendInvate");
                    }
                });
            }
            ((ImageView) view.findViewById(R.id.deletefriendImage)).setVisibility(View.INVISIBLE);
            ((ImageView) view.findViewById(R.id.emailImage)).setVisibility(View.INVISIBLE);
        }
        else if(FriendsType.equals("User")){
            ((ImageView)view.findViewById(R.id.addFriendImage)).setVisibility(View.INVISIBLE);
            ((ImageView) view.findViewById(R.id.deletefriendImage)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(activity);
                    dialog.setContentView(R.layout.dialog_yes_no);
                    ((TextView)dialog.findViewById(R.id.alertText)).setText(context.getResources().getString(R.string.deleteFriend));
                    Button yesButton = (Button)dialog.findViewById(R.id.yesButton);
                    yesButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            database db = new database(context,activity);
                            db.execute("deleteFriend",list.get(position).getEmail(),list.get(position).getUserName(),userInfoFile.getUserName(context));
                            dialog.dismiss();
                        }
                    });
                    Button noButton = (Button)dialog.findViewById(R.id.noButton);
                    noButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                   dialog.show();
                }
            });
            ((ImageView) view.findViewById(R.id.emailImage)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(activity);
                    dialog.setContentView(R.layout.dialog_message);
                    ((Button)dialog.findViewById(R.id.sendButton)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String theme = ((EditText)dialog.findViewById(R.id.ThemeText)).getText().toString();
                            String body = ((EditText)dialog.findViewById(R.id.MessageText)).getText().toString();
                            Date date = new Date();
                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            database db = new database(context,activity);
                            db.execute("sendMessage",list.get(position).getEmail(),theme,dateFormat.format(date),userInfoFile.getUserName(context),"False",body,"Message");
                            dialog.dismiss();
                        }
                    });
                    ((Button)dialog.findViewById(R.id.cancelButton)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });

        }
        return view;
    }
}
