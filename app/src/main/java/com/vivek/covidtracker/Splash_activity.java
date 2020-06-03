package com.vivek.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Splash_activity extends AppCompatActivity {

    TextView covid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);
        covid =findViewById(R.id.covid);
        YoYo.with(Techniques.BounceIn)
                .duration(1000)
                .repeat(2)
                .playOn(covid);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                Intent mainIntent = new Intent(Splash_activity.this,MainActivity.class);
                Splash_activity.this.startActivity(mainIntent);
                Splash_activity.this.finish();


            }
        }, 6000);
    }
}
