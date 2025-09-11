package com.example.mountainserver;

import java.util.Objects;

/**
 * Mountain Object stored on/in Server/MountainServer
 */

public class Mountain {
    private String name;
    private String continent;
    private String country;
    private int height;

    public void setName(String name) {
        this.name = name;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public static Mountain makeMountain(final String continent, final String country, final String name,
                                        final int height, final int id) {
        if (name == null || name.isEmpty() || continent == null || continent.isEmpty()
                || country == null || country.isEmpty() || height < 0 || id < 0) {
            return null;
        } else {
            return new Mountain(continent, country, name, height, id);
        }
    }

    public static Mountain makeUploadMountain(final String continent, final String country, final String name,
                                              final int height){
        return Mountain.makeMountain(continent, country, name, height, 0);
    }

    public Mountain(){}

    private Mountain(final String continent, final String country, final String name, final int height, final int id) {
        this.name = name;
        this.continent = continent;
        this.country = country;
        this.height = height;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return continent;
    }

    public String getCountry() {
        return country;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%s is %dm high. It is in %s, which is in %s.", name, height, country, continent);
    }
}
