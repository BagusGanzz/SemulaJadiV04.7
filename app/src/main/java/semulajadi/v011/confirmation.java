package semulajadi.v011;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class confirmation extends AppCompatActivity {

    Button btn_yes, btn_no;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        btn_yes = (Button)findViewById(R.id.btn_yes);
        btn_no = (Button)findViewById(R.id.btn_no);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(confirmation.this, "Share Successful!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(confirmation.this, "Share Cancel!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(confirmation.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();

                    }
                });
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote("Hello ! We are from SemulaJadi. Thank You For Ordering With Us ! Please do payment for your nature tourism destination to CIMB 761344101600 Bagus Adrianto")
                        .setContentUrl(Uri.parse("http://appsemulajadi.com/index.php"))
                        .build();
                if (ShareDialog.canShow(ShareLinkContent.class))
                {
                    shareDialog.show(linkContent);
                    finish();

//                    Intent intent = new Intent(
//                            getApplicationContext(),
//                            MainActivity.class);
//                    startActivity(intent);
//                    finish();
                }
            }
        });
    }

    public void cancel (View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
