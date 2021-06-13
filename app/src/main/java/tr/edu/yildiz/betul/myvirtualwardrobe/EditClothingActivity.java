package tr.edu.yildiz.betul.myvirtualwardrobe;

import androidx.appcompat.app.AppCompatActivity;
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

public class EditClothingActivity extends AppCompatActivity {

    private EditText type, color, pattern, date, cost;
    private ImageButton addButton, returnButton;
    private Button saveClothingButton, deleteButton;
    private Clothing clothing;
    Uri URI = null;
    private Drawer drawer;
    private int position;
    private String imagePath;
    private String uri;
    private ArrayList<Clothing> clothes;
    private ArrayList<Drawer> drawers;


    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_clothing);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        position = args.getInt("POSITION");
        clothes = (ArrayList<Clothing>) args.getSerializable("CLOTHES");
        clothing = clothes.get(position);
        defineVariables();
        defineListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == 101 && resultCode == RESULT_OK ){
            URI = data.getData();
            addButton.setImageURI(URI);
            Toast.makeText(EditClothingActivity.this, "Image attached", Toast.LENGTH_SHORT).show();
        }
    }
    private void defineListeners() {
        saveClothingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readDrawersFromFileSerializable();
                createClothing();
                saveDrawersToFileSerializable();
                try {
                    saveImage();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
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
                startActivity(intent);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readDrawersFromFileSerializable();
                deleteClothing();
                saveDrawersToFileSerializable();
            }
        });
    }

    private void readDrawersFromFileSerializable(){
        drawers = SerializableManager.readSerializable(EditClothingActivity.this, MainActivity.DRAWERS_FILE_NAME);
        for (Drawer drawer : drawers){
            if (drawer.getName().equals(clothing.getDrawerName())){
                System.out.println("yep");
                this.drawer = drawer;
                break;
            }
        }

    }

    private void saveDrawersToFileSerializable() {
//        for (Drawer drawer : drawers){
//            if (drawer.getName().equals(clothing.getDrawerName())){
//                drawer.getClothes().set(position, clothing);
//            }
//        }
        SerializableManager.saveSerializable(EditClothingActivity.this, drawers, MainActivity.DRAWERS_FILE_NAME, false);
        Toast.makeText(EditClothingActivity.this, "Clothing is updated", Toast.LENGTH_SHORT).show();
    }

    private void defineVariables() {
        imagePath = "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/images"
                + "/"
                + clothing.getDrawerName();
        type = (EditText)findViewById(R.id.clothingType);
        color = (EditText)findViewById(R.id.clothingColor);
        pattern = (EditText)findViewById(R.id.clothingPattern);
        date = (EditText)findViewById(R.id.clothingDate);
        cost = (EditText)findViewById(R.id.clothingCost);
        addButton = (ImageButton)findViewById(R.id.imageButtonAddClothing);
        returnButton = (ImageButton)findViewById(R.id.imageButtonReturn);
        saveClothingButton = (Button)findViewById(R.id.buttonSave);
        deleteButton = (Button)findViewById(R.id.buttonDelete);

        type.setText(clothing.getType());
        color.setText(clothing.getColor());
        pattern.setText(clothing.getPattern());
        date.setText(clothing.getDate());
        cost.setText(clothing.getCost());

        String path = "sdcard/Android/data/tr.edu.yildiz.betul.myvirtualwardrobe/images/" + clothing.getDrawerName() + "/"+ clothing.getUri() + ".jpg";
        File imgFile = new File(path);
        if(imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            addButton.setImageBitmap(bitmap);
        }
    }

    public void createClothing() {
//        if (!checkClothingExists(URI, drawer.getClothes())){
        clothing = new Clothing(clothing.getUri(), clothing.getImagePath(), type.getText().toString(), color.getText().toString(), pattern.getText().toString(), date.getText().toString(), cost.getText().toString(), clothing.getDrawerName());
        drawer.getClothes().set(position, clothing);

    }
//    public boolean checkClothingExists(Uri URI, ArrayList<Clothing> clothes){
//        for (Clothing clothing : clothes){
//            if ( clothing.getUri().equals(URI.getLastPathSegment()) ){
//                return true;
//            }
//        }
//
//        return false;
//    }
    public void deleteClothing() {
        drawer.getClothes().remove(position);
        if (!drawer.getClothes().contains(clothing)){
            Toast.makeText(EditClothingActivity.this, "This clothing is deleted", Toast.LENGTH_SHORT).show();
        }
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



