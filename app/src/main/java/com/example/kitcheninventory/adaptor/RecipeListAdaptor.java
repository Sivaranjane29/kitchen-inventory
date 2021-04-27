/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.recipe.IngredientsList;
import com.example.kitcheninventory.activity.recipe.RecipeList;
import com.example.kitcheninventory.db.master.recipe.MRecipeSummary;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.GlobalConstants;

import java.util.List;

public class RecipeListAdaptor extends RecyclerView.Adapter<RecipeListAdaptor.HallViewHolder> {
    Context mContext;
    List<MRecipeSummary> recipeSummaryList;
    CommonUtils mUtils;

    public RecipeListAdaptor(RecipeList recipeList, List<MRecipeSummary> recipeSummaryList) {
        this.mContext = recipeList;
        this.recipeSummaryList = recipeSummaryList;
        mUtils = new CommonUtils(mContext);
    }

    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_recipe_list, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        MRecipeSummary mRow = recipeSummaryList.get(position);
        holder.txtName.setText(mRow.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, IngredientsList.class);
            intent.putExtra(GlobalConstants.ROW_DB_ID, String.valueOf(mRow.getRecipeSummaryId()));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeSummaryList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }
}
