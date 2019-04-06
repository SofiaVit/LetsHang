package project.letshang;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import java.net.ContentHandler;

public class popUp {
    String title;
    String message;
    Context mContext;
    popUp(Context mContext,String title, String message){
        this.mContext = mContext;
        this.title = title;
        this.message = message;
        createPopUp();
    }

    private void createPopUp(){
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }
        });

        alertDialog.show();
    }
}
