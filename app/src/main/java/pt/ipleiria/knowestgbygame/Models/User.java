package pt.ipleiria.knowestgbygame.Models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String email;
    private String avatar;
    private int points;
    private  List<Game> gamesPlayeds;
    private String uid;


    public User(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.points = 0;
        this.gamesPlayeds = new ArrayList<>();
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

    public List<Game> getGamesPlayeds() {
        return gamesPlayeds;
    }

    public void setGamesPlayeds(List<Game> games) {
        this.gamesPlayeds = games;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public void addGamePlayed(Game game){
        gamesPlayeds.add(game);
    }

    public boolean alreadyPlayed(Game game){
        return  gamesPlayeds.contains(game) ? true : false;
     /*   for (Game g: gamesPlayeds) {
            if (g.getUuid() == game.getUuid()){
                return true;
            }
        }
        return false;*/
    }
}
