package project.letshang;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class showDialog {
    private String messageId;
    private Context mContext;

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

   public static void showReadMessageDialog(final Context mContext, String header, String body, String author, final String messageId){
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
               db.execute("deleteMessage",messageId);
               dialog.dismiss();
           }
       });
       ((RelativeLayout)dialog.findViewById(R.id.repeatButton)).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });

       dialog.show();
   }
}
