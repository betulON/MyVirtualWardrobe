package tr.edu.yildiz.betul.myvirtualwardrobe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.MyViewHolder> {
        private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        Context context;
        ArrayList<Activity> activities;
        Clothing clothing;

    public ActivityAdapter(Context context, ArrayList<Activity> activities, Clothing clothing) {
            this.context = context;
            this.activities = activities;
            this.clothing = clothing;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageButton addButton;
        ImageButton deleteButton;
        ImageButton editButton;
        RecyclerView clothingItem;
        TextView activityName;
        EditText type, date, location;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.clothingItem = itemView.findViewById(R.id.sub_clothing_item);
            this.activityName = itemView.findViewById(R.id.textViewActivityName);
            this.deleteButton = itemView.findViewById(R.id.drawerCardActivityDeleteButton);
            this.addButton = itemView.findViewById(R.id.drawerCardActivityAddButton);
            this.editButton = itemView.findViewById(R.id.drawerCardActivityEditButton);
            this.type = itemView.findViewById(R.id.type);
            this.date = itemView.findViewById(R.id.date);
            this.location = itemView.findViewById(R.id.location);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Activity activity = activities.get(position);
        holder.activityName.setText(activity.getName());
        holder.type.setText(activity.getType());
        holder.location.setText(activity.getLocation());
        holder.date.setText(activity.getDate());
        // Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.clothingItem.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(activity.getClothes().size());

        // Create sub item view adapter
        ArrayList<Clothing> clothes = activity.getClothes();
        if (clothing != null){
            clothes.add(clothing);
        }
        if (clothes.size() != 0){
            ClothingAdapterToChoose clothingAdapter = new ClothingAdapterToChoose(context, clothes);
            holder.clothingItem.setLayoutManager(layoutManager);
            holder.clothingItem.setAdapter(clothingAdapter);
            holder.clothingItem.setRecycledViewPool(viewPool);
        }

        holder.addButton.setOnClickListener((v) -> {
            int LAUNCH_SECOND_ACTIVITY = 1;
            Intent intent;
            intent = new Intent(v.getContext(), ChooseClothesActivity.class);
            ((ActivitiesActivity) context).startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);

        });
        holder.editButton.setOnClickListener((v) -> {
            activities = SerializableManager.readSerializable(context, MainActivity.ACTIVITIES_FILE_NAME);
            for (Activity activity1 : activities){
                if (activity1.getName().equals(activity.getName())){
                    Activity activity2 = new Activity(activity.getName(), holder.type.getText().toString(),holder.date.getText().toString(),holder.location.getText().toString(), clothes);
                    activities.set(activities.indexOf(activity1), activity2);
                    SerializableManager.saveSerializable(context, activities, MainActivity.ACTIVITIES_FILE_NAME, false);
                }
            }

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
                activities.remove(position);
                SerializableManager.saveSerializable(context, activities, MainActivity.ACTIVITIES_FILE_NAME, false);
                Intent intent;
                intent = new Intent(context, ActivitiesActivity.class);
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
        return activities.size();
    }

}
