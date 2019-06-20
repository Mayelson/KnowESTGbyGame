package pt.ipleiria.knowestgbygame.Models;

import java.util.ArrayList;

public class GameRepository {
    private static final GameRepository instance = new GameRepository();

    public static GameRepository getInstance() {
        return instance;
    }

    private ArrayList<Game> games;


    public void addGame(Game game) {
        games.add(game);
    }


    public ArrayList<Game> getGames() {
        return games;
    }


    public void deleteGame(Game game) {
        games.remove(games);
    }

}
