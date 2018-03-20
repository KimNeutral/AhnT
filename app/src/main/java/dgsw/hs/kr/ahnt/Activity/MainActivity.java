package dgsw.hs.kr.ahnt.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import dgsw.hs.kr.ahnt.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnMealClicked(View view) {
        Intent intent = new Intent(this, MealActivity.class);
        startActivity(intent);
    }

}
