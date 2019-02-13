package com.example.android.bakingapp.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.DetailAdapter;
import com.example.android.bakingapp.adapter.StepAdapter;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.listeners.StepSelectionListener;

public class RecipeActivity extends AppCompatActivity implements StepSelectionListener {

    @BindString(R.string.bundle_key_recipe) String bundleKeyRecipe;
    @BindString(R.string.bundle_key_step_index) String bundleStepIndex;
    @BindView(R.id.recipe_detail_recycler_view) RecyclerView recyclerView;
    @Nullable @BindView(R.id.ingredients_card_container) FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        Recipe recipe = getIntent().getParcelableExtra(bundleKeyRecipe);

        setTitle(recipe.getName());

        if (frameLayout == null) {
            recyclerView.setAdapter(new DetailAdapter(this, recipe, this));
        } else {
            IngredientsCardFragment fragment = new IngredientsCardFragment();
            fragment.setIngredients(recipe.getIngredients());

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.ingredients_card_container, fragment)
                    .commit();

            recyclerView.setAdapter(new StepAdapter(this, recipe, this));
        }
    }

    @Override
    public void onStepSelected(Recipe recipe, int stepIndex) {
        Intent activity = new Intent(this, InstructionActivity.class);
        activity.putExtra(bundleKeyRecipe, recipe);
        activity.putExtra(bundleStepIndex, stepIndex);
        startActivity(activity);
    }
}
