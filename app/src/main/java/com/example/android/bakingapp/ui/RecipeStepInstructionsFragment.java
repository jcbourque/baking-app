package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindString;
import butterknife.ButterKnife;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class RecipeStepInstructionsFragment extends Fragment {

    private static final String TAG = "RecipeStepInstructionsFragment";

    @BindString(R.string.bundle_key_step_instructions) String bundleKey;

    @Setter
    private String instructions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        TextView root = (TextView) inflater.inflate(R.layout.recipe_step_instructions_fragment,
                container, false);
        ButterKnife.bind(root);

        if (savedInstanceState != null) {
            Log.d(TAG, "onCreateView: Restoring instructions: " + instructions);
            instructions = savedInstanceState.getString(bundleKey, instructions);
        }

        if (instructions != null) {
            Log.d(TAG, "onCreateView: Setting instructions: " + instructions);
            root.setText(instructions);
        } else {
            Log.d(TAG, "onCreateView: I have no instructions!!!");
        }

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState: Saving instructions: " + instructions);
        outState.putString(bundleKey, instructions);
    }
}
