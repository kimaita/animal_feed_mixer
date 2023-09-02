package com.kimaita.animalfeed.models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Objects;

public class Nutrient {

    private String name;
    private Double ratio;
    private String level;
    private Double mixMass;

    public Nutrient(String name, Double nutrientRatio, String level) {
        setName(name);
        setRatio(nutrientRatio);
        setLevel(level);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Double getMixMass() {
        return mixMass;
    }

    public void setMixMass(Double mixMass) {
        this.mixMass = mixMass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nutrient nutrient = (Nutrient) o;
        return Objects.equals(this.getName(), nutrient.getName()) && Objects.equals(this.getMixMass(), nutrient.getMixMass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mixMass);
    }

    @Override
    public String toString() {
        return "Nutrient:" +
                "\n\tname: " + name +
                "\n\tratio: "+ ratio +
                "\n\tmixMass: " + mixMass;
    }
}
