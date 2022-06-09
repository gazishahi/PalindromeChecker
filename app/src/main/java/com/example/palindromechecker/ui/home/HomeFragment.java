package com.example.palindromechecker.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.palindromechecker.R;
import com.example.palindromechecker.ui.notifications.NotificationsViewModel;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private NotificationsViewModel viewModel;
    private TextView textViewStatus;
    private EditText editTextInput;
    private Button button;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        textViewStatus = view.findViewById(R.id.textViewStatus);
        editTextInput = view.findViewById(R.id.editTextInput);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(this);
        viewModel = new ViewModelProvider(requireActivity()).get(NotificationsViewModel.class);
        return view;
    }

    @Override
    public void onClick (View v) {
        if (v == button) {
            char[] charInput = editTextInput.getText().toString().toLowerCase().replaceAll("[\\W]", "").toCharArray();
            int intLength = charInput.length;
            boolean isPalindrome = intLength != 0;

            for (int i = 0; i < intLength / 2; i++) {
                if (charInput[i] != charInput[intLength - 1 - i]) {
                    isPalindrome = false;
                    break;
                }
            }
            if (isPalindrome) {
                textViewStatus.setText(getString(R.string.palindrome));
                viewModel.setText(editTextInput.getText().toString());
            }
            else {
                textViewStatus.setText(getString(R.string.not_palindrome));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}