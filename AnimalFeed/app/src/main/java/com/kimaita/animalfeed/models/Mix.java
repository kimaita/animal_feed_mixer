package com.kimaita.animalfeed.models;

import androidx.annotation.NonNull;

public class Mix {
    private Feed feed;
    private Double mass;
    private Double unassignedMass;

    public Mix(Feed feed) {
        this.setFeed(feed);
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = (mass == null) ? 1 : mass;
        calculateNutrientMasses();
    }

    public Double getUnassignedMass() {
        return unassignedMass;
    }

    public void setUnassignedMass(Double unassignedMass) {
        this.unassignedMass = unassignedMass;
    }

    private void calculateNutrientMasses() {
        for (Nutrient n : feed.getNutrients()) {
            n.setMixMass(getMass() * n.getRatio() * 0.01);
        }
        calculateUnassignedMass();
    }

    public void calculateMassFromNutrient(Nutrient n, Double nutrientMass) {
        double k = nutrientMass / (n.getRatio() * 0.01);
        this.setMass(1 * k);
    }

    private void calculateUnassignedMass() {
        Double assignedMass = getFeed().getNutrients().stream().mapToDouble(Nutrient::getMixMass).sum();
        setUnassignedMass(getMass() - assignedMass);
    }

    @NonNull
    @Override
    public String toString() {
        return "Mix:" +
                "\n\t" + feed.toString() +
                "\n\tTotal mass:" + mass;
    }
}
