package project.letshang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

public class userMenuFragment extends Fragment {

    public userMenuFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_menu, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bar,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.account_menu){
            getActivity().startActivity(new Intent(getActivity(),userAccount.class));
        }
        else if(id == R.id.logOut_menu){
            AccessToken token = AccessToken.getCurrentAccessToken();
            if(token != null){
                LoginManager.getInstance().logOut();
            }
            userInfoFile.deleteFile(getActivity());
            getActivity().startActivity(new Intent(getActivity(),login.class));

        }
        else if (id == R.id.myMeetings_menu){
            getActivity().startActivity(new Intent(getActivity(),UserMain.class));
        }
        else if(id == R.id.newMeeting_menu){
            getActivity().startActivity(new Intent(getActivity(),newMeeting.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
