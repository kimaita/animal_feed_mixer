package com.kimaita.animalfeed.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.kimaita.animalfeed.R;
import com.kimaita.animalfeed.models.Nutrient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MixNutrientsAdapter extends RecyclerView.Adapter<MixNutrientsAdapter.NutrientViewHolder> {

    private List<Nutrient> nutrientList;
    private final Context context;
    private final OnItemClickListener listener;

    public MixNutrientsAdapter(Context c, OnItemClickListener listener) {
        context = c;
        this.listener = listener;
    }

    public void setNutrientList(List<Nutrient> nutrientList) {
        this.nutrientList = nutrientList;
        notifyItemRangeChanged(0, nutrientList.size());
    }

    public interface OnItemClickListener {
        void onItemClick(Nutrient nutrient);
    }

    @NonNull
    @Override
    public NutrientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleritem_mixportions, parent, false);
        return new NutrientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NutrientViewHolder holder, int position) {
        Nutrient nutrient = nutrientList.get(position);
        holder.bind(nutrient, context);
    }

    @Override
    public int getItemCount() {
        return nutrientList.size();
    }

    class NutrientViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView nutrientShareTV;
        private final MaterialTextView nutrientNameTV;

        public NutrientViewHolder(@NonNull View itemView) {
            super(itemView);
            nutrientShareTV = itemView.findViewById(R.id.mix_nutrient_share);
            nutrientNameTV = itemView.findViewById(R.id.mix_nutrient_name);
        }

        public void bind(Nutrient nutrient, Context c) {
            if (nutrient.getMixMass() == null) {
                nutrientNameTV.setText(nutrient.getName());
                nutrientShareTV.setText(c.getString(stringID(nutrient.getLevel(), false),
                        nutrient.getRatio()));
            } else {
                nutrientNameTV.setText(c.getString(R.string.nutrient_name_ratio,
                        nutrient.getName(), nutrient.getRatio()));
                nutrientShareTV.setText(c.getString(stringID(nutrient.getLevel(), true),
                        nutrient.getMixMass()));
            }
            itemView.setOnClickListener(view -> listener.onItemClick(nutrient));
        }

        private int stringID(String level, boolean isMass) {
            if (level.equals("min"))
                return (isMass ? R.string.nutrient_mass_min : R.string.nutrient_ratio_min);
            else if (level.equals("max"))
                return (isMass ? R.string.nutrient_mass_max : R.string.nutrient_ratio_max);
            return (isMass ? R.string.mix_mass : R.string.nutrient_mix_ratio);
        }


    }


}
