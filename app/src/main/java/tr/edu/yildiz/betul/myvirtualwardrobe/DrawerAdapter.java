package tr.edu.yildiz.betul.myvirtualwardrobe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.Serializable;
import java.util.ArrayList;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    ArrayList<Drawer> drawers;

    public DrawerAdapter(Context context, ArrayList<Drawer> drawers) {
        this.context = context;
        this.drawers = drawers;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageButton addButton;
        ImageButton deleteButton;
        RecyclerView clothingItem;
        TextView drawerName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.clothingItem = itemView.findViewById(R.id.sub_clothing_item);
            this.drawerName = itemView.findViewById(R.id.textViewDrawerName);
            this.deleteButton = itemView.findViewById(R.id.drawerCardDeleteButton);
            this.addButton = itemView.findViewById(R.id.drawerCardAddButton);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(context).inflate(R.layout.drawer_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Drawer drawer = drawers.get(position);
        holder.drawerName.setText(drawer.getName());
        // Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.clothingItem.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(drawer.getClothes().size());

        // Create sub item view adapter
        ArrayList<Clothing> clothes = drawer.getClothes();
        if (clothes.size() != 0){
            ClothingAdapter clothingAdapter = new ClothingAdapter(context, clothes);
            holder.clothingItem.setLayoutManager(layoutManager);
            holder.clothingItem.setAdapter(clothingAdapter);
            holder.clothingItem.setRecycledViewPool(viewPool);
        }

        holder.addButton.setOnClickListener((v) -> {
            Intent intent;
            intent = new Intent(context, AddClothingActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("DRAWERS",(Serializable) drawers);
            args.putInt("POSITION", position);
            args.putSerializable("DRAWER",(Serializable) drawer);
            intent.putExtra("BUNDLE",args);
            context.startActivity(intent);
//            @Override
//            public void onClick(View v) {
//                requestcode = position;
//                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                activity.startActivityForResult(pickPhoto, 1);
//            }

//            Toast.makeText(context, drawers.get(position).getAttachmentPath(), Toast.LENGTH_SHORT).show();
        });
        holder.deleteButton.setOnClickListener((v) -> {
            alertUser(position);

        });

    }

    private void alertUser(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete this drawer?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                drawers.remove(position);
                SerializableManager.saveSerializable(context, drawers, MainActivity.DRAWERS_FILE_NAME, false);
                Intent intent;
                intent = new Intent(context, DrawersActivity.class);
//                intent.putExtra("userName", userName);
                context.startActivity(intent);
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



    @Override
    public int getItemCount() {
        return drawers.size();
    }


}