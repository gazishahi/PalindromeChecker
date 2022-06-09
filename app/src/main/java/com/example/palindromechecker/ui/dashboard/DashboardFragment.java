package com.example.palindromechecker.ui.dashboard;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.palindromechecker.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;
import java.util.Random;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private TextView answer;
    private TextView wordLength;
    private TextView someLetters;
    private EditText editTextInput;
    private Button guessButton;
    private Button nextButton;
    private int tries;
    private String randomString;
    private String hintLetters;
    private String[] words;
    Handler myHandler;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        answer = view.findViewById(R.id.answer);
        wordLength = view.findViewById(R.id.wordLength);
        someLetters = view.findViewById(R.id.someLetters);
        editTextInput = view.findViewById(R.id.enterWord);
        guessButton = view.findViewById(R.id.guessButton);
        guessButton.setOnClickListener(this);
        nextButton = view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        words = getResources().getStringArray(R.array.gameList);
        answer.setText(getString(R.string.waiting));
        myHandler = new Handler();
        nextWord();
        return view;
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            answer.setText(getString(R.string.waiting));
            nextWord();
        }
    };

    @Override
    public void onClick (View v) {
        if (v == guessButton) {
            String userInput = editTextInput.getText().toString().toLowerCase().replaceAll("[\\W]", "");
            if (userInput.equals(randomString)) {
                answer.setText(getString(R.string.correct));
                myHandler = new Handler();
                myHandler.postDelayed(r, 5000);
            }
            else {
                if(tries != 0) {
                    String triesLeft = "You have " + tries + " tries left.";
                    answer.setText(getString(R.string.wrong));
                    Snackbar.make(requireContext(), view, triesLeft, 5000).show();
                    tries--;
                }
                else {
                    String finalAnswer = getString(R.string.no_more_tries) + " " + randomString;
                    answer.setText(finalAnswer);
                    Snackbar.make(requireContext(), view, "No more tries left.", 5000).show();
                    myHandler = new Handler();
                    myHandler.postDelayed(r, 5000);
                }
            }
        }
        if (v == nextButton) {
            answer.setText(getString(R.string.waiting));
            nextWord();
        }
    }

    public void nextWord() {
        randomString = words[new Random().nextInt(words.length)];
        randomString = randomString.toLowerCase().replaceAll("[\\W]", "");
        int randLength = randomString.length();
        tries = randLength / 3;
        String randLetters = "Length: " + String.format(Locale.getDefault(), "%d", randLength);
        wordLength.setText(randLetters);
        char[] letters = new char[randLength / 3];
        for (int i = 0; i < randLength / 3; i++) {
            Random random = new Random();
            int index = random.nextInt(randLength);
            letters[i] = randomString.charAt(index);
            hintLetters = new String(letters);
        }
        hintLetters = hintLetters.replaceAll(".(?=.)", "$0 ");
        hintLetters = "Letters: " + hintLetters;
        someLetters.setText(hintLetters);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}