package com.example.alexeyz.fragmenttesting;

public abstract class LocationExperienceInterface {

    public abstract String getId();
    public abstract String getValue();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationExperienceInterface that = (LocationExperienceInterface) o;

        return getId().equals(that.getId());

    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}