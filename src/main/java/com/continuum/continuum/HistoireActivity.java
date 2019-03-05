package com.continuum.continuum;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.content.pm.ActivityInfo;
import android.widget.Button;
import android.widget.ImageView;

public class HistoireActivity extends AppCompatActivity {
    private Personnage perso1;
    private AnimationDrawable Animation;
    private int carre_number = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(R.layout.activity_histoire);
        hideSystemUI();

        ImageView imageView = findViewById(R.id.carre2);
        imageView.setBackgroundResource(R.drawable.animation);
        Animation = (AnimationDrawable) imageView.getBackground();
        Animation.start();



        final ConstraintLayout layout = findViewById(R.id.game_layout);
        final ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) findViewById(R.id.carre).getLayoutParams();
        final Context app_context = getApplicationContext();
        perso1 = new Personnage();


        Button btn=findViewById(R.id.bouton_Histoire2);
        btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //update_view(carre_number, lp, layout, app_context, perso1.x);
                carre_number++;
                perso1.moveWithSpeed();
            }
        });
    }

    public void update_view(int id, ConstraintLayout.LayoutParams lp, ConstraintLayout layout, Context app_context, int x, int speed){

        x+=speed;

        // si x dépasse la largeur de l'écran, on inverse le déplacement
        if(x > 500){speed=-speed;}

        // si x passe à gauche de l'écran, on inverse le déplacement
        if(x<-500){speed=-speed;}

        ImageView new_carre = new ImageView(app_context);
        new_carre.setId(id);

        ConstraintLayout.LayoutParams newParams = new ConstraintLayout.LayoutParams(100, 100);

        newParams.endToEnd = lp.endToEnd;
        newParams.topToTop = lp.topToTop;
        newParams.bottomToBottom = lp.bottomToBottom;
        newParams.startToStart = lp.startToStart;

        newParams.setMargins(x,Math.round((float)Math.random()*500),0,Math.round((float)Math.random()*500));

        new_carre.setLayoutParams(newParams);
        new_carre.setBackgroundResource(R.drawable.animation);

        Animation = (AnimationDrawable) new_carre.getBackground();
        Animation.start();
        layout.addView(new_carre);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return Math.round(dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
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
    }
}
