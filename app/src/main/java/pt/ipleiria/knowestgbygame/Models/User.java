package pt.ipleiria.knowestgbygame.Models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String email;
    private String avatar;
    private int points;
    private  List<Game> games;
    private String uid;


    public User(String name, String email, String avatar, String uid) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.points = 0;
        this.games = new ArrayList<>();
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
