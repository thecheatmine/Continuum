package com.continuum.continuum;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.content.pm.ActivityInfo;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HistoireActivity extends AppCompatActivity {

    AnimationDrawable Animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(R.layout.activity_histoire);
        hideSystemUI();

        ImageView imageView = findViewById(R.id.carre);
        imageView.setBackgroundResource(R.drawable.animation);
        Animation = (AnimationDrawable) imageView.getBackground();
        Animation.start();

        imageView = findViewById(R.id.carre2);
        imageView.setBackgroundResource(R.drawable.animation);
        Animation = (AnimationDrawable) imageView.getBackground();
        Animation.start();

        imageView = findViewById(R.id.carre3);
        imageView.setBackgroundResource(R.drawable.animation);
        Animation = (AnimationDrawable) imageView.getBackground();
        Animation.start();

        Button btn=findViewById(R.id.bouton_Histoire2);
        btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                ImageView image = findViewById(R.id.carre);
                    Mouvement(image);

            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int px = Math.round(dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public void Mouvement(ImageView image) {
        //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //lp.setMargins(10, 10, 0, 0);
        //findViewById(R.id.carre).setLayoutParams(lp);
        ConstraintLayout.LayoutParams marginParams = (ConstraintLayout.LayoutParams) image.getLayoutParams();
        int top = (marginParams.topMargin+(int)convertDpToPixel(100, HistoireActivity.this));
        marginParams.setMargins(0, top, 0, 0);
        image.setLayoutParams(marginParams);
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
