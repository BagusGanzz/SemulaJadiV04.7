package semulajadi.v011;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ryan on 06/12/2017.
 */

public class AboutUs extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

    }

    public void facebook (View view){
        Intent openfacebook = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/AppSemulaJadi"));
        startActivity(openfacebook);
    }

    public void twiter (View view){
        Intent opentwiter = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/@AppSemulajadi"));
        startActivity(opentwiter);
    }

    public void instagram (View view){
        Intent openinstagram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/appsemulajadi/"));
        startActivity(openinstagram);
    }
}
