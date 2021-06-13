package tr.edu.yildiz.betul.myvirtualwardrobe;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class Drawer implements Serializable {
    private String name;
    private ArrayList<Clothing> clothes;
    private ArrayList<Uri> images;
    private ArrayList<String> uris;
//    private ArrayList<Drawer> drawers;

    public Drawer(String name) {
        this.name = name;
        this.clothes = new ArrayList<>();
    }

    public void addClothing( Clothing clothing ){
        if (clothes == null){
            clothes = new ArrayList<>();
        }
        clothes.add(clothing);
    }

    public ArrayList<Clothing> getClothes() {
        return clothes;
    }
    public ArrayList<String> getClothesAsString(){
        ArrayList<String> uris = new ArrayList<>();
        for (Uri uri : this.images) {
            uris.add(uri.toString());
        }
        return uris;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getUris() {
        return uris;
    }
    //    public ArrayList<Drawer> getDrawers() {
//        return drawers;
//    }


}
