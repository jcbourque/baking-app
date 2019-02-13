package com.example.android.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.RecipeAdapter;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.listeners.RecipeSelectionListener;
import com.example.android.bakingapp.utils.AppExecutors;
import com.example.android.bakingapp.utils.JsonUtils;
import com.example.android.bakingapp.utils.NetUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeSelectionListener {
    @BindView(R.id.recipe_list) RecyclerView recyclerView;
    @BindView(R.id.progress_indicator) ProgressBar progressBar;

    @BindString(R.string.bundle_key_recipe) String recipeBundleKey;
    @BindString(R.string.bundle_key_recipes) String recipesBundleKey;

    private RecipeAdapter recipeAdapter;

    @Override
    public void onRecipeSelected(Recipe recipe) {
        Intent activity = new Intent(this, RecipeActivity.class);
        activity.putExtra(recipeBundleKey, recipe);
        startActivity(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (savedInstanceState != null && savedInstanceState.containsKey(recipesBundleKey)) {
            populateUI(savedInstanceState.getParcelableArrayList(recipesBundleKey));
        } else {
            loadData();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(recipesBundleKey)) {
            populateUI(savedInstanceState.getParcelableArrayList(recipesBundleKey));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(recipesBundleKey,
                (ArrayList<? extends Parcelable>) recipeAdapter.getRecipes());
    }

    private void loadData() {
        NetUtils.request(getString(R.string.data_url), new NetUtils.Response() {
            @Override
            public void onData(@Nullable String response) {
                AppExecutors.instance().mainThread().execute(() ->
                        populateUI(JsonUtils.parseRecipes(response)));
            }

            @Override
            public void onError(@NonNull String message) {
                Snackbar.make(recyclerView, message, Snackbar.LENGTH_INDEFINITE).show();
            }
        });
    }

    private void populateUI(List<Recipe> recipes) {
        if (recipeAdapter == null) {
            recipeAdapter = new RecipeAdapter(this, recipes, this);
        } else {
            recipeAdapter.setRecipes(recipes);
        }

        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
