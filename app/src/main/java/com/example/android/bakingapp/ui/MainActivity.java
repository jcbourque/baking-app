package com.example.android.bakingapp.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.RecipeAdapter;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.listeners.RecipeSelectionListener;
import com.example.android.bakingapp.ui.widget.RecipeWidgetProvider;
import com.example.android.bakingapp.utils.AppExecutors;
import com.example.android.bakingapp.utils.JsonUtils;
import com.example.android.bakingapp.utils.NetUtils;
import com.example.android.bakingapp.utils.StringUtils;
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
    private int appWidgetId;
    private boolean configureMode;

    @Override
    public void onRecipeSelected(Recipe recipe) {
        Intent activity = new Intent(this, RecipeActivity.class);
        activity.putExtra(recipeBundleKey, recipe);

        if (!configureMode) {
            startActivity(activity);
        } else {
            activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    activity, PendingIntent.FLAG_CANCEL_CURRENT);

            RecipeWidgetProvider.setSharedPreferences(this, appWidgetId, recipe);

            RemoteViews views = new RemoteViews(getPackageName(), R.layout.recipe_widget_provider);

            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
            views.setTextViewText(R.id.appwidget_text, StringUtils.getIngredients(recipe));

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            appWidgetManager.updateAppWidget(appWidgetId, views);

            Intent resultValue = new Intent();

            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            setResult(RESULT_OK, resultValue);

            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        configureMode = appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID;

        if (configureMode) {
            setResult(RESULT_CANCELED);
        }

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
            public void onData(@Nullable String response, int recipeId) {
                AppExecutors.instance().mainThread().execute(() ->
                        populateUI(JsonUtils.toRecipes(response)));
            }

            @Override
            public void onError(@NonNull String message, int recipeId) {
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
