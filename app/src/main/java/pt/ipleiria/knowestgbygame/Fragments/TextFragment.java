package pt.ipleiria.knowestgbygame.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import pt.ipleiria.knowestgbygame.Activities.ChallengeActivity;
import pt.ipleiria.knowestgbygame.R;

public class TextFragment extends Fragment {

    private Button btnAnswer;
    private EditText answer_edit_Text;
    private String answer;
    private View view;
    private ChallengeActivity challengeActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_answer_text, container, false);


        answer_edit_Text = view.findViewById(R.id.editText_number_answer);
        btnAnswer = view.findViewById(R.id.btn_to_answer);

        challengeActivity = (ChallengeActivity) getActivity();


        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                answer = answer_edit_Text.getText().toString();
                if (answer.matches("")) {
                    Toast.makeText(TextFragment.this.getContext(), "Deve preencher o campo da resposta", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(TextFragment.this.getContext(), "Resposta enviada: " + answer, Toast.LENGTH_SHORT).show();

                closeKeyboard();

                challengeActivity.getAnswer(answer, 0);

                //TODO
                //verify if answer is equal to possible answer

            }


        });

        return view;
    }

    private void closeKeyboard() {
        if (view != null) {
            InputMethodManager inputMethod = (InputMethodManager)TextFragment.this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
