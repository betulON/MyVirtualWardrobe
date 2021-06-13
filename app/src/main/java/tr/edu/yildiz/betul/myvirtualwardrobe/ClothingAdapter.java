package tr.edu.yildiz.betul.myvirtualwardrobe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class ClothingAdapter extends RecyclerView.Adapter<ClothingAdapter.ViewHolder>
{
//    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Clothing> clothes;
    private Context context;

    public ClothingAdapter(Context context, ArrayList<Clothing> clothes) {
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

        String path = "sdcard/Android/data/tr.edu.yildiz.betul.myvirtualwardrobe/images/" + clothing.getDrawerName() + "/"+ clothing.getUri() + ".jpg";
        File imgFile = new File(path);
        if(imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.image.setImageBitmap(bitmap);
        }
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditClothingActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("CLOTHES",(Serializable) (ArrayList<Clothing>) clothes);
                args.putInt("POSITION", position);
                intent.putExtra("BUNDLE",args);
                context.startActivity(intent);
//                Log.d(TAG, "onClick: clicked on an image: " + mNames.get(position));
//                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
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




