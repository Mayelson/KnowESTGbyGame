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


        challenges1.add(ChallengesManager.manager().getChallenges().get(0));
        challenges1.add(ChallengesManager.manager().getChallenges().get(1));

        challenges2.add(ChallengesManager.manager().getChallenges().get(2));
        challenges2.add(ChallengesManager.manager().getChallenges().get(3));
        challenges2.add(ChallengesManager.manager().getChallenges().get(4));

        challenges3.add(ChallengesManager.manager().getChallenges().get(5));
        challenges3.add(ChallengesManager.manager().getChallenges().get(6));


       // challenges1.add(new Challenge("Edifícios da ESTG", "Quantos edifícios tem ESTG?", 30000, AnswerType.NUMBER, 10));

     /*   challenges2.add(new Challenge("Biblioteca ESTG", "Como se chama a biblioteca da ESTG (Campus 2)?", 60000, AnswerType.TEXT, 20));
        challenges2.add(new Challenge("Ler QRCode", "Faça a leitura do QrCode na sala 2.04", 120000, AnswerType.QRCODE, 100));
        challenges2.add(new Challenge("Bares da ESTG", "Quantos Bares têm a ESTG?", 30000, AnswerType.NUMBER, 30));

        challenges3.add(new Challenge("Cursos ESTG 2019", "Quantos cursos têm na ESTG este Ano?", 60000, AnswerType.NUMBER, 20));
        challenges3.add(new Challenge("Departamentos ESTG", "Quantos departamentos tem a ESTG?", 60000, AnswerType.NUMBER, 100));*/


        games.add(new Game("Jogo 1", "Este jogo permite aos jogadores conhecerem as salas do edificio A ", challenges1, "Msousa"));
        games.add(new Game("Jogo 2", "Este jogo, permite aos jogadores conherem os bares da ESTG", challenges2, "CSants"));
        games.add(new Game("Jogo 3", "Este jogo permite aos jogadores conhecerem a Biblioteca José saramago", challenges3, "Admin1"));
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

}
