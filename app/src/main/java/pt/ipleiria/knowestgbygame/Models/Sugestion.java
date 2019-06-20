package pt.ipleiria.knowestgbygame.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sugestion implements Serializable{

    @SerializedName("Description")
    private String description;

    @SerializedName("Thumbnail")
    private int thumbnail;

    public Sugestion(String description, int thumbnail) {
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Sugestion(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Sugestion(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
