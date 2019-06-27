package pt.ipleiria.knowestgbygame.Models;

import java.util.ArrayList;

import pt.ipleiria.knowestgbygame.R;

public class ChallengesManager {
    private static final ChallengesManager instance = new ChallengesManager();

    public static ChallengesManager manager() {
        return instance;
    }

    private ArrayList<Challenge> challenges;

    private ChallengesManager() {
        challenges = new ArrayList<>();
        buildExemplesChallenges();

    }

    private void buildExemplesChallenges() {
       challenges = new ArrayList<>();

        challenges.add(new Challenge("Equipamento informático", "Tirar ma foto a um teclado.", 30000, AnswerType.OBJECTDETECTION, 50));
        challenges.add(new Challenge("Edifícios da ESTG", "Quantos edifícios tem ESTG?", 30000, AnswerType.NUMBER, 10));
        challenges.add(new Challenge("Biblioteca ESTG", "Como se chama a biblioteca da ESTG (Campus 2)?", 60000, AnswerType.TEXT, 20));
        challenges.add(new Challenge("Ler QRCode", "Faça a leitura do QrCode na sala 2.04", 120000, AnswerType.QRCODE, 100));
        challenges.add(new Challenge("Bares da ESTG", "Quantos Bares têm a ESTG?", 30000, AnswerType.NUMBER, 30));
        challenges.add(new Challenge("Cursos ESTG 2019", "Quantos cursos têm na ESTG este Ano?", 60000, AnswerType.NUMBER, 20));
        challenges.add(new Challenge("Departamentos ESTG", "Quantos departamentos tem a ESTG?", 60000, AnswerType.NUMBER, 100));
    }

    public void addChallenge(Challenge challenge) {
        challenges.add(challenge);
    }

    public void addChallengeAtPosition(int position, Challenge challenge) {
        challenges.add(position, challenge);
    }


    public ArrayList<Challenge> getChallenges() {
        return challenges;
    }


    public void removeChallenge(Challenge challenge) {
        challenges.remove(challenges);
    }

    public void removeChallengeAtPosition(int position) {
        challenges.remove(position);
    }
}
