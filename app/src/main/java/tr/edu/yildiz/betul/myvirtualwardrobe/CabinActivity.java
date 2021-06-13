package tr.edu.yildiz.betul.myvirtualwardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CabinActivity extends AppCompatActivity {

    private ImageButton head, face, top, bottom, shoes;
    private Button saveOutfitButton, deleteOutfitButton, getOutFitButton, returnButton;
    private Clothing clothing;
    private Outfit outfit;
    private ArrayList<Outfit> outfits;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabin);
        defineVariables();
        defineListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ChooseClothesActivity.RESULT_OK){
            Bundle args = data.getBundleExtra("BUNDLE");
            clothing = (Clothing) args.getSerializable("CLOTHING");
            String path = MainActivity.IMAGES_FILE_PATH + clothing.getDrawerName() + "/"+ clothing.getUri() + ".jpg";
            File imgFile = new File(path);
            Bitmap bitmap = null;
            if(imgFile.exists()){
                bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            }
            if (bitmap != null) {
                if (requestCode == 1) {
                    head.setImageBitmap(bitmap);
                    outfit.setHead(path);
                } else if (requestCode == 2) {
                    face.setImageBitmap(bitmap);
                    outfit.setFace(path);
                } else if (requestCode == 3) {
                    top.setImageBitmap(bitmap);
                    outfit.setTop(path);
                } else if (requestCode == 4) {
                    bottom.setImageBitmap(bitmap);
                    outfit.setBottom(path);
                } else {
                    shoes.setImageBitmap(bitmap);
                    outfit.setShoes(path);
                }
            }
        }
    }
    private void defineVariables() {
        outfits = SerializableManager.readSerializable(CabinActivity.this, MainActivity.OUTFITS_FILE_NAME);
        if (outfits == null){
            outfits = new ArrayList<>();
        }
        outfit = new Outfit(null, null, null, null,null,null);
        name = (EditText)findViewById(R.id.editTextMyCabinName);
        head = (ImageButton)findViewById(R.id.imageButtonMyCabinHead);
        face = (ImageButton)findViewById(R.id.imageButtonMyCabinFace);
        top = (ImageButton)findViewById(R.id.imageButtonMyCabinTop);
        bottom = (ImageButton)findViewById(R.id.imageButtonMyCabinBottom);
        shoes = (ImageButton)findViewById(R.id.imageButtonMyCabinShoes);
        saveOutfitButton = (Button) findViewById(R.id.myCabinetButtonSave);
        returnButton = (Button)findViewById(R.id.myCabinetButtonReturn);
        deleteOutfitButton = (Button)findViewById(R.id.myCabinetButtonDelete);
        getOutFitButton = (Button)findViewById(R.id.myCabinetButtonGet);

    }

    public void saveOutfitToFile() {
        if (outfits == null){
            outfits = new ArrayList<>();
        }
        String name = this.name.getText().toString();
        if (isOutfitExists(name, outfits) == null){
            outfit.setName(name);
            outfits.add(outfit);
            SerializableManager.saveSerializable(CabinActivity.this, outfits, MainActivity.OUTFITS_FILE_NAME, false);
            Toast.makeText(CabinActivity.this, outfit.getName() + " is saved", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(CabinActivity.this, "Outfit is already exist", Toast.LENGTH_SHORT).show();

    }
    public Outfit isOutfitExists(String name, ArrayList<Outfit> outfits){
        for (Outfit outfit : outfits){
            if (outfit.getName().equals(name)){
                return outfit;
            }
        }
        return null;
    }
    public void deleteOutfit(){
        Outfit outfit = isOutfitExists(this.name.getText().toString(), this.outfits);
        if (outfit != null){
            outfits.remove(outfit);
            SerializableManager.saveSerializable(CabinActivity.this, outfits, MainActivity.OUTFITS_FILE_NAME, false);
            Toast.makeText(CabinActivity.this, outfit.getName() + " is deleted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(CabinActivity.this, "No outfit exists in the given name", Toast.LENGTH_SHORT).show();
        }

    }
    public void getOutfit(){
        Outfit outfit = isOutfitExists(this.name.getText().toString(), this.outfits);
        if (outfit != null){
            setView(outfit.getHead(), head);
            setView(outfit.getFace(), face);
            setView(outfit.getTop(), top);
            setView(outfit.getBottom(), bottom);
            setView(outfit.getShoes(), shoes);
            Toast.makeText(CabinActivity.this, outfit.getName() + " is showed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(CabinActivity.this, "No outfit exists in the given name", Toast.LENGTH_SHORT).show();
        }

    }
    public void setView(String path, ImageButton imButton){
        File imgFile = new File(path);
        if(imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imButton.setImageBitmap(bitmap);
        }
    }


    private void defineListeners() {
        saveOutfitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOutfitToFile();
            }
        });
        getOutFitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOutfit();
            }
        });
        deleteOutfitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertUser();
//                deleteOutfit();
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int LAUNCH_SECOND_ACTIVITY = 1;
                Intent intent;
                intent = new Intent(v.getContext(), ChooseClothesActivity.class);
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            }
        });
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int LAUNCH_SECOND_ACTIVITY = 2;
                Intent intent;
                intent = new Intent(v.getContext(), ChooseClothesActivity.class);
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            }
        });
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int LAUNCH_SECOND_ACTIVITY = 3;
                Intent intent;
                intent = new Intent(v.getContext(), ChooseClothesActivity.class);
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            }
        });
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int LAUNCH_SECOND_ACTIVITY = 4;
                Intent intent;
                intent = new Intent(v.getContext(), ChooseClothesActivity.class);
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int LAUNCH_SECOND_ACTIVITY = 5;
                Intent intent;
                intent = new Intent(v.getContext(), ChooseClothesActivity.class);
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            }
        });
    }
    private void alertUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CabinActivity.this);
        builder.setMessage("Are you sure you want to delete this outfit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteOutfit();
                Intent intent;
                intent = new Intent(CabinActivity.this, CabinActivity.class);
                CabinActivity.this.startActivity(intent);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}