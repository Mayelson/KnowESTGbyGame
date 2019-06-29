package pt.ipleiria.knowestgbygame.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import pt.ipleiria.knowestgbygame.Activities.AddGameActivity;
import pt.ipleiria.knowestgbygame.Activities.DashboardActivity;
import pt.ipleiria.knowestgbygame.Adapters.GameViewAdapter;
import pt.ipleiria.knowestgbygame.Helpers.Constant;
import pt.ipleiria.knowestgbygame.Models.GamesManager;
import pt.ipleiria.knowestgbygame.R;

public class GameFragment extends Fragment {

    private RecyclerView recyclerView;
    private GameViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private View view;
    private DashboardActivity dashboardActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game, container, false);
        dashboardActivity = (DashboardActivity) getActivity();
        dashboardActivity.setTitle(R.string.game);
        setHasOptionsMenu(true);
        buildRecycleView();

        return view;
    }


    public void buildRecycleView() {
        recyclerView = view.findViewById(R.id.rv_list_games);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(GameFragment.this.getContext());
        adapter = new GameViewAdapter(GamesManager.manager().getGames());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new GameViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startAddChallengeActivity(position);
            }
        });

        adapter.setOnLongClickListener(new GameViewAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                removeGame(position);
            }
        });
    }


    public void removeGame(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameFragment.this.getContext());
        builder.setMessage(R.string.message_delete_game)
                .setTitle(R.string.delete_game_title);
        builder.setPositiveButton(R.string.cancel, null);
        builder.setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                GamesManager.manager().removeGameAtPosition(position);
                adapter.notifyDataSetChanged();
            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add :
            {
//                Intent intent = new Intent(getActivity(), AddGameActivity.class);
  //              startActivityForResult(intent, Constant.REQUEST_CODE);
                startAddChallengeActivity(-1);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE && resultCode == dashboardActivity.RESULT_OK) {
            adapter.notifyDataSetChanged();
        }
    }

    public void startAddChallengeActivity(int position) {
        Intent addActivity = new Intent(dashboardActivity.getApplicationContext(), AddGameActivity.class);

        //if position is not -1, then we are editing
        if (position != -1){
            addActivity.putExtra(Constant.GAME_TO_EDIT, GamesManager.manager().getGames().get(position));
            addActivity.putExtra(Constant.POSITION, position);
        }
        startActivityForResult(addActivity, Constant.REQUEST_CODE);
    }
}
