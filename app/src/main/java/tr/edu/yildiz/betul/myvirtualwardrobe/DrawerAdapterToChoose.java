package tr.edu.yildiz.betul.myvirtualwardrobe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DrawerAdapterToChoose extends RecyclerView.Adapter<DrawerAdapterToChoose.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    ArrayList<Drawer> drawers;

    public DrawerAdapterToChoose(Context context, ArrayList<Drawer> drawers) {
        this.context = context;
        this.drawers = drawers;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView clothingItem;
        TextView drawerName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.clothingItem = itemView.findViewById(R.id.sub_clothing_item2);
            this.drawerName = itemView.findViewById(R.id.textViewDrawerName2);
        }
    }

    @Override
    public DrawerAdapterToChoose.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(context).inflate(R.layout.drawer_item2, parent, false);
        DrawerAdapterToChoose.MyViewHolder holder = new DrawerAdapterToChoose.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerAdapterToChoose.MyViewHolder holder, int position) {
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
            ClothingAdapterToChoose clothingAdapter = new ClothingAdapterToChoose(context, clothes);
            holder.clothingItem.setLayoutManager(layoutManager);
            holder.clothingItem.setAdapter(clothingAdapter);
            holder.clothingItem.setRecycledViewPool(viewPool);
        }

    }
    @Override
    public int getItemCount() {
        return drawers.size();
    }

}
