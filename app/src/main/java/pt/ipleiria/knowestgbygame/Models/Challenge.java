package pt.ipleiria.knowestgbygame.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Challenge implements Serializable {

    @SerializedName("Title")
    private  String title;

    @SerializedName("Description")
    private  String description;

    @SerializedName("Thumbnail")
    private int thumbnail;

    @SerializedName("time")
    private long time;

    @SerializedName("Sugestion")
    private List<Sugestion> sugestions;

    @SerializedName("answerType")
    private AnswerType answerType;

    public Challenge(String title, String description, int thumbnail, long time, List<Sugestion> sugestions, AnswerType answerType) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.time = time;
        this.sugestions = sugestions;
        this.answerType = answerType;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<Sugestion> getSugestions() {
        return sugestions;
    }

    public void setSugestions(List<Sugestion> sugestions) {
        this.sugestions = sugestions;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }
}
