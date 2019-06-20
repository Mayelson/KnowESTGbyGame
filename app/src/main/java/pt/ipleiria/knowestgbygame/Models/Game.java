package pt.ipleiria.knowestgbygame.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private List<Challenge> challenges;

    @SerializedName("Created_by")
    private String created_by;

    public Game(String title, String description, int thumbnail, List<Challenge> challenges, String created_by, int score) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.score = score;
        this.challenges = challenges;
        this.created_by = created_by;
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

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
}
