package pt.ipleiria.knowestgbygame.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pt.ipleiria.knowestgbygame.Activities.AddGameActivity;
import pt.ipleiria.knowestgbygame.Activities.MainActivity;
import pt.ipleiria.knowestgbygame.Adapters.GameViewAdapter;
import pt.ipleiria.knowestgbygame.Models.AnswerType;
import pt.ipleiria.knowestgbygame.Models.Challenge;
import pt.ipleiria.knowestgbygame.Models.Game;
import pt.ipleiria.knowestgbygame.Models.GamesManager;
import pt.ipleiria.knowestgbygame.Models.Sugestion;
import pt.ipleiria.knowestgbygame.R;

public class GameFragment extends Fragment {

    //private ArrayList<Game> games;
    //private ArrayList<Challenge> challenges;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private View view;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game, container, false);
        mainActivity = (MainActivity) getActivity();

        setHasOptionsMenu(true);

        //createGamesExamples();
        buildRecycleView();



        return view;
    }

    public void createGamesExamples() {
    /*    games = new ArrayList<>();
        challenges = new ArrayList<>();

        challenges.add(new Challenge("Equipamento informático", "Tirar ma foto a um teclado.", R.drawable.ic_map, 30000, AnswerType.OBJECTDETECTION, 50));
        challenges.add(new Challenge("Edifícios da ESTG", "Quantos edifícios tem ESTG?", R.drawable.ic_map, 30000, AnswerType.NUMBER, 10));
        // challenges.add(new Challenge("Identificação do rosto", "Caminhe sorindo ate o Bar da Cantina 2", R.drawable.ic_launcher_foreground, 60000, sugestions, AnswerType.OBJECTDETECTION));
        challenges.add(new Challenge("Biblioteca ESTG", "Como se chama a biblioteca da ESTG (Campus 2)?", R.drawable.ic_map, 60000, AnswerType.TEXT, 20));
        challenges.add(new Challenge("Ler QRCode", "Faça a leitura do QrCode na sala 2.04", R.drawable.ic_challenge, 120000, AnswerType.QRCODE, 100));
        challenges.add(new Challenge("Bares da ESTG", "Quantos Bares têm a ESTG?", R.drawable.ic_map, 30000, AnswerType.NUMBER, 30));

        games.add(new Game("Jogo 1", "Este jogo permite aos jogadores conhecerem as salas do edificio A ", R.drawable.ic_launcher_foreground, challenges, "Mayelson", 10));
        games.add(new Game("Jogo 2", "Este jogo, permite aos jogadores conherem os bares da ESTG", R.drawable.ic_launcher_foreground, challenges, "Mayelson", 20));
        games.add(new Game("Jogo 3", "Este jogo permite aos jogadores conhecerem a Biblioteca José saramago", R.drawable.ic_launcher_foreground, challenges, "Mayelson", 5));*/

    }

    public void buildRecycleView() {
        recyclerView = view.findViewById(R.id.rv_list_games);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(GameFragment.this.getContext());
        adapter = new GameViewAdapter(GamesManager.manager().getGames());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add :
            {
                Intent intent = new Intent(getActivity(), AddGameActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.action_search:
            {
                //Write here what to do you on click
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
