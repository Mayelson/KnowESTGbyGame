package pt.ipleiria.knowestgbygame.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.knowestgbygame.Adapters.DashboardViewAdapter;
import pt.ipleiria.knowestgbygame.Models.AnswerType;
import pt.ipleiria.knowestgbygame.Models.Challenge;
import pt.ipleiria.knowestgbygame.Models.Game;
import pt.ipleiria.knowestgbygame.Models.Sugestion;
import pt.ipleiria.knowestgbygame.R;

public class DashboardFragment extends Fragment {

    List<Game> games;
    List<Challenge> challenges;
    List<Sugestion> sugestions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        games = new ArrayList<>();
        sugestions = new ArrayList<>();
        challenges = new ArrayList<>();

        sugestions.add(new Sugestion("A Sala encontra no Edifício A"));
        sugestions.add(new Sugestion("A cantina 2 encontra-se ao pé da Biblioteca José Saramago"));

        challenges.add(new Challenge("Edifícios da ESTG", "Quantos edifícios tem ESTG?", R.drawable.ic_map, 30000, sugestions, AnswerType.NUMBER));
        // challenges.add(new Challenge("Identificação do rosto", "Caminhe sorindo ate o Bar da Cantina 2", R.drawable.ic_launcher_foreground, 60000, sugestions, AnswerType.LABEL));
        challenges.add(new Challenge("Biblioteca ESTG", "Como se chama a biblioteca da ESTG (Campus 2)?", R.drawable.ic_map, 60000, sugestions, AnswerType.TEXT));
        challenges.add(new Challenge("Ler QRCode", "Faça a leitura do QrCode na sala 2.04", R.drawable.ic_challenge, 120000, sugestions, AnswerType.QRCODE));
        challenges.add(new Challenge("Bares da ESTG", "Quantos Bares têm a ESTG?", R.drawable.ic_map, 30000, sugestions, AnswerType.NUMBER));

        games.add(new Game("Jogo 1", "Este jogo permite aos jogadores conhecerem as salas do edificio A ", R.drawable.ic_launcher_foreground, challenges, "Mayelson", 10));
        games.add(new Game("Jogo 2", "Este jogo, permite aos jogadores conherem os bares da ESTG", R.drawable.ic_launcher_foreground, challenges, "Mayelson", 20));
        games.add(new Game("Jogo 3", "Este jogo permite aos jogadores conhecerem a Biblioteca José saramago", R.drawable.ic_launcher_foreground, challenges, "Mayelson", 5));

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleView_challenge);
        DashboardViewAdapter myAdapter = new DashboardViewAdapter(this.getContext(), games);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView.setAdapter(myAdapter);

        return view;
    }

}
