package com.example.android.bakingapp;

import android.widget.TextView;

import com.example.android.bakingapp.ui.MainActivity;
import com.example.android.bakingapp.ui.RecipeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(AndroidJUnit4.class)
public class ActivityInstrumentedTest {
    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void testRecyclerViewIsDisplayed() {
        // Test that the recycler view is displayed
        onView(withId(R.id.recipe_list)).check(matches((isDisplayed())));

        // Click on the third entry (Yellow Cake)
        onView(withId(R.id.recipe_list))
                .perform(RecyclerViewActions.scrollToPosition(2))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        // Test that the RecipeActivity is launched via an Intent
        intended(hasComponent(RecipeActivity.class.getName()));

        // Test that the Yellow Cake recipe is shown
        onView(allOf(instanceOf(TextView.class), withParent(withResourceName("action_bar"))))
                .check(matches(withText("Yellow Cake")));
    }
}
