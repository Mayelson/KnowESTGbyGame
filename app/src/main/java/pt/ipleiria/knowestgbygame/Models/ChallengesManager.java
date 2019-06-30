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

        challenges.add(new Challenge("Ler QRCode", "Faça a leitura do QrCode na sala A.S.0.4", 120000, AnswerType.QRCODE, 100));
        challenges.get(0).setAnswer("A.S.0.4: Esta sala é reservada ao alunos de mestrado.");
        challenges.get(0).setSuggestion("A: Edifício A\n S: Sala\n 0: Piso\n 4: Número da Sala");


        challenges.add(new Challenge("Edifícios da ESTG", "Quantos edifícios tem no campus 2?", 60000, AnswerType.NUMBER, 100));
        challenges.get(1).setAnswer("5");

        challenges.add(new Challenge("Equipamento informático: Teclado", "Tire uma foto a um teclado.", 30000, AnswerType.OBJECTDETECTION, 50));
        challenges.get(2).setAnswer("keyboard");

        challenges.add(new Challenge("Biblioteca ESTG", "Como se chama a biblioteca da ESTG (Campus 2)?", 60000, AnswerType.TEXT, 20));
        challenges.get(3).setAnswer("Biblioteca José Saramago");
        challenges.get(3).setSuggestion("Biblioteca J_s_ Sa_a__go");

        challenges.add(new Challenge("Bares da ESTG", "Quantos Bares têm a ESTG?", 30000, AnswerType.NUMBER, 30));
        challenges.get(4).setAnswer("3");


        challenges.add(new Challenge("Plataforma para gestão de conteúdos", "Como se chama a plataforma utilizada no ipl para gestão dos conteúdos?", 30000, AnswerType.TEXT, 10));
        challenges.get(5).setAnswer("Plataforma eLearning");
        challenges.get(5).setSuggestion("Plataforma e_e_r_ing ou _o_d_l_");

        challenges.add(new Challenge("Rede da wireless", "Como se chama a Rede wireless mais utilizada pela comunidade ipl?", 30000, AnswerType.NUMBER, 40));
        challenges.get(6).setAnswer("eduroam");

        challenges.add(new Challenge("Equipamentos Informáticos: Rato", "Tire uma foto a um rato", 30000, AnswerType.OBJECTDETECTION, 10));
        challenges.get(7).setAnswer("mouse");


        challenges.add(new Challenge("Logotipo IPL", "Encontre um logotipo do IPL e tire uma foto!", 120000, AnswerType.LOGODETECTION, 120));
        challenges.get(8).setAnswer("LEIRIA");

        challenges.add(new Challenge("Mostrar felicidade", "Encontre um amigo e tire uma foto que aparenta felicidade!", 120000, AnswerType.FACEDETECTION, 300));
        challenges.get(9).setAnswer("sorriso");

        for (Challenge challenge: challenges) {
            challenge.setThumbnail(R.drawable.ic_extension_black);
        }

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

    public String[] getChallengsNames() {
        String[] challengesTitle = new String[challenges.size()];
        for (int i = 0; i < challenges.size(); i++) {
            challengesTitle[i] = challenges.get(i).getTitle();
        }
        return challengesTitle;
    }




    public  ArrayList<Integer> getPositionOfSelectedsChallengs(ArrayList<Challenge> challengesInCurrentGame){
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < challengesInCurrentGame.size(); i++) {
            for (int position = 0; position < challenges.size(); position++) {
                if (challenges.get(position).getUuid().equalsIgnoreCase(challengesInCurrentGame.get(i).getUuid())){
                    positions.add(position);
                }
            }
        }
        return positions;
    }
}
