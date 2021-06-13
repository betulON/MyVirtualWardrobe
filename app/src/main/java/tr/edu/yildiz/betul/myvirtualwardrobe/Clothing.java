package tr.edu.yildiz.betul.myvirtualwardrobe;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.net.URL;
import java.time.chrono.IsoEra;
import java.util.Date;

public class Clothing implements Serializable {
    private String uri; //use as clothing id
    private String type;
    private String color;
    private String pattern;
//    private Date date;
    private String date;
    private String cost;
    private String drawerName;
    private String imagePath;
    private String activityName;


    public Clothing(String uri, String imagePath, String type, String color, String pattern, String  date, String cost, String drawerName) {
        this.uri = uri;
        this.imagePath = imagePath;
        this.type = type;
        this.color = color;
        this.pattern = pattern;
        this.date = date;
        this.cost = cost;
        this.drawerName = drawerName;
    }
    public Clothing(String uri, String imagePath, String type, String color, String pattern, String  date, String cost, String drawerName, String activityName) {
        this.uri = uri;
        this.imagePath = imagePath;
        this.type = type;
        this.color = color;
        this.pattern = pattern;
        this.date = date;
        this.cost = cost;
        this.drawerName = drawerName;
        this.activityName = activityName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getImagePath() {
        return imagePath;
    }
    public String getUri(){ return uri; }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public String getPattern() {
        return pattern;
    }

    public String getDate() {
        return date;
    }

    public String getCost() {
        return cost;
    }

    public String getDrawerName() {
        return drawerName;
    }


}
