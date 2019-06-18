package pt.ipleiria.knowestgbygame;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.knowestgbygame.Adapters.ChallengeViewAdapter;
import pt.ipleiria.knowestgbygame.Models.Challenge;

public class ChallengeFragment extends Fragment {

    List<Challenge> challenges;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        challenges = new ArrayList<>();
        challenges.add(new Challenge("Ler QRCode", "Faça a leitura do QrCode na sala 2.04", R.drawable.ic_classification));
        challenges.add(new Challenge("Simbolo do IPL", "Tirar uma foto ao logotipo de IPL", R.drawable.ic_map));
        challenges.add(new Challenge("Identificação do rosto", "Tristique sollicitudin nibh sit amet commodo", R.drawable.ic_launcher_foreground));
        challenges.add(new Challenge("Caixa Geral de Depósito", "Diam phasellus vestibulum lorem sed risus ultricies tristique.", R.drawable.ic_profile));
        challenges.add(new Challenge("Foto Alegre", "Cras ornare arcu dui vivamus arcu felis bibendum.", R.drawable.ic_support));
        challenges.add(new Challenge("Efetuar 300 passos", "O utilizador deve efetuar 200 passos. dentro de uma área definida pela aplicação. caso saia da área definida, o desafio é perdido", R.drawable.ic_challenge));
        challenges.add(new Challenge("Caminhar até a sala de MEICM", "Mauris augue neque gravida in fermentum et sollicitudin ac orci.", R.drawable.ic_profile));

        View view = inflater.inflate(R.layout.fragment_challenge, container, false);
       // ImageView imageView = (ImageView) view.findViewById(R.id.my_image);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleView_challenge);
        ChallengeViewAdapter myAdapter = new ChallengeViewAdapter(this.getContext(), challenges);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView.setAdapter(myAdapter);

        return view;
    }

}
