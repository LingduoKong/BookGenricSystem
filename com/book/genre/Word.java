package com.book.genre;

import JSON.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * It is the element of the inverted invertedIndex.
 * Each word contains a table which shows the position of such a word.
 * The table includes books it belongs to and its position in the book's description
 */

public class Word {

    private String word;
    private HashMap<String, ArrayList<Integer>> positions;

    public Word(String word) {
        this.word = word;
        positions = new HashMap<>();
    }

    // insert a new position for current word
    public void insertNewPos(String title, int pos) {

        if (!positions.containsKey(title)) {
            positions.put(title, new ArrayList<>());
        }
        positions.get(title).add(pos);
    }

    public String getWord() {
        return word;
    }

    // find whether this word appears in a certain position of a book's description
    public boolean find(String title, int pos) {

        if (!positions.containsKey(title)) {
            return false;
        }

        if (!positions.get(title).contains(pos)) {
            return false;
        }

        return true;
    }

    // find the appearance of the word in a certain book's description
    public ArrayList<Integer> find(String title) {

        if (positions.containsKey(title)) {
            return positions.get(title);
        } else {
            return new ArrayList<>();
        }
    }

    public String toString() {
        JSONObject wordIndex = new JSONObject(positions);
        return wordIndex.toString();
    }
}
