package tr.edu.yildiz.betul.myvirtualwardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String DRAWERS_FILE_NAME = "MyDrawers.txt";
    public static final String ACTIVITIES_FILE_NAME = "MyActivities.txt";
    public static final String OUTFITS_FILE_NAME = "MyOutfits.txt";
    public static final String IMAGES_FILE_PATH = "sdcard/Android/data/tr.edu.yildiz.betul.myvirtualwardrobe/images/";
    private ArrayList<Drawer> drawers;
    private LinearLayout listDrawers;
    private LinearLayout cabin;
    private LinearLayout activities;
    private LinearLayout combinations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defineVariables();
        defineListeners();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.linearLayoutMyDrawers:
                intent = new Intent(v.getContext(), DrawersActivity.class);
//                Bundle args = new Bundle();
//                args.putSerializable("DRAWERS",(Serializable) drawers);
//                args.putInt("POSITION", 0);
//                intent.putExtra("BUNDLE",args);
                startActivity(intent);
                break;
            case R.id.linearLayoutMyCabinet:
                intent = new Intent(v.getContext(), CabinActivity.class);
                startActivity(intent);
                break;

            case R.id.linearLayoutShareCombinations:
                intent = new Intent(v.getContext(), ShareOutfitActivity.class);
                startActivity(intent);
                break;

            case R.id.linearLayoutMyActivities:
                intent = new Intent(v.getContext(), ActivitiesActivity.class);
                startActivity(intent);
                break;


//            case R.id.linearLayoutLogOut:
//                intent = new Intent(v.getContext(), MainActivity.class);
//                Toast.makeText(this, "Goodbye " + userName, Toast.LENGTH_SHORT).show();
//                startActivity(intent);
//                break;
            default:
                break;

        }
    }

    private void defineVariables() {
        listDrawers = (LinearLayout) findViewById(R.id.linearLayoutMyDrawers);
        cabin = (LinearLayout) findViewById(R.id.linearLayoutMyCabinet);
        activities = (LinearLayout) findViewById(R.id.linearLayoutMyActivities);
        combinations = (LinearLayout) findViewById(R.id.linearLayoutShareCombinations);
        drawers = SerializableManager.readSerializable(MainActivity.this, DRAWERS_FILE_NAME);
    }
    private void defineListeners() {
        listDrawers.setOnClickListener(this);
        cabin.setOnClickListener(this);
        activities.setOnClickListener(this);
        combinations.setOnClickListener(this);
    }

}