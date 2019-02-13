package com.example.android.bakingapp.listeners;

import com.example.android.bakingapp.data.Recipe;

public interface StepSelectionListener {
    void onStepSelected(Recipe recipe, int stepIndex);
}
