package com.kimaita.animalfeed.models;

import androidx.annotation.NonNull;

import java.util.List;

public class Feed {

    private String feedName;
    private String animal;
    private List<Nutrient> nutrients;

    public Feed(String feedName, String animal, List<Nutrient> feedNutrients) {
        setFeedName(feedName);
        setNutrients(feedNutrients);
        setAnimal(animal);
    }

    public Feed() {

    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    public void setNutrients(List<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    @NonNull
    @Override
    public String toString() {
        return "Feed:" +
                "\n\tfeedName:" + feedName +
                "\n\tanimal:" + animal;
    }
}
