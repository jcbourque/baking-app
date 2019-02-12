package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Ingredient;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.Step;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int INGREDIENTS = 0;
    private static final int STEP = 1;

    @Nullable @BindView(R.id.ingredients_list_linear_layout) LinearLayout ingredientsList;
    @Nullable @BindView(R.id.step_image_view) ImageView stepImage;
    @Nullable @BindView(R.id.step_short_description_text_view) TextView shortDescription;

    private final LayoutInflater layoutInflater;

    @Getter
    @Setter
    private Recipe recipe;

    public DetailAdapter(Context context, Recipe recipe) {
        this.recipe = recipe;

        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(getLayoutResourceId(viewType), parent, false);
        ButterKnife.bind(this, view);

        switch (viewType) {
            case INGREDIENTS:
                return new IngredientsViewHolder(view);
            case STEP:
            default:
                return new StepViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == INGREDIENTS) {
            if (ingredientsList == null) {
                return;
            }

            holder.setIsRecyclable(false);

            List<Ingredient> ingredients = recipe.getIngredients();

            if (ingredients != null && !ingredients.isEmpty()) {
                for (Ingredient ingredient : ingredients) {
                    LinearLayout ingredientLayout = (LinearLayout) layoutInflater.inflate(
                            R.layout.ingredient_fragment, ingredientsList, false);

                    TextView quantity = ingredientLayout.findViewById(R.id.ingredient_quantity_text_view);
                    TextView unit = ingredientLayout.findViewById(R.id.ingredient_unit_text_view);
                    TextView name = ingredientLayout.findViewById(R.id.ingredient_name_text_view);

                    quantity.setText(String.valueOf(ingredient.getQuantity()));

                    if ("unit".equalsIgnoreCase(ingredient.getUnit())) {
                        unit.setVisibility(View.GONE);
                    } else {
                        unit.setText(ingredient.getUnit().toLowerCase());
                    }

                    name.setText(ingredient.getName());

                    ingredientsList.addView(ingredientLayout);
                }
            }
        } else {
            if (stepImage == null || shortDescription == null) {
                return;
            }

            Step step = recipe.getSteps().get(position - 1);

            if (!step.getThumbnailURL().isEmpty()) {
                Picasso.get().load(step.getThumbnailURL()).into(stepImage);
                stepImage.setVisibility(View.VISIBLE);
            }

            shortDescription.setText(step.getShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if (recipe != null) {
            if (recipe.hasIngredients()) {
                count++;
            }

            if (recipe.hasSteps()) {
                count += recipe.getSteps().size();
            }
        }

        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return recipe.hasIngredients() && position == 0 ? INGREDIENTS : STEP;
    }

    private int getLayoutResourceId(int viewType) {
        switch (viewType) {
            case INGREDIENTS:
                return R.layout.ingredients_card_fragment;
            case STEP:
            default:
                return R.layout.step;
        }
    }
}
