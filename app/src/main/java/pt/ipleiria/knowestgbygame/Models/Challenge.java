package pt.ipleiria.knowestgbygame.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import pt.ipleiria.knowestgbygame.R;

public class Challenge implements Serializable {

    @SerializedName("Title")
    private  String title;

    @SerializedName("Description")
    private  String description;

    @SerializedName("Thumbnail")
    private int thumbnail;

    @SerializedName("time")
    private long time;

    @SerializedName("Suggestion")
    private String suggestion;

    @SerializedName("answerType")
    private AnswerType answerType;

    @SerializedName("points")
    private long points;

    @SerializedName("Answer")
    private String answer;

    public Challenge(String title, String description, long time, AnswerType answerType, long points) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.answerType = answerType;
        this.points = points;
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


    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
