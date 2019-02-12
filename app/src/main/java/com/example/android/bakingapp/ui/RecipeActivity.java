package com.example.android.bakingapp.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.DetailAdapter;
import com.example.android.bakingapp.adapter.StepAdapter;
import com.example.android.bakingapp.data.Recipe;

public class RecipeActivity extends AppCompatActivity {

    @BindString(R.string.bundle_key_recipe) String bundleKey;
    @BindView(R.id.recipe_detail_recycler_view) RecyclerView recyclerView;
    @Nullable @BindView(R.id.ingredients_card_container) FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        Recipe recipe = getIntent().getParcelableExtra(bundleKey);

        setTitle(recipe.getName());

        if (frameLayout == null) {
            recyclerView.setAdapter(new DetailAdapter(this, recipe));
        } else {
            IngredientsCardFragment fragment = new IngredientsCardFragment();
            fragment.setIngredients(recipe.getIngredients());

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.ingredients_card_container, fragment)
                    .commit();

            recyclerView.setAdapter(new StepAdapter(this, recipe.getSteps()));
        }
    }
}
