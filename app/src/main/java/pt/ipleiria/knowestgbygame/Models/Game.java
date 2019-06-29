package pt.ipleiria.knowestgbygame.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game implements Serializable {

    @SerializedName("Title")
    private  String title;

    @SerializedName("Description")
    private  String description;

    @SerializedName("Thumbnail")
    private int thumbnail;

    @SerializedName("score")
    private int score;

    @SerializedName("Challenges")
    private ArrayList<Challenge> challenges;

    @SerializedName("Created_by")
    private String created_by;

    @SerializedName("UUID")
    private String uuid;

    public Game(String title, String description, ArrayList<Challenge> challenges, String created_by) {
        this.title = title;
        this.description = description;
        this.challenges = challenges;
        this.created_by = created_by;
        this.uuid = UUID.randomUUID().toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(ArrayList<Challenge> challenges) {
        this.challenges = challenges;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
