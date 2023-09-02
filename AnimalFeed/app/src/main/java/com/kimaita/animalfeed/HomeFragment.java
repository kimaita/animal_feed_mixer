package com.kimaita.animalfeed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.kimaita.animalfeed.adapters.FeedListAdapter;
import com.kimaita.animalfeed.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel mViewModel;
    private FeedListAdapter animalListAdapter;
    private Map<String, List<String>> animalFeedsMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ExpandableListView animalListView = binding.listAnimals;
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        animalFeedsMap = Objects.requireNonNull(mViewModel.getAnimalFeedsMap().getValue());
        animalListAdapter = new FeedListAdapter(getContext(), animalFeedsMap);
        mViewModel.getAnimalFeedsMap().observe(getViewLifecycleOwner(), animalFeedsMapObserver);
        animalListView.setAdapter(animalListAdapter);
        animalListView.setOnChildClickListener(childClickListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    final Observer<Map<String, List<String>>> animalFeedsMapObserver = newMap ->
            animalListAdapter.notifyDataSetChanged();

    final ExpandableListView.OnChildClickListener childClickListener = (parent, v, groupPos, childPos, id) -> {
        String animal = new ArrayList<>(animalFeedsMap.keySet()).get(groupPos);
        String feed = Objects.requireNonNull(animalFeedsMap.get(animal)).get(childPos);

        HomeFragmentDirections.ActionHomeToFeed toFeedMix = HomeFragmentDirections
                .actionHomeToFeed(feed, animal);
        Navigation.findNavController(v).navigate(toFeedMix);

        return false;
    };

}