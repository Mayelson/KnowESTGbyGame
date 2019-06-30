package pt.ipleiria.knowestgbygame.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import pt.ipleiria.knowestgbygame.Activities.ChallengeActivity;
import pt.ipleiria.knowestgbygame.Models.AnswerType;
import pt.ipleiria.knowestgbygame.R;

public class NumberFragment extends Fragment {

    private EditText number_edit_Text;
    private int number;
    private Button btnAnswer;
    private View view;
    private ChallengeActivity challengeActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_answer_number, container, false);


        number_edit_Text = view.findViewById(R.id.editText_number_answer);
        challengeActivity = (ChallengeActivity) getActivity();

        btnAnswer = view.findViewById(R.id.btn_to_answer);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = number_edit_Text.getText().toString();
                if (text.matches("")) {
                    Toast.makeText(NumberFragment.this.getContext(), "Deve indicar um número", Toast.LENGTH_SHORT).show();
                    return;
                }
                number = Integer.parseInt(text);
               // Toast.makeText(NumberFragment.this.getContext(), "Resposta enviada " + number, Toast.LENGTH_SHORT).show();
                closeKeyboard();
                challengeActivity.getAnswer(Integer.toString(number));
                //TODO
                //verify if number is equal to possible answer


            }
        });

        return view;
    }

    private void closeKeyboard() {
        if (view != null) {
            InputMethodManager inputMethod = (InputMethodManager)NumberFragment.this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
