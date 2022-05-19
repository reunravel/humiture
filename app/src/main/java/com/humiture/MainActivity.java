package com.humiture;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.humiture.Entity.DataBase;
import com.humiture.Entity.ObjectBox;
import com.humiture.Entity.TcpClient;

public class MainActivity extends AppCompatActivity {
    TextView name;
    LottieAnimationView lottieAnimationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ObjectBox.init(this);

        DataBase.addData();

        TcpClient.sharedCenter().connect();

        name = findViewById(R.id.name);
        lottieAnimationView = findViewById(R.id.lottie);

        name.animate().translationY(2800).setDuration(1000).setStartDelay(5000);
        lottieAnimationView.setSpeed(2);
        lottieAnimationView.enableMergePathsForKitKatAndAbove(true);
        lottieAnimationView.animate().translationY(2800).setDuration(1000).setStartDelay(5000);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, introductoryActivity.class);
            startActivity(intent);
            finish();
        }, 6000);
    }
}