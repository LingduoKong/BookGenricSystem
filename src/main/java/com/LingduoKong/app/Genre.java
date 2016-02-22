package com.LingduoKong.app;


import org.json.JSONObject;

import java.util.HashMap;

/**
 *  it represents a genre which contains a table of keyword and value
 */

public class Genre {

    HashMap<String, Integer> key_value;
    String name;
    public Genre(String name) {
        this.name = name;
        key_value = new HashMap<>();
    }

    public String toString() {
        JSONObject genre = new JSONObject(key_value);
        return genre.toString();
    }
}
