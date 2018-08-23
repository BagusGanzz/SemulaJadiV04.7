package semulajadi.v011;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class fblogin extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    URL profile_pic;
    String id;
    String nameOrder, idOrder;
    int priceOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fblogin);

        //nangkep variable
        Intent intent = getIntent();
        if (intent.hasExtra("idYangDiPesan")){
            idOrder = intent.getStringExtra("idYangDiPesan");
            nameOrder = intent.getStringExtra("OrderYgDiPesan");
            priceOrder = intent.getIntExtra("Harga", 0);
        }else {
            finish();
            Toast.makeText(getApplicationContext(), "logged out", Toast.LENGTH_SHORT).show();
        }

        //cek_status_login
        boolean LoginButton = AccessToken.getCurrentAccessToken() == null;
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile","email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String accesToken = loginResult.getAccessToken().getToken();
                Log.i("accesToken",accesToken);
                //GraphJSON API FACEBOOK
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        Bundle bFacebookData = getFacebookData(object);
                        String firstname = bFacebookData.getString("first_name");
                        String lastname = bFacebookData.getString("last_name");
                        String email = bFacebookData.getString("email");
                        String profilepic = bFacebookData.getString("profile_pic");
                        Intent Intent = new Intent(fblogin.this, fbform.class);
                        //lempar variablbe
                        Intent.putExtra("idYangDiPesan", idOrder);
                        Intent.putExtra("OrderYgDiPesan", nameOrder);
                        Intent.putExtra("Harga", priceOrder);
                        Intent.putExtra("firstname", firstname);
                        Intent.putExtra("lastname",lastname);
                        Intent.putExtra("email",email);
                        Intent.putExtra("image",profilepic);

                        startActivity(Intent);
                        finish();

                    }
                });
                //request graph API
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,email,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                finish();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();
        try {
            id = object.getString("id");
            profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=250&height=250");
            Log.i("profile_pic",profile_pic +"");
            bundle.putString("profile_pic",profile_pic.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        bundle.putString("idFacebook",id);

        if (object.has("first_name")){
            try {
                bundle.putString("first_name", object.getString("first_name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (object.has("last_name")){
            try {
                bundle.putString("last_name", object.getString("last_name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (object.has("email")){
            try {
                bundle.putString("email", object.getString("email"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return bundle;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        loginButton.setVisibility(View.GONE);
    }

}
