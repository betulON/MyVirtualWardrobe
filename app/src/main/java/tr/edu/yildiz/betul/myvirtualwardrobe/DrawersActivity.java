package tr.edu.yildiz.betul.myvirtualwardrobe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class DrawersActivity extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Drawer> drawers;
    RecyclerView recyclerView;
    DrawerAdapter drawerAdapter;
    private ImageButton returnButton, addButton;
    EditText drawerNameEditText;
//    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
//        Intent intent = getIntent();
//        Bundle args = intent.getBundleExtra("BUNDLE");
//        position = args.getInt("POSITION");
//        drawers = (ArrayList<Drawer>) args.getSerializable("DRAWERS");
        defineVariables();
        defineListeners();
    }

    private void defineVariables() {
        returnButton = findViewById(R.id.imageButtonMyDrawersReturn);
        addButton = findViewById(R.id.imageButtonMyDrawersAdd);
        drawerNameEditText = findViewById(R.id.editTextMyDrawers);
        drawerNameEditText.setVisibility(View.INVISIBLE);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        drawers = SerializableManager.readSerializable(DrawersActivity.this, MainActivity.DRAWERS_FILE_NAME);
        if (drawers == null){
            Toast.makeText(DrawersActivity.this, "No drawers", Toast.LENGTH_SHORT).show();
            drawers = new ArrayList<>();
        }else{
            drawerAdapter = new DrawerAdapter(this, drawers);
            recyclerView.setAdapter(drawerAdapter);
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
                if (drawerNameEditText.getVisibility() == View.VISIBLE){
                    drawerNameEditText.setVisibility(View.INVISIBLE);
                    Drawer drawer = new Drawer(drawerNameEditText.getText().toString());
                    drawers.add(drawer);
                    SerializableManager.saveSerializable(DrawersActivity.this, drawers, MainActivity.DRAWERS_FILE_NAME, false);
                    Intent intent;
                    intent = new Intent(v.getContext(), DrawersActivity.class);
//                    Bundle args = new Bundle();
//                    args.putSerializable("DRAWERS",(Serializable) drawers);
//                    args.putInt("POSITION", 0);
//                    intent.putExtra("BUNDLE",args);
                    startActivity(intent);
                }else{
                    drawerNameEditText.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

