package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class RecipeStepInstructionsFragment extends Fragment {

    @Setter
    private String instructions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        TextView root = (TextView) inflater.inflate(R.layout.recipe_step_instructions_fragment,
                container, false);

        if (instructions != null) {
            root.setText(instructions);
        }

        return root;
    }
}
