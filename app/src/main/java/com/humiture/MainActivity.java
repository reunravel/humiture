package com.humiture;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    TextView name;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TcpClient.sharedCenter().connect();

        name = findViewById(R.id.name);
        lottieAnimationView = findViewById(R.id.lottie);

        name.animate().translationY(2800).setDuration(1000).setStartDelay(5000);
        lottieAnimationView.setSpeed(2);
        lottieAnimationView.enableMergePathsForKitKatAndAbove(true);
        lottieAnimationView.animate().translationY(2800).setDuration(1000).setStartDelay(5000);

        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(MainActivity.this, introductoryActivity.class);
            MainActivity.this.startActivity(mainIntent);
            MainActivity.this.finish();
        }, 6000);
    }
}