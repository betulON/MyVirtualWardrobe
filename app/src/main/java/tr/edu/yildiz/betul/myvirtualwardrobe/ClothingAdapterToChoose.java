package tr.edu.yildiz.betul.myvirtualwardrobe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class ClothingAdapterToChoose extends RecyclerView.Adapter<ClothingAdapterToChoose.ViewHolder>{
    private ArrayList<Clothing> clothes;
    private Context context;

    public ClothingAdapterToChoose(Context context, ArrayList<Clothing> clothes) {
        this.clothes = clothes;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothing_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Clothing clothing = clothes.get(position);
        String path = MainActivity.IMAGES_FILE_PATH + clothing.getDrawerName() + "/"+ clothing.getUri() + ".jpg";
        File imgFile = new File(path);
        if(imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.image.setImageBitmap(bitmap);
        }
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = ((ChooseClothesActivity)context).getIntent();
                Bundle args = new Bundle();
                args.putSerializable("CLOTHING",(Serializable) clothing);
                returnIntent.putExtra("BUNDLE", args);
                ((ChooseClothesActivity)context).setResult(CabinActivity.RESULT_OK, returnIntent);
                ((ChooseClothesActivity)context).finish();

            }
        });
    }
    @Override
    public int getItemCount() {
        return clothes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.clothing_image_view);
        }
    }
}

