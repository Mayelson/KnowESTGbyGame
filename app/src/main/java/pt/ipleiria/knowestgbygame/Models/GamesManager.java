package pt.ipleiria.knowestgbygame.Models;

import java.util.ArrayList;

import pt.ipleiria.knowestgbygame.R;

public class GamesManager {

    private ArrayList<Game> games;

    private static final GamesManager instance = new GamesManager();

    public static GamesManager manager() {
        return instance;
    }

    private GamesManager() {
        games = new ArrayList<>();
        buildExemplesGames();
    }

    private void buildExemplesGames() {
       games = new ArrayList<>();

        ArrayList<Challenge> challenges1 = new ArrayList<>();
        ArrayList<Challenge> challenges2 = new ArrayList<>();
        ArrayList<Challenge> challenges3 = new ArrayList<>();
        ArrayList<Challenge> challenges4 = new ArrayList<>();
        ArrayList<Challenge> challenges5 = new ArrayList<>();


        challenges1.add(ChallengesManager.manager().getChallenges().get(0));
        challenges1.add(ChallengesManager.manager().getChallenges().get(1));

        challenges2.add(ChallengesManager.manager().getChallenges().get(2));
        challenges2.add(ChallengesManager.manager().getChallenges().get(3));
        challenges2.add(ChallengesManager.manager().getChallenges().get(4));

        challenges3.add(ChallengesManager.manager().getChallenges().get(5));
        challenges3.add(ChallengesManager.manager().getChallenges().get(6));
        challenges3.add(ChallengesManager.manager().getChallenges().get(7));

        challenges4.add(ChallengesManager.manager().getChallenges().get(8));

        challenges5.add(ChallengesManager.manager().getChallenges().get(9));


        games.add(new Game("Jogo 1", "Este jogo permite aos jogadores conhecerem as salas do edificio A ", challenges1, "Admin"));
        games.add(new Game("Jogo 2", "Este jogo, permite aos jogadores conherem os bares da ESTG", challenges2, "Admin"));
        games.add(new Game("Jogo 3", "Este jogo permite aos jogadores conhecerem a Biblioteca José saramago", challenges3, "Admin"));
        games.add(new Game("Jogo 4", "Conheça o logotipo do IPL com este jogo", challenges4, "Admin"));
        games.add(new Game("Jogo 5", "Mostre felicidade dos alunos do IPL", challenges5, "Admin"));
        for (Game game: games) {
            game.setThumbnail(R.drawable.ic_launcher_foreground);
        }
    }



    public void addGame(Game game) {
        games.add(game);
    }

    public void addGameAtPosition(int position, Game game) {
        games.add(position, game);
    }


    public void removeGame(Game game) {
        games.remove(game);
    }

    public void removeGameAtPosition(int position) {
        games.remove(position);
    }


    public ArrayList<Game> getGames() {
        return games;
    }


    public ArrayList<Game> getGamesWithChallenges() {
        ArrayList<Game> list = new ArrayList<>();
        for (Game game: games) {
            if (game.getChallenges().size() > 0){
                list.add(game);
            }
        }
        return list;
    }

    public Game getGamesByUid(String uid) {
        for (Game game: games) {
            if (game.getUuid().equalsIgnoreCase(uid)){
                return game;
            }
        }
        return null;
    }

}
