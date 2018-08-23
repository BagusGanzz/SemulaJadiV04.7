package semulajadi.v011;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(1000)
                .withBackgroundColor(Color.parseColor("#FFFFFF"))
                .withLogo(R.mipmap.logo)
                .withBeforeLogoText("Welcome !")
                .withFooterText("SBR.Corp")
                .withAfterLogoText("makes your planning goes planned");

        //set Text Color
        config.getBeforeLogoTextView().setTextColor(Color.BLACK);
        config.getFooterTextView().setTextColor(Color.BLACK);
        config.getAfterLogoTextView().setTextColor(Color.BLACK);

        //set to view
        View view = config.create();

        //set view to content view
        setContentView(view);
    }
}
