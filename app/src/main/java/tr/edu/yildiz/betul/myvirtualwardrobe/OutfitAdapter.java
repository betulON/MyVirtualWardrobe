package tr.edu.yildiz.betul.myvirtualwardrobe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class OutfitAdapter extends RecyclerView.Adapter<OutfitAdapter.MyViewHolder> {

    Context context;
    ArrayList<Outfit> outfits;

    public OutfitAdapter(Context context, ArrayList<Outfit> outfits) {
        this.context = context;
        this.outfits = outfits;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView outfitName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.outfitName = itemView.findViewById(R.id.textViewOutfitName);
//            outfitName.setOnTouchListener((ShareOutfitActivity) context);
        }
    }

    @Override
    public OutfitAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(context).inflate(R.layout.outfit_item, parent, false);
        OutfitAdapter.MyViewHolder holder = new OutfitAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OutfitAdapter.MyViewHolder holder, int position) {
        Outfit outfit = outfits.get(position);
        holder.outfitName.setText(outfit.getName());

        holder.outfitName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendImage(outfit);
            }
        });
    }
    @Override
    public int getItemCount() {
        return outfits.size();
    }

    public void addToPaths(ArrayList<String> paths, String path){
        File imgFile = new File(path);
        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String newPath = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        paths.add(newPath);
    }
    public void sendImage(Outfit outfit){
        ArrayList<String> paths = new ArrayList<>();
        addToPaths(paths, outfit.getHead());
        addToPaths(paths, outfit.getFace());
        addToPaths(paths, outfit.getTop());
        addToPaths(paths, outfit.getBottom());
        addToPaths(paths, outfit.getShoes());

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Some images.");
        intent.setType("image/*");

        ArrayList<Uri> files = new ArrayList<>();

        for(String newPath : paths) {
            Uri uri = Uri.parse(newPath);
            files.add(uri);
        }

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
        context.startActivity(intent);
    }
}

