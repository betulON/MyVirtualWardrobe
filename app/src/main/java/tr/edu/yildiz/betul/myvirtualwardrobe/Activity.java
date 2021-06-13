package tr.edu.yildiz.betul.myvirtualwardrobe;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class Activity implements Serializable {
    private String name;
    private String type;
    private String date;
    private String location;
    private ArrayList<Clothing> clothes;

//    public Activity(String name, String) {
//        this.name = name;
//        this.clothes = new ArrayList<>();
//    }

    public Activity(String name, String type, String date, String location, ArrayList<Clothing> clothes) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.location = location;
        if (clothes == null){
            clothes = new ArrayList<>();
        }
        this.clothes = clothes;
    }

    public void addClothing(Clothing clothing ){
        if (clothes == null){
            clothes = new ArrayList<>();
        }
        clothes.add(clothing);
    }

    public ArrayList<Clothing> getClothes() {
        if (this.clothes == null){
            this.clothes = new ArrayList<>();
        }
        return clothes;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setClothes(ArrayList<Clothing> clothes) {
        this.clothes = clothes;
    }
}
