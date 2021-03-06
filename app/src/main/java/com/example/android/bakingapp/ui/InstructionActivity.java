package com.example.android.bakingapp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.Step;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionActivity extends AppCompatActivity
        implements ViewTreeObserver.OnScrollChangedListener {

    @BindString(R.string.bundle_key_recipe) String bundleKeyRecipe;
    @BindString(R.string.bundle_key_step_index) String bundleStepIndex;

    @Nullable @BindView(R.id.instruction_activity_scroll_view) ScrollView scrollView;
    @BindView(R.id.media_player_container) FrameLayout mediaPlayerFrameLayout;
    @BindView(R.id.navigation_bar_next_button) Button nextButton;
    @BindView(R.id.navigation_bar_previous_button) Button previousButton;

    private Recipe recipe;
    private int stepIndex;
    private boolean actionBarShown = true;

    public void onNext(View view) {
        stepIndex++;
        replaceFragments();
    }

    public void onPrevious(View view) {
        stepIndex--;
        replaceFragments();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);

        if (scrollView != null) {
            scrollView.getViewTreeObserver().addOnScrollChangedListener(this);
        }

        if (!restoreSavedState(savedInstanceState)) {
            Intent intent = getIntent();

            recipe = intent.getParcelableExtra(bundleKeyRecipe);
            stepIndex = intent.getIntExtra(bundleStepIndex, 0);
        }

        setTitle(recipe.getName());

        Step step = recipe.getSteps().get(stepIndex);

        displayNavigationButtonsAsNeeded();

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

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (actionBar != null) {
                actionBar.hide();
                actionBarShown = false;
            }

            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            ViewGroup.LayoutParams params = mediaPlayerFrameLayout.getLayoutParams();
            params.height = size.y;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (scrollView != null) {
            scrollView.getViewTreeObserver().removeOnScrollChangedListener(this);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        restoreSavedState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(bundleKeyRecipe, recipe);
        outState.putInt(bundleStepIndex, stepIndex);
    }

    @Override
    public void onScrollChanged() {
        int y = scrollView != null ? scrollView.getScrollY() : 0;

        if (y == 0 && actionBarShown) {
            ActionBar actionBar = getSupportActionBar();

            if (actionBar != null) {
                actionBar.hide();
            }

            actionBarShown = false;
        } else if (y > 0 && !actionBarShown) {
            ActionBar actionBar = getSupportActionBar();

            if (actionBar != null) {
                actionBar.show();
                actionBarShown = true;
            }
        }
    }

    private void displayNavigationButtonsAsNeeded() {
       if (stepIndex > 0) {
            previousButton.setVisibility(View.VISIBLE);
        } else {
            previousButton.setVisibility(View.INVISIBLE);
        }

        if (stepIndex < recipe.getSteps().size() - 1) {
            nextButton.setVisibility(View.VISIBLE);
        } else {
            nextButton.setVisibility(View.INVISIBLE);
        }
    }

    private void replaceFragments() {
        Step step = recipe.getSteps().get(stepIndex);

        displayNavigationButtonsAsNeeded();

        MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
        mediaPlayerFragment.setVideoUrl(step.getVideoURL());
        mediaPlayerFragment.setThumbnailUrl(step.getThumbnailURL());

        RecipeStepInstructionsFragment instructionsFragment = new RecipeStepInstructionsFragment();
        instructionsFragment.setInstructions(step.getDescription());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.media_player_container, mediaPlayerFragment)
                .replace(R.id.recipe_step_instructions_container, instructionsFragment)
                .commit();
    }

    private boolean restoreSavedState(Bundle savedState) {
        int value = 0;

        if (savedState != null) {
            if (savedState.containsKey(bundleKeyRecipe)) {
                recipe = savedState.getParcelable(bundleKeyRecipe);
                value++;
            }

            if (savedState.containsKey(bundleStepIndex)) {
                stepIndex = savedState.getInt(bundleStepIndex);
                value++;
            }
        }

        return value == 2;
    }
}
