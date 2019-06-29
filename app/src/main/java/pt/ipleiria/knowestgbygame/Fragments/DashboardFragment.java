package pt.ipleiria.knowestgbygame.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pt.ipleiria.knowestgbygame.Adapters.DashboardViewAdapter;
import pt.ipleiria.knowestgbygame.Helpers.HelperMethods;
import pt.ipleiria.knowestgbygame.Models.Challenge;
import pt.ipleiria.knowestgbygame.Models.Game;
import pt.ipleiria.knowestgbygame.Models.GamesManager;
import pt.ipleiria.knowestgbygame.R;

public class DashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        buildRecycleView();
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.app_name);

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if(menu!=null){
            menu.clear();
        }
    }

    public void buildRecycleView(){
        recyclerView = view.findViewById(R.id.recycleView_challenge);
        DashboardViewAdapter myAdapter = new DashboardViewAdapter(this.getContext(), GamesManager.manager().getGames());
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView.setAdapter(myAdapter);

    }
}
