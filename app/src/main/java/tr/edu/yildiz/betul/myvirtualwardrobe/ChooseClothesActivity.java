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

import java.util.ArrayList;

public class ChooseClothesActivity extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Drawer> drawers;
    RecyclerView recyclerView;
    DrawerAdapterToChoose drawerAdapter;
    private ImageButton returnButton, addButton;
    EditText drawerNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_clothes);
        defineVariables();
        defineListeners();
    }


    private void defineVariables() {
        returnButton = findViewById(R.id.imageButtonChooseClothesReturn);
        recyclerView = findViewById(R.id.recyclerViewChooseClothes);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        drawers = SerializableManager.readSerializable(ChooseClothesActivity.this, MainActivity.DRAWERS_FILE_NAME);
        if (drawers == null){
            Toast.makeText(ChooseClothesActivity.this, "No drawers", Toast.LENGTH_SHORT).show();
            drawers = new ArrayList<>();
        }else{
            drawerAdapter = new DrawerAdapterToChoose(this, drawers);
            recyclerView.setAdapter(drawerAdapter);
        }
    }

    private void defineListeners() {
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
//                setResult(ChooseClothesActivity.RESULT_CANCELED, returnIntent);
                setResult(CabinActivity.RESULT_CANCELED, returnIntent);
                finish();
//                returnIntent = new Intent(v.getContext(), CabinActivity.class);
            }
        });

    }
}