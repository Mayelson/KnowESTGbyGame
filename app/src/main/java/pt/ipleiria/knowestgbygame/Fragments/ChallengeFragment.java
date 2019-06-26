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

import pt.ipleiria.knowestgbygame.Activities.AddChallengeActivity;
import pt.ipleiria.knowestgbygame.Activities.MainActivity;
import pt.ipleiria.knowestgbygame.Adapters.ChallengeViewAdapter;
import pt.ipleiria.knowestgbygame.Models.AnswerType;
import pt.ipleiria.knowestgbygame.Models.Challenge;
import pt.ipleiria.knowestgbygame.Models.Sugestion;
import pt.ipleiria.knowestgbygame.R;

public class ChallengeFragment extends Fragment {

    private ArrayList<Challenge> challenges;
    private ArrayList<Sugestion> sugestions;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private View view;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_challenge, container, false);
        mainActivity = (MainActivity) getActivity();

        setHasOptionsMenu(true);

        createChallengesExemples();
        buildRecycleView();


        return view;
    }

    public void createChallengesExemples() {
        sugestions = new ArrayList<>();
        challenges = new ArrayList<>();

        sugestions.add(new Sugestion("A Sala encontra no Edifício A"));
        sugestions.add(new Sugestion("A cantina 2 encontra-se ao pé da Biblioteca José Saramago"));

        challenges.add(new Challenge("Equipamento informático", "Tirar ma foto a um teclado.", R.drawable.ic_map, 30000, sugestions, AnswerType.LABEL));
        challenges.add(new Challenge("Edifícios da ESTG", "Quantos edifícios tem ESTG?", R.drawable.ic_map, 30000, sugestions, AnswerType.NUMBER));
        // challenges.add(new Challenge("Identificação do rosto", "Caminhe sorindo ate o Bar da Cantina 2", R.drawable.ic_launcher_foreground, 60000, sugestions, AnswerType.LABEL));
        challenges.add(new Challenge("Biblioteca ESTG", "Como se chama a biblioteca da ESTG (Campus 2)?", R.drawable.ic_map, 60000, sugestions, AnswerType.TEXT));
        challenges.add(new Challenge("Ler QRCode", "Faça a leitura do QrCode na sala 2.04", R.drawable.ic_challenge, 120000, sugestions, AnswerType.QRCODE));
        challenges.add(new Challenge("Bares da ESTG", "Quantos Bares têm a ESTG?", R.drawable.ic_map, 30000, sugestions, AnswerType.NUMBER));
    }

    public void buildRecycleView() {
        recyclerView = view.findViewById(R.id.rv_list_challenges);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ChallengeFragment.this.getContext());
        adapter = new ChallengeViewAdapter(challenges);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add: {
                Intent intent = new Intent(getActivity(), AddChallengeActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.action_search: {
                //Write here what to do you on click
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}