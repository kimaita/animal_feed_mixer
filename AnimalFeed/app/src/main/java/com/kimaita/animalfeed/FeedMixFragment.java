package com.kimaita.animalfeed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.kimaita.animalfeed.adapters.MixNutrientsAdapter;
import com.kimaita.animalfeed.databinding.FragmentFeedMixBinding;
import com.kimaita.animalfeed.models.Mix;
import com.kimaita.animalfeed.models.Nutrient;

public class FeedMixFragment extends Fragment {

    private static final String TOTAL = "Total";
    private FragmentFeedMixBinding binding;
    private HomeViewModel mViewModel;
    private String feed;
    private String animal;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFeedMixBinding.inflate(inflater, container, false);
        feed = FeedMixFragmentArgs.fromBundle(getArguments()).getFeed();
        animal = FeedMixFragmentArgs.fromBundle(getArguments()).getAnimal();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(feed);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        MixNutrientsAdapter nutrientsAdapter = new MixNutrientsAdapter(getContext(), nutrient -> mixMassDialogBuilder(nutrient.getName()));
        binding.recyclerFeedmix.setAdapter(nutrientsAdapter);
        binding.recyclerFeedmix.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mViewModel.setMix(feed, animal);

        mViewModel.getMix().observe(getViewLifecycleOwner(), mix -> {
            nutrientsAdapter.setNutrientList(mix.getFeed().getNutrients());
            if (mix.getMass() == null) {
                binding.textMixMass.setText("--");
                binding.textMixRemainder.setText(getResources().getString(R.string.remainder_empty));
            } else {
                binding.textMixMass.setText(getResources().getString(R.string.mix_mass, mix.getMass()));
                binding.textMixRemainder.setText(getResources().getString(R.string.remainder_mass,
                        mix.getUnassignedMass()));
            }
        });

        binding.cardMixTotal.setOnClickListener(view1 -> mixMassDialogBuilder(TOTAL));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void mixMassDialogBuilder(String title) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle(getResources().getString(R.string.set_mass, title));

        View viewInflated = LayoutInflater.from(getContext())
                .inflate(R.layout.mix_input_mass, (ViewGroup) getView(), false);
        final EditText input = viewInflated.findViewById(R.id.edittext_mix_mass);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            dialog.dismiss();
            Double mass = Double.parseDouble(input.getText().toString());
            Mix m = mViewModel.getMix().getValue();
            if (title.equals(TOTAL))
                m.setMass(mass);
            else {
                Nutrient n = m.getFeed().getNutrients().stream()
                        .filter(nutrient -> nutrient.getName().equals(title))
                        .findAny().orElse(null);
                m.calculateMassFromNutrient(n, mass);
            }

            mViewModel.getMix().setValue(m);
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }
}