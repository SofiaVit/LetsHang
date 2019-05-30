package project.letshang;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class showDialog {
    private String messageId;
    private Context mContext;
    private Activity mActivity;

   public static void showErrorDialog(Context mContext,String body){
       final Dialog dialog = new Dialog(mContext);
       dialog.setContentView(R.layout.dialog_error);
       ((TextView)dialog.findViewById(R.id.bodyText)).setText(body);
       RelativeLayout dismiss_button = (RelativeLayout)dialog.findViewById(R.id.dismissButton);
       dismiss_button.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               dialog.dismiss();

           }
       });
       dialog.show();
   }


   public static void showReportDialog(final Context mContext, final Activity mActivity, final String type, final String id){
       final String[] header = new String[1];
       final Dialog dialog = new Dialog(mContext);
       if(type.equals("meeting"))
            dialog.setContentView(R.layout.dialog_report_meeting);
       else
           dialog.setContentView(R.layout.dialog_report_user);
       dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       final AppCompatSpinner spinner = (AppCompatSpinner)dialog.findViewById(R.id.headerSpinner);
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                header[0] = parent.getItemAtPosition(position).toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {
                header[0] = parent.getItemAtPosition(4).toString();
           }
       });
       ((Button)dialog.findViewById(R.id.cancelButton)).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();
           }
       });
       ((Button)dialog.findViewById(R.id.sendButton)).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();
           }
       });

       ((Button)dialog.findViewById(R.id.sendButton)).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String description = ((EditText)dialog.findViewById(R.id.bodyText)).getText().toString();
               database db = new database(mContext,mActivity);
               db.execute("report",id,header[0],description);
               dialog.dismiss();
           }
       });
       dialog.show();
   }

   public static  void showNotificationDialog(Context mContext,String header,String body){
       final Dialog dialog = new Dialog(mContext);
       dialog.setContentView(R.layout.dialog_error);
       ((TextView)dialog.findViewById(R.id.headerText)).setText(header);
       ((TextView)dialog.findViewById(R.id.bodyText)).setText(body);
       RelativeLayout dismiss_button = (RelativeLayout)dialog.findViewById(R.id.dismissButton);
       dismiss_button.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               dialog.dismiss();

           }
       });
       dialog.show();
   }

   public static void showInviteMeetingDialog(final Context mContext,final Activity mActivity, final MeetingForList list){
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_meeting_invite);
        String body = list.getCreator() + " " + mContext.getResources().getString(R.string.invitedToMeeting) + " '" + list.getSubTheme() + "'";
       ((TextView)dialog.findViewById(R.id.bodyText)).setText(body);
       ((Button)dialog.findViewById(R.id.cancelButton)).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();
           }
       });
       ((Button)dialog.findViewById(R.id.deleteButton)).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               database db = new database(mContext,null);
               db.execute("deleteMessage",list.getMeetingId(),userInfoFile.getUserEmail(mContext));
               dialog.dismiss();
           }
       });
       ((Button)dialog.findViewById(R.id.goToMeetingButton)).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(mActivity, meetingInfo.class);
               intent.putExtra("Meeting", list);
               mContext.startActivity(intent);
           }
       });
       dialog.show();
   }

   public static void showReadMessageDialog(final Context mContext, final Activity mActivity, String header, String body, final String author, final String messageId){
       final Dialog dialog = new Dialog(mContext);
       dialog.setContentView(R.layout.dialog_read_message);
       ((TextView)dialog.findViewById(R.id.headerText)).setText(header);
       ((TextView)dialog.findViewById(R.id.bodyText)).setText(body);
       ((RelativeLayout)dialog.findViewById(R.id.closeButton)).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();
           }
       });
       ((RelativeLayout)dialog.findViewById(R.id.deleteButton)).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               database db = new database(mContext,null);
               db.execute("deleteMessage",messageId,userInfoFile.getUserEmail(mContext));
               dialog.dismiss();
           }
       });
       ((RelativeLayout)dialog.findViewById(R.id.repeatButton)).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                dialog.dismiss();
               final Dialog dialog = new Dialog(mContext);
               dialog.setContentView(R.layout.dialog_message);
               ((Button)dialog.findViewById(R.id.sendButton)).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       String theme = ((EditText)dialog.findViewById(R.id.ThemeText)).getText().toString();
                       String body = ((EditText)dialog.findViewById(R.id.MessageText)).getText().toString();
                       Date date = new Date();
                       DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                       database db = new database(mContext,mActivity);
                       db.execute("findEmailMessage",null,theme,dateFormat.format(date),userInfoFile.getUserName(mContext),"False",body,"Message",author);
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

       dialog.show();
   }

}
