package tr.edu.yildiz.betul.myvirtualwardrobe;

import android.net.Uri;

import java.io.Serializable;

public class Outfit implements Serializable {
    private String name;
    private String head;
    private String face;
    private String top;
    private String bottom;
    private String shoes;

    public Outfit(String name, String head, String face, String top, String bottom, String shoes) {
        this.head = head;
        this.face = face;
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public void setShoes(String shoes) {
        this.shoes = shoes;
    }

    public String getHead() {
        return head;
    }

    public String getFace() {
        return face;
    }

    public String getTop() {
        return top;
    }

    public String getBottom() {
        return bottom;
    }

    public String getShoes() {
        return shoes;
    }
}
