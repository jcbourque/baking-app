package com.example.android.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.StepAdapter;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.Step;
import com.example.android.bakingapp.listeners.StepSelectionListener;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements StepSelectionListener {

    @BindString(R.string.bundle_key_recipe) String bundleKeyRecipe;
    @BindString(R.string.bundle_key_step_index) String bundleStepIndex;
    @BindView(R.id.recipe_detail_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.ingredients_card_container) FrameLayout cardContainer;
    @Nullable @BindView(R.id.media_player_container) FrameLayout mediaPlayerContainer;

    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);
        twoPane = mediaPlayerContainer != null;

        Recipe recipe = getIntent().getParcelableExtra(bundleKeyRecipe);
        setTitle(recipe.getName());

        if (savedInstanceState != null) {
            recyclerView.setAdapter(new StepAdapter(this, recipe, this));
            return;
        }

        IngredientsCardFragment fragment = new IngredientsCardFragment();
        fragment.setIngredients(recipe.getIngredients());

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.ingredients_card_container, fragment)
                .commit();

        if (twoPane) {
            Step step = recipe.getSteps().get(0);

            MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
            mediaPlayerFragment.setVideoUrl(step.getVideoURL());
            mediaPlayerFragment.setThumbnailUrl(step.getThumbnailURL());

            RecipeStepInstructionsFragment instructionsFragment = new RecipeStepInstructionsFragment();
            instructionsFragment.setInstructions(step.getDescription());

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.media_player_container, mediaPlayerFragment)
                    .add(R.id.recipe_step_instructions_container, instructionsFragment)
                    .commit();
        }

        recyclerView.setAdapter(new StepAdapter(this, recipe, this));
    }

    @Override
    public void onStepSelected(Recipe recipe, int stepIndex) {
        if (twoPane) {
            replaceFragments(recipe, stepIndex);
        } else {
            Intent activity = new Intent(this, InstructionActivity.class);
            activity.putExtra(bundleKeyRecipe, recipe);
            activity.putExtra(bundleStepIndex, stepIndex);
            startActivity(activity);
        }
    }

    private void replaceFragments(Recipe recipe, int stepIndex) {
        Step step = recipe.getSteps().get(stepIndex);

        IngredientsCardFragment ingredientsFragment = new IngredientsCardFragment();
        ingredientsFragment.setIngredients(recipe.getIngredients());

        MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
        mediaPlayerFragment.setVideoUrl(step.getVideoURL());
        mediaPlayerFragment.setThumbnailUrl(step.getThumbnailURL());

        RecipeStepInstructionsFragment instructionsFragment = new RecipeStepInstructionsFragment();
        instructionsFragment.setInstructions(step.getDescription());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.ingredients_card_container, ingredientsFragment)
                .replace(R.id.media_player_container, mediaPlayerFragment)
                .replace(R.id.recipe_step_instructions_container, instructionsFragment)
                .commit();
    }
}
