package project.letshang;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookFragment extends Fragment {
    private CallbackManager callbackManager;
    private LoginButton mLoginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facebook, container, false);
        callbackManager = CallbackManager.Factory.create();
        mLoginButton = (LoginButton)view.findViewById(R.id.login_button);
        mLoginButton.setReadPermissions("email");
        mLoginButton.setFragment(this);

        mLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("Main",response.toString());
                        getProfile(object);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                popUp pop = new popUp(getActivity(),"error",error.getMessage());
            }
        });
        return view;
    }

    private void getProfile(JSONObject object) {
        String email="";
        String gender="";
        String birthday="";
        try{
            email = object.getString("email");
            gender = object.getString("gender");
            birthday = object.getString("birthday");
        }catch (JSONException e){
            e.printStackTrace();
        }
        database db = new database(getActivity(),getActivity());
        db.execute("facebookLogin",email,gender,birthday);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
