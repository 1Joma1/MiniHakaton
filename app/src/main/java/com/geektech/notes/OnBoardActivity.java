package com.geektech.notes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OnBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
    }

    //if btn clicked this will not show again (onBoard)
    public void firstClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
        //animation
        overridePendingTransition(R.anim.right_in_alpha, R.anim.left_out_alpha);
        SharedPreferences sharedPreferences = getSharedPreferences("firstTime", MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("First", false).apply();
        finish();
    }
}
