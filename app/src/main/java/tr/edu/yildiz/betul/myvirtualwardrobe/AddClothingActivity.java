package tr.edu.yildiz.betul.myvirtualwardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class AddClothingActivity extends AppCompatActivity {

    private EditText type, color, pattern, date, cost;
    private ImageButton addButton, returnButton;
    private Button saveClothingButton;
    private Clothing clothing;
    Uri URI = null;
    private Drawer drawer;
    private int position;
    private String imagePath;
    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothing);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        position = args.getInt("POSITION");
        drawer = (Drawer) args.getSerializable("DRAWER");
        defineVariables();
        defineListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == 101 && resultCode == RESULT_OK ){
            URI = data.getData();
            addButton.setImageURI(URI);
            Toast.makeText(AddClothingActivity.this, "File attached", Toast.LENGTH_SHORT).show();
        }
    }
    private void defineListeners() {
        saveClothingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (URI == null){
                    Toast.makeText(AddClothingActivity.this, "Please add an image", Toast.LENGTH_SHORT).show();
                }else{
                    createClothing();
                    saveDrawerToFileSerializable();
                    try {
                        saveImage();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra("return_data", true);
                startActivityForResult(Intent.createChooser(intent, "complete action using"), 101);
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(v.getContext(), DrawersActivity.class);
//                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
    }

    private void saveDrawerToFileSerializable() {
        ArrayList<Drawer> drawers;
        drawers = SerializableManager.readSerializable(AddClothingActivity.this, MainActivity.DRAWERS_FILE_NAME);
        drawers.set(position, drawer);
        SerializableManager.saveSerializable(AddClothingActivity.this, drawers, MainActivity.DRAWERS_FILE_NAME, false);
        Toast.makeText(AddClothingActivity.this, "Clothing is saved", Toast.LENGTH_SHORT).show();
    }

    private void defineVariables() {
        imagePath = "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/images"
                + "/"
                + drawer.getName();
        type = (EditText)findViewById(R.id.clothingType);
        color = (EditText)findViewById(R.id.clothingColor);
        pattern = (EditText)findViewById(R.id.clothingPattern);
        date = (EditText)findViewById(R.id.clothingDate);
        cost = (EditText)findViewById(R.id.clothingCost);
        addButton = (ImageButton)findViewById(R.id.imageButtonAddClothing);
        returnButton = (ImageButton)findViewById(R.id.imageButtonReturn);
        saveClothingButton = (Button)findViewById(R.id.buttonSave);
    }

    public void createClothing() {
        if (!checkClothingExists(URI, drawer.getClothes())){
            uri = URI.getLastPathSegment();
            clothing = new Clothing(uri, imagePath, type.getText().toString(), color.getText().toString(), pattern.getText().toString(), date.getText().toString(), cost.getText().toString(), drawer.getName());
            drawer.getClothes().add(clothing);
        }else
            Toast.makeText(AddClothingActivity.this, "This clothing image already exists in the drawer", Toast.LENGTH_SHORT).show();
    }
    public boolean checkClothingExists(Uri URI, ArrayList<Clothing> clothes){
        for (Clothing clothing : clothes){
            if ( clothing.getImagePath().equals(URI.getLastPathSegment()) ){
                return true;
            }
        }

        return false;
    }

    public File saveImage() throws FileNotFoundException {
        if (URI != null) {
            File picName = getOutputMediaFile();
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), URI);
                image = Bitmap.createScaledBitmap(image,200,200, true);
                FileOutputStream fos = new FileOutputStream(picName);
                image.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
                return picName;
            } catch (FileNotFoundException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    private File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/images"
                + "/"
                + clothing.getDrawerName()
        );

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + URI.getLastPathSegment() + ".jpg" );
        return mediaFile;
    }

}



