package tr.edu.yildiz.betul.myvirtualwardrobe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ActivitiesActivity extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Activity> activities;
    RecyclerView recyclerView;
    ActivityAdapter activityAdapter;
    private ImageButton returnButton, addButton;
    EditText activityNameEditText;
    private Clothing clothing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        defineVariables();
        defineListeners();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ChooseClothesActivity.RESULT_OK && requestCode == 1){
            Bundle args = data.getBundleExtra("BUNDLE");
            clothing = (Clothing) args.getSerializable("CLOTHING");
        }
    }

    private void defineVariables() {
        returnButton = findViewById(R.id.imageButtonMyActivitiesReturn);
        addButton = findViewById(R.id.imageButtonMyActivitiesAdd);
        activityNameEditText = findViewById(R.id.editTextMyActivities);
        activityNameEditText.setVisibility(View.INVISIBLE);
        recyclerView = findViewById(R.id.recyclerViewActivities);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        activities = SerializableManager.readSerializable(ActivitiesActivity.this, MainActivity.ACTIVITIES_FILE_NAME);
        if (activities == null){
            Toast.makeText(ActivitiesActivity.this, "No activities", Toast.LENGTH_SHORT).show();
            activities = new ArrayList<>();
        }else{
            activityAdapter = new ActivityAdapter(this, activities, clothing);
            recyclerView.setAdapter(activityAdapter);
        }
    }

    private void defineListeners() {
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityNameEditText.getVisibility() == View.VISIBLE){
                    activityNameEditText.setVisibility(View.INVISIBLE);
                    Activity activity = new Activity(activityNameEditText.getText().toString(), null, null, null, null);
                    activities.add(activity);
                    SerializableManager.saveSerializable(ActivitiesActivity.this, activities, MainActivity.ACTIVITIES_FILE_NAME, false);
                    Intent intent;
                    intent = new Intent(v.getContext(), ActivitiesActivity.class);
//                    Bundle args = new Bundle();
//                    args.putSerializable("DRAWERS",(Serializable) drawers);
//                    args.putInt("POSITION", 0);
//                    intent.putExtra("BUNDLE",args);
                    startActivity(intent);
                }else{
                    activityNameEditText.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}


