package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Step;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class RecipeStepsFragment extends Fragment {
    @Setter
    private List<Step> steps;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView root = (RecyclerView) inflater.inflate(R.layout.recipe_steps_fragment,
                container, false);

        assert steps != null;

        // TODO: Create Adapter and ViewHolder

        return root;
    }
}
