package com.kimaita.animalfeed;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kimaita.animalfeed.models.Feed;
import com.kimaita.animalfeed.models.Mix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<Feed>> feedsList;
    private MutableLiveData<Map<String, List<String>>> animalFeedsMap;
    private MutableLiveData<Mix> mix;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Feed>> getFeedsList() {
        if (feedsList == null) {
            feedsList = new MutableLiveData<>();
            feedsList.setValue(new FileHandler(getApplication().getApplicationContext()).loadAnimalFeeds());
        }
        return feedsList;
    }

    public LiveData<Map<String, List<String>>> getAnimalFeedsMap() {
        if (animalFeedsMap == null) {
            mapToAnimalFeeds();
        }
        return animalFeedsMap;
    }

    public void setMix(String feed, String animal) {
        setAnimalFeedMix(feed, animal);
    }

    public MutableLiveData<Mix> getMix() {
        return mix;
    }

    private void setAnimalFeedMix(String feedName, String animal) {
        mix = new MutableLiveData<>();
        List<Feed> feeds = getFeedsList().getValue();
        Feed feed = feeds.stream()
                .filter(feed1 -> feed1.getFeedName().equals(feedName) && feed1.getAnimal().equals(animal))
                .findAny().orElse(null);
        mix.setValue(new Mix(feed));
    }

    private void mapToAnimalFeeds() {
        animalFeedsMap = new MutableLiveData<>();
        List<Feed> feeds = getFeedsList().getValue();
        animalFeedsMap.setValue(feeds.stream()
                .collect(groupingBy(Feed::getAnimal, HashMap::new,
                        mapping(Feed::getFeedName, toList()))));
    }
}