package com.example.android.bakingapp.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.ui.MainActivity;
import com.example.android.bakingapp.ui.RecipeActivity;
import com.example.android.bakingapp.utils.JsonUtils;
import com.example.android.bakingapp.utils.StringUtils;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String PREF = "_pref_";
    private static final String RECIPE = "recipe";

    public static SharedPreferences getSharedPreferences(Context context, int appWidgetId) {
        String name = context.getPackageName() + PREF + appWidgetId;
        return context.getSharedPreferences(name, 0);
    }

    public static void setSharedPreferences(Context context, int appWidgetId, Recipe recipe) {
        SharedPreferences sharedPreferences = getSharedPreferences(context, appWidgetId);
        sharedPreferences
                .edit()
                .putString(RECIPE, JsonUtils.fromRecipe(recipe))
                .apply();
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        SharedPreferences sharedPreferences = getSharedPreferences(context, appWidgetId);
        String recipeJson = sharedPreferences.getString(RECIPE, null);

        Recipe recipe = recipeJson == null ? null : JsonUtils.toRecipe(recipeJson);
        Intent intent;

        if (recipe == null) {
            intent = new Intent(context, MainActivity.class);
        } else {
            intent = new Intent(context, RecipeActivity.class);
            intent.putExtra(context.getString(R.string.bundle_key_recipe), recipe);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            views.setTextViewText(R.id.appwidget_text, StringUtils.getIngredients(recipe));
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            SharedPreferences sharedPreferences = getSharedPreferences(context, appWidgetId);
            sharedPreferences.edit().remove(RECIPE).apply();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

